package astarlib

import org.scalatest.FunSuite

class ParallelExecutorBenchmarksSpec extends FunSuite {
  def time[A](f: => A): Double = {
    val start = System.nanoTime
    f
    (System.nanoTime() - start) / 1e9
  }
  def printScore[A](desc: String, duration: Double): Unit = {
    println(f"$desc in $duration%.3fs")
  }
  test("let's test how parallel map implementations perform") {
    val executor = ParallelExecutor(8)
    val list = (1 to 4000).toList
    val f = (_: Int) => (1 to 100000).toList.sum
    val singleThreadTime = time(executor.mapWithSingleThread(list, f))
    val parallelCollTime = time(executor.mapWithParallelCollections(list, f))
    printScore("single thread", singleThreadTime)
    printScore("parallel", parallelCollTime)
    assert(singleThreadTime > parallelCollTime)
  }
}
