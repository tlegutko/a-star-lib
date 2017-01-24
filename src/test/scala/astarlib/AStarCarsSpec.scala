package astarlib

import astarclient.AStarCarsFromFile
import org.scalatest.{FlatSpec, Matchers}

class AStarCarsSpec extends FlatSpec with Matchers {
  def test(testNum: String): Int = {
    val pathPrefix = "src/main/resources"
    AStarCarsFromFile(s"$pathPrefix/text_in/test$testNum.in", s"$pathPrefix/text_out/test$testNum.out").solve(8).length - 1
  }

  "a-star-lib" should "return correct value for cars example 01" in {
    test("01") should be(2)
  }
  "a-star-lib" should "return correct value for cars example 02" in {
    test("02") should be(3)
  }
  "a-star-lib" should "return correct value for cars example 03" in {
    test("03") should be(8)
  }
  "a-star-lib" should "return correct value for cars example 04" in {
    test("04") should be(23)
  }
  "a-star-lib" should "return correct value for cars example 05" in {
    test("05") should be(24)
  }
  "a-star-lib" should "return correct value for cars example 06" in {
    test("06") should be(238)
  }
  "a-star-lib" should "return correct value for cars example 07" in {
    test("07") should be(21)
  }
  "a-star-lib" should "return correct value for cars example 1000x1000" in {
    test("1000x1000") should be(335)
  }
  "a-star-lib" should "return correct value for cars example 08" in {
    test("08") should be(5)
  }
  "a-star-lib" should "return correct value for cars example 11" in {
    test("11") should be(7)
  }
  "a-star-lib" should "return correct value for cars example 12" in {
    test("12") should be(9)
  }
  "a-star-lib" should "return correct value for cars example 13" in {
    test("13") should be(8)
  }


  //  "a-star-lib" should "return correct value for cars example 09" in {
  //    test("09") should be(8)
  //  }
  //  "a-star-lib" should "return correct value for cars example 10" in {
  //    test("10") should be(8)
  //  }
}
