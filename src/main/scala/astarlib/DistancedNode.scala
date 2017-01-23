package astarlib

case class DistancedNode[T](data: T, cost: Double, heuristic: Double, path: List[T]) {
  def totalCost: Double = cost + heuristic

  override def hashCode(): Int = data hashCode

  override def equals(obj: Any): Boolean = data equals obj.asInstanceOf[DistancedNode[T]].data

  override def toString: String = s"DistancedNode(data = ($data), cost = $cost, heuristic = $heuristic, totalCost = $totalCost)"
}
