package astarlib

import java.util.Comparator

import scala.collection.mutable

private case class DistancedNode[T](data: T, cost: Double, heuristic: Double, path: List[T])

trait AStar[T] {

  def start: T

  def end: T

  def neighbours(node: T): List[T]

  def heuristic(node1: T, node2: T): Double

  def cost(node1: T, node2: T): Double
}

object AStarAlgorithm {

  def solve[T](aStarAPI: AStar[T]): List[T] = {
    val ordering = Ordering.comparatorToOrdering(Comparator.comparing[DistancedNode[T], Double](node => node.cost + node.heuristic))
    val visited = mutable.TreeSet.empty[DistancedNode[T]](ordering)
    val notVisited = mutable.TreeSet(DistancedNode(aStarAPI.start, 0, aStarAPI.heuristic(aStarAPI.start, aStarAPI.end), List.empty))(ordering)
    val bestSolution = Option.empty[DistancedNode[T]]


    while (shouldContinue()) {

    }






    List(aStarAPI.start, aStarAPI.end)
  }

  def shouldContinue(): Boolean = {
    true
  }
}
