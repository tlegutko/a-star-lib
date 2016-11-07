package astarlib

case class AStarNode[T](name: String, data: T)

trait AStarAPI[T] {

  def start: AStarNode[T]

  def end: AStarNode[T]

  def neighbours(node: T): List[T]

  def heuristic(node1: T, node2: T): Double

  def cost(node1: T, node2: T): Double
}

object AStarAlgorithm {

  def solve[T](aStarAPI: AStarAPI[T]): List[AStarNode[T]] = {






    ???
  }
}
