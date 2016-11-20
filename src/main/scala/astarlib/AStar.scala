package astarlib

import scala.collection.mutable

private case class DistancedNode[T](data: T, cost: Double, heuristic: Double, path: List[T]) {
  def totalCost: Double = cost + heuristic

  override def hashCode(): Int = data.hashCode()

  override def equals(obj: scala.Any): Boolean = data equals obj.asInstanceOf[DistancedNode[T]].data
}

trait AStarParameters[T] {

  def start: T

  def isEnd(node: T): Boolean

  def neighbours(node: T): List[T]

  def heuristic(node: T): Double

  def cost(node1: T, node2: T): Double
}

object AStarAlgorithm {

  def solve[T](parameters: AStarParameters[T]): List[T] = {
    val startingNode = DistancedNode(parameters.start, 0, parameters.heuristic(parameters.start), List(parameters.start))

    val visited = mutable.ListBuffer.empty[DistancedNode[T]]
    val notVisited = mutable.ListBuffer(startingNode)
    var bestSolution = Option.empty[DistancedNode[T]]

    while (true) {
      val maybeNode = nodeToContinueWith(visited, notVisited, bestSolution)

      if (maybeNode.isEmpty) {
        if (bestSolution.isDefined) {
          return bestSolution.get.path
        } else {
          return List.empty
        }
      }

      val node = maybeNode.get

      calculateNeighbours(node, parameters).foreach(aNode => {
        if (parameters.isEnd(aNode.data)) {
          if (bestSolution.isEmpty || bestSolution.get.cost > aNode.cost) {
            bestSolution = Option(aNode)
          }
          if (!visited.contains(aNode)) {
            visited += aNode
            visited.sortBy(_.totalCost)
          }
        } else if (!(visited.contains(aNode) || notVisited.contains(aNode))) {
          notVisited += aNode
          notVisited.sortBy(_.totalCost)
        }
      })
    }

    List.empty
  }

  private def calculateNeighbours[T](originalNode: DistancedNode[T], parameters: AStarParameters[T]): List[DistancedNode[T]] = {
    val rawNeighbours = parameters.neighbours(originalNode.data)

    rawNeighbours.map(rawNeighbour => {

      val cost = originalNode.cost + parameters.cost(originalNode.data, rawNeighbour)
      val heuristic = parameters.heuristic(rawNeighbour)

      DistancedNode(rawNeighbour, cost, heuristic, originalNode.path :+ rawNeighbour)
    })
  }

  private def nodeToContinueWith[T](visited: mutable.ListBuffer[DistancedNode[T]],
                                    notVisited: mutable.ListBuffer[DistancedNode[T]],
                                    bestSolution: Option[DistancedNode[T]]): Option[DistancedNode[T]] = {
    while (true) {
      if (notVisited.isEmpty) {
        return Option.empty
      }

      val node = notVisited.head
      notVisited -= node
      if (!visited.contains(node)) {
        visited += node
        visited.sortBy(_.totalCost)
      }
      if (bestSolution.isEmpty || bestSolution.get.totalCost > node.totalCost) {
        return Option(node)
      }
    }
    Option.empty
  }
}
