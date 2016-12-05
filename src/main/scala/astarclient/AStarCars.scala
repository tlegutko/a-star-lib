package astarclient

import astarlib.{AStarAlgorithm, AStarParameters}

import scala.collection.mutable

case class AStarCars(map: Seq[Seq[Int]], startPoint: SpeedPoint, endPoint: SpeedPoint) {

  def n = map.head.length

  def m = map.length

  def prepareParameters = new AStarParameters[SpeedPoint] {
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
      val minStepsX = calculateMinNumberOfSteps(node.x, node.vx, endPoint.x)
      val minStepsY = calculateMinNumberOfSteps(node.y, node.vy, endPoint.y)
      math.max(minStepsX, minStepsY)
    }

    private def calculateMinNumberOfSteps(start: Int, speed: Int, end: Int): Int = {
      val distance = math.abs(start - end)
      var speedTowardsTarget = speed
      if (end < start) {
        speedTowardsTarget = -speedTowardsTarget
      }
      calculateMinNumberOfSteps(distance, speedTowardsTarget)
    }

    private def calculateMinNumberOfSteps(distance: Int, speedTowardsTarget: Int): Int = {
      if (speedTowardsTarget < 0) {
        val speedAwayFromTarget = -speedTowardsTarget
        o2v2oSteps(v2o(speedAwayFromTarget) + distance) + speedAwayFromTarget
      } else if (v2o(speedTowardsTarget) > distance) {
        o2v2oSteps(v2o(speedTowardsTarget) - distance) + speedTowardsTarget
      } else {
        vo2v2v0Steps(distance - v2o(speedTowardsTarget), speedTowardsTarget) + speedTowardsTarget
      }
    }

    private def v2o(v: Int): Int = v * (v - 1) / 2

    private def o2v2oSteps(x: Int): Int = math.ceil(2 * math.sqrt(x)).toInt

    private def vo2v2v0Steps(x: Int, v0: Int): Int = math.ceil(2 * (math.sqrt(x + v0 * v0) - v0 * v0)).toInt

    override def cost(node1: SpeedPoint, node2: SpeedPoint): Double = 1
  }

  def solve = AStarAlgorithm.solve(prepareParameters)

  def solveCountingHeuristicCalls = AStarAlgorithm.solveCountingHeuristicCalls(prepareParameters)
}
