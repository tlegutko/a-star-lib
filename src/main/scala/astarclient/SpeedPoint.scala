package astarclient

case class SpeedPoint(x: Int, y: Int, vx: Int, vy: Int) {
  override def toString: String = s"position = $x, $y; speed = $vx, $vy"
}
