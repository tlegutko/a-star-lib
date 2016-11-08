package astarlib

import java.util.Comparator

import scala.collection.mutable
import scala.util.control.Breaks._

private case class DistancedNode[T](data: T, cost: Double, heuristic: Double, path: List[T]) {
  def totalCost: Double = cost + heuristic
}

trait AStarParameters[T] {

  def start: T

  def end: T

  def neighbours(node: T): List[T]

  def heuristic(node1: T, node2: T): Double

  def cost(node1: T, node2: T): Double
}

object AStarAlgorithm {

  def solve[T](parameters: AStarParameters[T]): List[T] = {
    val ordering = Ordering.comparatorToOrdering(Comparator.comparingDouble[DistancedNode[T]](node => node.totalCost))

    val visited = mutable.TreeSet.empty[DistancedNode[T]](ordering)
    val notVisited = mutable.TreeSet(DistancedNode(parameters.start, 0, parameters.heuristic(parameters.start, parameters.end), List(parameters.start)))(ordering)
    var bestSolution = Option.empty[DistancedNode[T]]

    breakable {
      while (true) {
        val maybeNode = nodeToContinueWith(visited, notVisited, bestSolution)
        if (maybeNode.isEmpty) {
          break
        }
        val node = maybeNode.get

        notVisited.remove(node)
        calculateNeighbours(node, parameters).foreach(aNode => {
          if (aNode.path.last equals parameters.end) {
            if (bestSolution.isEmpty || bestSolution.get.cost > aNode.cost) {
              bestSolution = Option(aNode)
            }
          }
          visited.add(aNode)
        })
      }
    }

    if (bestSolution.isDefined) {
      bestSolution.get.path
    } else {
      List.empty
    }
  }

  private def calculateNeighbours[T](originalNode: DistancedNode[T], parameters: AStarParameters[T]): List[DistancedNode[T]] = {
    val rawNeighbours = parameters.neighbours(originalNode.data)

    rawNeighbours.map(rawNeighbour => {

      val cost = originalNode.cost + parameters.cost(originalNode.data, rawNeighbour)
      val heuristic = parameters.heuristic(rawNeighbour, originalNode.data)

      DistancedNode(rawNeighbour, cost, heuristic, originalNode.path :+ rawNeighbour)
    })
  }

  private def nodeToContinueWith[T](visited: mutable.TreeSet[DistancedNode[T]], notVisited: mutable.TreeSet[DistancedNode[T]], bestSolution: Option[DistancedNode[T]]): Option[DistancedNode[T]] = {
    breakable {
      while (true) {
        if (notVisited.isEmpty) {
          break
        }

        val node = notVisited.firstKey
        if (bestSolution.isEmpty || bestSolution.get.cost > node.totalCost) {
          return Option(node)
        }
        notVisited.remove(node)
        visited.add(node)
      }
    }
    Option.empty
  }
}
