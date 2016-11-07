package astarlib

import astarclient.{AStar2Dimensions, Point}
import org.scalatest.{FlatSpec, Matchers}

class AStar2DimensionsSpec extends FlatSpec with Matchers {
  "a-star-lib" should "return correct value for simple case" in {
    val map = Seq(
      Seq(1, 0),
      Seq(1, 1)
    )
    val correctPath = List(Point(0, 0), Point(0, 1), Point(1, 1))
    AStar2Dimensions(map).solve() should be(correctPath)
  }
  "a-star-lib" should "return empty list when there is no solution" in {
    val map = Seq(
      Seq(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
      Seq(1, 1, 1, 0, 1, 1, 1, 0, 1, 1),
      Seq(1, 1, 1, 0, 1, 1, 1, 0, 1, 1),
      Seq(1, 1, 1, 0, 1, 1, 1, 0, 1, 1),
      Seq(1, 1, 1, 0, 1, 1, 1, 0, 1, 1),
      Seq(1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
      Seq(1, 1, 1, 1, 1, 1, 1, 1, 0, 1),
      Seq(1, 1, 1, 1, 1, 1, 1, 1, 0, 1),
      Seq(1, 1, 1, 1, 1, 1, 1, 1, 0, 1),
      Seq(1, 1, 1, 1, 1, 1, 1, 1, 0, 1)
    )
    AStar2Dimensions(map).solve() should be(List.empty)
  }
}
