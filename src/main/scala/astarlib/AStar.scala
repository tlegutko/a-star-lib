package astarlib

import scala.collection.mutable

trait AStarParameters[T] {

  def start: T

  def isEnd(node: T): Boolean

  def neighbours(node: T): List[T]

  def heuristic(node: T): Double

  def cost(node1: T, node2: T): Double
}

class AStarAlgorithm[T](numberOfParallelHeuristicProcessors: Int) {
  val parallelActorsExecutor: ParallelActorsExecutor[T, Double] = new ParallelActorsExecutor[T, Double](numberOfParallelHeuristicProcessors)

  def solveCountingHeuristicCalls(parameters: AStarParameters[T]): (List[T], Int) = {
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

  def solve(parameters: AStarParameters[T]): List[T] = {
    val startingNode = DistancedNode(parameters.start, 0, parameters.heuristic(parameters.start), List(parameters.start))

    val visited: mutable.HashSet[DistancedNode[T]] = mutable.HashSet empty
    val notVisited: DistancedSet[T] = new DistancedSet[T]
    notVisited add startingNode
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
          visited add aNode
        } else if (!((visited contains aNode) || (notVisited contains aNode))) {
          notVisited add aNode
        }
      })
    }

    List.empty
  }

  private def calculateNeighbours(originalNode: DistancedNode[T], parameters: AStarParameters[T]): List[DistancedNode[T]] = {
    val rawNeighbours = parameters.neighbours(originalNode.data)
    val calculatedHeuristics = (rawNeighbours zip parallelActorsExecutor.mapUsingActors(rawNeighbours, parameters.heuristic)).toMap

    rawNeighbours.map(rawNeighbour => {
      val cost = originalNode.cost + parameters.cost(originalNode.data, rawNeighbour)
      val heuristic = calculatedHeuristics(rawNeighbour)

      DistancedNode(rawNeighbour, cost, heuristic, originalNode.path :+ rawNeighbour)
    })
  }

  private def nodeToContinueWith(visited: mutable.HashSet[DistancedNode[T]],
                                 notVisited: DistancedSet[T],
                                 bestSolution: Option[DistancedNode[T]]): Option[DistancedNode[T]] = {
    while (true) {
      if (notVisited isEmpty) {
        return Option empty
      }

      val node = notVisited takeSmallestTotalCostNode

      visited add node

      if (bestSolution.isEmpty || bestSolution.get.totalCost > node.totalCost) {
        return Option(node)
      }
    }
    Option.empty
  }
}
