package astarlib

import astarclient.AStarCarsFromFile
import org.scalatest.{FlatSpec, Matchers}

class AStarCarsSpec extends FlatSpec with Matchers {
  "a-star-lib" should "return correct value for cars example 01" in {
    AStarCarsFromFile("src/test/resources/test01.in").solve.length - 1 should be(2)
  }
  "a-star-lib" should "return correct value for cars example 02" in {
    AStarCarsFromFile("src/test/resources/test02.in").solve.length - 1 should be(3)
  }
  "a-star-lib" should "return correct value for cars example 03" in {
    AStarCarsFromFile("src/test/resources/test03.in").solve.length - 1 should be(8)
  }
  "a-star-lib" should "return correct value for cars example 04" in {
    AStarCarsFromFile("src/test/resources/test04.in").solve.length - 1 should be(23)
  }
  "a-star-lib" should "return correct value for cars example 05 0" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 1" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 2" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 3" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 4" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 5" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 6" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 7" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 8" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 9" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 10" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 11" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 12" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 13" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 05 14" in {
    AStarCarsFromFile("src/test/resources/test05.in").solve.length - 1 should be(24)
  }
}
