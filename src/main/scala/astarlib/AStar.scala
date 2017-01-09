package astarlib

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.util.Timeout

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration.Duration

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

class AStarAlgorithm(numberOfParallelHeuristicProcessors: Int) {
  val actorSystem: ActorSystem = ActorSystem("astarsystem")

  def solveCountingHeuristicCalls[T](parameters: AStarParameters[T]): (List[T], Int) = {
    var heuristicCalls = 0
    val solution = solve(new AStarParameters[T] {
      override def start: T = parameters.start

      override def isEnd(node: T): Boolean = parameters.isEnd(node)

      override def neighbours(node: T): List[T] = parameters.neighbours(node)

      override def heuristic(node: T): Double = {
        heuristicCalls = heuristicCalls + 1
        parameters.heuristic(node)
      }

      override def cost(node1: T, node2: T): Double = parameters.cost(node1, node2)
    })
    (solution, heuristicCalls)
  }

  def solve[T](parameters: AStarParameters[T]): List[T] = {
    val heuristicScheduler: ActorRef = actorSystem.actorOf(Props(new HeuristicScheduler[T](actorSystem, parameters.heuristic, numberOfParallelHeuristicProcessors)))
    val startingNode = DistancedNode(parameters.start, 0, parameters.heuristic(parameters.start), List(parameters.start))

    val visited: mutable.HashSet[DistancedNode[T]] = mutable.HashSet.empty
    val notVisited: mutable.HashSet[DistancedNode[T]] = mutable.HashSet(startingNode)
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

      calculateNeighbours(node, parameters, heuristicScheduler).foreach(aNode => {
        if (parameters.isEnd(aNode.data)) {
          if (bestSolution.isEmpty || bestSolution.get.cost > aNode.cost) {
            bestSolution = Option(aNode)
          }
          visited add aNode
        } else if (!((visited contains aNode) || (notVisited contains aNode))) {
          notVisited add aNode
        }
      })
    }

    List.empty
  }

  private def calculateNeighbours[T](originalNode: DistancedNode[T], parameters: AStarParameters[T], heuristicScheduler: ActorRef): List[DistancedNode[T]] = {
    val rawNeighbours = parameters.neighbours(originalNode.data)
    val calculatedHeuristics: mutable.HashMap[T, Double] =
      Await.result(akka.pattern.ask(heuristicScheduler, CalculationRequest(rawNeighbours))(Timeout(10, TimeUnit.DAYS))
        .mapTo[CalculationResponse[T]]
        , Duration.Inf)
        .resultsMap

    rawNeighbours.map(rawNeighbour => {
      val cost = originalNode.cost + parameters.cost(originalNode.data, rawNeighbour)
      val heuristic = calculatedHeuristics(rawNeighbour)

      DistancedNode(rawNeighbour, cost, heuristic, originalNode.path :+ rawNeighbour)
    })
  }

  private def nodeToContinueWith[T](visited: mutable.HashSet[DistancedNode[T]],
                                    notVisited: mutable.HashSet[DistancedNode[T]],
                                    bestSolution: Option[DistancedNode[T]]): Option[DistancedNode[T]] = {
    while (true) {
      if (notVisited.isEmpty) {
        return Option.empty
      }

      val node = notVisited.reduce((node1, node2) => if (node1.totalCost < node2.totalCost) node1 else node2)

      notVisited remove node
      visited add node

      if (bestSolution.isEmpty || bestSolution.get.totalCost > node.totalCost) {
        return Option(node)
      }
    }
    Option.empty
  }
}

private case class CalculationRequest[T](request: List[T])

private case class CalculationResponse[T](resultsMap: mutable.HashMap[T, Double])

private case class CalculationTask[T](arg: T)

private case class CalculationTaskResponse[T](arg: T, response: Double)

private class HeuristicScheduler[T](actorSystem: ActorSystem, heuristicFunction: T => Double, numberOfProcessors: Int) extends Actor {
  val processors: List[ActorRef] = (for (i <- 1 to numberOfProcessors) yield actorSystem.actorOf(Props(new HeuristicProcessor[T](heuristicFunction)))).toList

  var currentRequest: mutable.HashMap[T, Double] = _
  var currentRequestSender: ActorRef = _
  var currentRequestResponses: Int = 0

  override def receive = {
    case CalculationRequest(request: List[T]) =>
      currentRequest = mutable.HashMap.empty
      currentRequestSender = sender
      currentRequestResponses = 0
      var i: Int = 0
      for (key <- request) {
        processors(i) ! CalculationTask(key)
        i = (i + 1) % numberOfProcessors
      }
    case CalculationTaskResponse(arg: T, result: Double) =>
      currentRequest.put(arg, result)
      currentRequestResponses = currentRequestResponses + 1
      if (currentRequestResponses == currentRequest.size) {
        currentRequestSender ! CalculationResponse(currentRequest)
      }
    case _ => println("unknown")
  }
}

private class HeuristicProcessor[T](heuristicFunction: T => Double) extends Actor {
  override def receive = {
    case CalculationTask(arg: T) =>
      sender ! CalculationTaskResponse(arg, heuristicFunction(arg))
  }
}
