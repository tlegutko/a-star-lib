package astarclient

import astarlib.{AStarAlgorithm, AStarParameters}

import scala.collection.mutable

case class AStarCars(map: Seq[Seq[Int]], startPoint: CarsState, endPoint: CarState) {

  def n = map.head.length

  def m = map.length

  def prepareParameters = new AStarParameters[CarsState] {
    override def start = startPoint

    override def isEnd(node: CarsState) = node.cars.forall(node => (node equals endPoint) || node.x == -1)

    override def neighbours(node: CarsState): List[CarsState] = {
      cartesianProduct(node.cars.map(neighbourStates)).filter(stateValid).map(CarsState)
    }

    private def stateValid(state: List[CarState]): Boolean = {
      for (index1 <- state.indices) {
        val car1 = state(index1)
        if (car1.x >= 0) {
          for (index2 <- index1 + 1 until state.length) {
            val car2 = state(index2)
            if (car1.x == car2.x && car1.y == car2.y) {
              return false
            }
          }
        }
      }
      true
    }

    private def cartesianProduct(xss: List[List[CarState]]): List[List[CarState]] = xss match {
      case Nil => List(Nil)
      case h :: t => for (xh <- h; xt <- cartesianProduct(t)) yield xh :: xt
    }

    private def neighbourStates(node: CarState): List[CarState] = {
      if ((node equals endPoint) || (node.x == -1)) {
        return List(CarState(-1, -1, 0, 0))
      }

      val result: mutable.ListBuffer[CarState] = mutable.ListBuffer.empty

      for (i <- -1 to 1; j <- -1 to 1) {
        val newX = node.x + node.vx + i
        val newY = node.y + node.vy + j

        if (newX >= 0 && newX < n && newY >= 0 && newY < m && map(newY)(newX) == 0) {
          result += CarState(newX, newY, node.vx + i, node.vy + j)
        }
      }

      result.toList
    }

    override def heuristic(node: CarsState): Double = {
      node.cars.map(heuristic(_)).reduce(math.max(_, _))
    }

    def heuristic(node: CarState): Double = {
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
        minStepsToGoThrough(distanceWhileStoppingFrom(speedAwayFromTarget) + distance) + speedAwayFromTarget
      } else {
        val distanceWhileStoppingFromStartSpeed = distanceWhileStoppingFrom(speedTowardsTarget)
        if (distanceWhileStoppingFromStartSpeed > distance) {
          minStepsToGoThrough(distanceWhileStoppingFromStartSpeed - distance) + speedTowardsTarget
        } else {
          minStepsToGoThroughStartingAtVo(distance - distanceWhileStoppingFromStartSpeed, speedTowardsTarget) + speedTowardsTarget
        }
      }
    }

    private def distanceWhileStoppingFrom(v: Int) = v2o(v)

    private def minStepsToGoThrough(x: Int) = math.min(o2v2oSteps(x), o2vv2oSteps(x))

    private def minStepsToGoThroughStartingAtVo(x: Int, v0: Int) = math.min(vo2v2voSteps(x, v0), vo2vv2voSteps(x, v0))

    private def v2o(v: Int): Int = v * (v - 1) / 2

    private def o2v2oSteps(x: Int): Int = math.ceil(2 * math.sqrt(x)).toInt

    private def o2vv2oSteps(x: Int): Int = math.ceil(math.sqrt(1 + 4 * x)).toInt

    private def vo2v2voSteps(x: Int, v0: Int): Int = math.ceil(2 * (math.sqrt(x + v0 * v0) - v0 * v0)).toInt

    private def vo2vv2voSteps(x: Int, v0: Int): Int = math.ceil(math.sqrt(1 + 4 * (x + v0 * v0)) - 2 * v0 * v0).toInt

    override def cost(node1: CarsState, node2: CarsState): Double = 1
  }

  def solve = new AStarAlgorithm(1).solve(prepareParameters)

  def solveCountingHeuristicCalls = new AStarAlgorithm(1).solveCountingHeuristicCalls(prepareParameters)
}
