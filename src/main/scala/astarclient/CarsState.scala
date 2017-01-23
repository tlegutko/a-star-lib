package astarclient

case class CarState(x: Int, y: Int, vx: Int, vy: Int) {
  override def toString: String = s"position = [$x, $y], speed = [$vx, $vy]"
}

case class CarsState(cars: List[CarState]) {
  override def toString: String = cars.map(_.toString).mkString("; ")
}
