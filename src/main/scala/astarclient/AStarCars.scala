package astarclient

import astarlib.{AStarAlgorithm, AStarParameters}

import scala.collection.mutable

case class AStarCars(map: Seq[Seq[Int]], startPoint: SpeedPoint, endPoint: SpeedPoint) {

  def n = map.head.length

  def m = map.length

  def solve = AStarAlgorithm.solve(new AStarParameters[SpeedPoint] {
    override def start = startPoint

    override def isEnd(node: SpeedPoint) = node equals endPoint

    override def neighbours(node: SpeedPoint): List[SpeedPoint] = {
      val result: mutable.ListBuffer[SpeedPoint] = mutable.ListBuffer.empty

      for (i <- -1 to 1; j <- -1 to 1) {
        val newX = node.x + node.vx + i
        val newY = node.y + node.vy + j

        if (newX >= 0 && newX < n && newY >= 0 && newY < m && map(newY)(newX) == 0) {
          result += SpeedPoint(newX, newY, node.vx + i, node.vy + j)
        }
      }

      result.toList
    }

    override def heuristic(node: SpeedPoint): Double = {
      val distance = (endPoint.x - node.x) + (endPoint.y - node.y)
      val speed = node.vx + node.vy

      var distanceInSteps = speed
      var steps = 1

      while (true) {
        if (distance <= distanceInSteps) {
          return steps
        }
        distanceInSteps = distanceInSteps + steps * 2 + speed
        steps = steps + 1
      }
      0
    }

    override def cost(node1: SpeedPoint, node2: SpeedPoint): Double = 1
  })
}
