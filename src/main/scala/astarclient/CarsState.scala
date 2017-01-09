package astarclient

case class CarState(x: Int, y: Int, vx: Int, vy: Int)

case class CarsState(cars: List[CarState]) {
  override def toString: String = cars.map(carState => s"position = ${carState.x}, ${carState.y}; speed = ${carState.vx}, ${carState.vy}").mkString(" ||| ")
}
