package astarlib

import org.scalatest.FunSuite

import scala.util.Random

class ParallelExecutorBenchmarksSpec extends FunSuite {
//  def time[A](desc: String, f: => A): Double = {
//    val start = System.nanoTime
//    f
//    val duration = (System.nanoTime() - start) / 1e9
//    println(f"$desc in $duration%.3fs")
//    duration
//  }
//  test("let's test how parallel map implementations perform") {
//    val list = (1 to 1000).toList
//    val f = (_: Int) => (1 to 30000).map(Random.nextInt).sorted.sum
//    val singleThreadTime = time("single thread", ParallelExecutor.mapWithSingleThread(list, f))
//    val parallelCollTime = time("parallel collections", ParallelExecutor.mapWithParallelCollections(list, f))
//    val futuresTime = time("futures", ParallelExecutor.mapWithFutures(list, f))
//    val actorsTime = time("actors", new ParallelActorsExecutor(8).mapUsingActors(list, f))
//    assert(parallelCollTime < singleThreadTime)
//    assert(futuresTime < singleThreadTime)
//    assert(actorsTime < singleThreadTime)
//  }
}
