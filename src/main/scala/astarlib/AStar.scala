package astarlib

trait AStarNode[T] {
  def neighbours: List[T]
  def heuristic(node1: T, node2: T): Double

}
