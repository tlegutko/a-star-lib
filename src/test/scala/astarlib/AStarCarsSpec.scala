package astarlib

import astarclient.AStarCarsFromFile
import org.scalatest.{FlatSpec, Matchers}

class AStarCarsSpec extends FlatSpec with Matchers {
  "a-star-lib" should "return correct value for cars example 01" in {
    AStarCarsFromFile("../testRepo/text_in/test01.in", "../testRepo/text_out/test01.out").solve.length - 1 should be(2)
  }
  "a-star-lib" should "return correct value for cars example 02" in {
    AStarCarsFromFile("../testRepo/text_in/test02.in", "../testRepo/text_out/test02.out").solve.length - 1 should be(3)
  }
  "a-star-lib" should "return correct value for cars example 03" in {
    AStarCarsFromFile("../testRepo/text_in/test03.in", "../testRepo/text_out/test03.out").solve.length - 1 should be(8)
  }
  "a-star-lib" should "return correct value for cars example 04" in {
    AStarCarsFromFile("../testRepo/text_in/test04.in", "../testRepo/text_out/test04.out").solve.length - 1 should be(23)
  }
  "a-star-lib" should "return correct value for cars example 05" in {
    AStarCarsFromFile("../testRepo/text_in/test05.in", "../testRepo/text_out/test05.out").solve.length - 1 should be(24)
  }
  "a-star-lib" should "return correct value for cars example 06" in {
    AStarCarsFromFile("../testRepo/text_in/test06.in", "../testRepo/text_out/test06.out").solve.length - 1 should be(234)
  }
  "a-star-lib" should "return correct value for cars example 07" in {
    AStarCarsFromFile("../testRepo/text_in/test07.in", "../testRepo/text_out/test07.out").solve.length - 1 should be(21)
  }
  "a-star-lib" should "return correct value for cars example 1000x1000" in {
    AStarCarsFromFile("../testRepo/text_in/test1000x1000.in", "../testRepo/text_out/test1000x1000.out").solve.length - 1 should be(338)
  }
  "a-star-lib" should "return correct value for cars example 08" in {
    AStarCarsFromFile("../testRepo/text_in/test08.in", "../testRepo/text_out/test08.out").solve.length - 1 should be(8)
  }
  "a-star-lib" should "return correct value for cars example 09" in {
    AStarCarsFromFile("../testRepo/text_in/test09.in", "../testRepo/text_out/test09.out").solve.length - 1 should be(8)
  }
  "a-star-lib" should "return correct value for cars example 10" in {
    AStarCarsFromFile("../testRepo/text_in/test10.in", "../testRepo/text_out/test10.out").solve.length - 1 should be(8)
  }
}
