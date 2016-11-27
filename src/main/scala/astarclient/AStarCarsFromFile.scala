package astarclient

import scala.collection.mutable.ListBuffer
import scala.io.Source

case class AStarCarsFromFile(fileName: String) {

  def prepareAStar = {
    val lines = Source.fromFile(fileName).getLines().toList

    val rawMap: List[List[Int]] = lines.tail.map(_.split(" ").map(_.toInt).toList)

    val starts: ListBuffer[Point] = ListBuffer.empty
    val ends: ListBuffer[Point] = ListBuffer.empty

    for (x <- rawMap.head.indices; y <- rawMap.indices) {
      if (rawMap(y)(x) == 2) {
        starts += Point(x, y)
      }
      if (rawMap(y)(x) == 3) {
        ends += Point(x, y)
      }
    }

    val map = rawMap.map(_.map(number => {
      if (number == 1) {
        1
      } else {
        0
      }
    }))

    val start = starts.head
    val end = ends.head
    AStarCars(map, SpeedPoint(start.x, start.y, 0, 0), SpeedPoint(end.x, end.y, 0, 0))
  }

  def solve = {
    val printSolution = true
    if (printSolution) {
      val start = System.currentTimeMillis
      val (solution, heuristicCalls) = prepareAStar.solveCountingHeuristicCalls
      val end = System.currentTimeMillis

      println("==============================================")
      println(s"File name: ${fileName.split("/").last}")
      println(s"Execution time: ${(end - start) / 1000.0} s")
      println(s"Heuristic function calls: $heuristicCalls")
      println(s"Number of steps: ${solution.length - 1}")
      println("Solution:")
      solution.foreach(println)
      println("==============================================")

      solution
    } else {
      prepareAStar.solve
    }
  }
}
