package astarlib

import astarclient.AStarCarsFromFile
import org.scalatest.{FlatSpec, Matchers}

class AStarCarsSpec extends FlatSpec with Matchers {
  "a-star-lib" should "return correct value for cars example 01" in {
    AStarCarsFromFile("../testRepo/text_in/test01.in").solve.length - 1 should be(2)
  }
  "a-star-lib" should "return correct value for cars example 02" in {
    AStarCarsFromFile("../testRepo/text_in/test02.in").solve.length - 1 should be(3)
  }
  "a-star-lib" should "return correct value for cars example 03" in {
    AStarCarsFromFile("../testRepo/text_in/test03.in").solve.length - 1 should be(8)
  }
  "a-star-lib" should "return correct value for cars example 04" in {
    AStarCarsFromFile("../testRepo/text_in/test04.in").solve.length - 1 should be(23)
  }
  "a-star-lib" should "return correct value for cars example 05" in {
    AStarCarsFromFile("../testRepo/text_in/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 06" in {
    AStarCarsFromFile("../testRepo/text_in/test06.in").solve.length - 1 should be(237)
  }
  "a-star-lib" should "return correct value for cars example 07" in {
    AStarCarsFromFile("../testRepo/text_in/test07.in").solve.length - 1 should be(21)
  }
  "a-star-lib" should "return correct value for cars example 1000x1000" in {
    AStarCarsFromFile("../testRepo/text_in/test1000x1000.in").solve.length - 1 should be(337)
  }
}
