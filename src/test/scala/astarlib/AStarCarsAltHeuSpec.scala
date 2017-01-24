package astarlib

import astarclient.AStarCarsFromFile
import org.scalatest.{FlatSpec, Matchers}

class AStarCarsAltHeuSpec extends FlatSpec with Matchers {
  def test(testNum: String): Int = {
    val pathPrefix = "src/main/resources"
    AStarCarsFromFile(s"$pathPrefix/text_in/test$testNum.in", s"$pathPrefix/text_out/test$testNum.out").solveWithAStarH(8).length - 1
  }

  "a-star-lib" should "return correct value for cars example 08" in {
    test("08") should be(5)
  }
  "a-star-lib" should "return correct value for cars example 11" in {
    test("11") should be(7)
  }


  //  "a-star-lib" should "return correct value for cars example 12" in {
  //    test("12") should be(9)
  //  }
  //  "a-star-lib" should "return correct value for cars example 13" in {
  //    test("13") should be(8)
  //  }
  //  "a-star-lib" should "return correct value for cars example 09" in {
  //    test("09") should be(8)
  //  }
  //  "a-star-lib" should "return correct value for cars example 10" in {
  //    test("10") should be(8)
  //  }
}
