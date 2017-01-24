package astarclient

import scala.collection.mutable.ListBuffer
import scala.io.Source

case class AStarCarsFromFile(fileName: String, outputFilename: String) {

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

    val map = rawMap.map(_.map(number =>
      if (number == 1) {
        1
      } else {
        0
      }
    ))

    val end = ends.head
    AStarCars(map, CarsState(starts.map(start => CarState(start.x, start.y, 0, 0)).toList), CarState(end.x, end.y, 0, 0))
  }

  def printToFile(filename: String)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(new java.io.File(filename))
    try {
      op(p)
    } finally {
      p.close()
    }
  }

  def solve(numberOfParallelHeuristicProcessors: Int) = {
    val printSolution = true
    if (printSolution) {

      // printing to console
      val start = System.currentTimeMillis
      val (solution, heuristicCalls) = prepareAStar.solveCountingHeuristicCalls(numberOfParallelHeuristicProcessors)
      val end = System.currentTimeMillis

      println("==============================================")
      println(s"File name: ${fileName.split("/").last}")
      println(s"Execution time: ${(end - start) / 1000.0} s")
      println(s"Heuristic function calls: $heuristicCalls")
      println(s"Number of steps: ${solution.length - 1}")
      //      println("Solution:")
      //      solution.foreach(println)
      println("==============================================")

      // printing to file
      printToFile(outputFilename) { printer =>
        printer.println(s"${solution.length} 1")
        solution.foreach(_.cars.foreach(car => printer.println(s"${car.x} ${car.y} ${car.vx} ${car.vy}")))
      }

      solution
    } else {
      prepareAStar.solve(numberOfParallelHeuristicProcessors)
    }
  }

  def solveWithAStarH(numberOfParallelHeuristicProcessors: Int) = {
    val printSolution = true
    if (printSolution) {

      // printing to console
      val start = System.currentTimeMillis
      val (solution, heuristicCalls) = prepareAStar.solveWithAStarHCountingHeuristicCalls(numberOfParallelHeuristicProcessors)
      val end = System.currentTimeMillis

      println("==============================================")
      println(s"File name: ${fileName.split("/").last}")
      println(s"Execution time: ${(end - start) / 1000.0} s")
      println(s"Heuristic function calls: $heuristicCalls")
      println(s"Number of steps: ${solution.length - 1}")
      //      println("Solution:")
      //      solution.foreach(println)
      println("==============================================")

      // printing to file
      printToFile(outputFilename) { printer =>
        printer.println(s"${solution.length} 1")
        solution.foreach(_.cars.foreach(car => printer.println(s"${car.x} ${car.y} ${car.vx} ${car.vy}")))
      }

      solution
    } else {
      prepareAStar.solveWithAStarH(numberOfParallelHeuristicProcessors)
    }
  }
}
