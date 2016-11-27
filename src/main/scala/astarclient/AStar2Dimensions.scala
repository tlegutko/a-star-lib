package astarclient

import astarlib.{AStarAlgorithm, AStarParameters}

case class AStar2Dimensions(map: Seq[Seq[Int]]) {

  def n = map.length

  def solve() = AStarAlgorithm.solve(new AStarParameters[Point] {
    override def start = Point(0, 0)

    override def isEnd(node: Point) = node.x == n - 1 && node.y == n - 1

    override def neighbours(node: Point): List[Point] = {
      List(
        Point(node.x + 1, node.y + 1),
        Point(node.x + 1, node.y),
        Point(node.x + 1, node.y - 1),
        Point(node.x, node.y + 1),
        Point(node.x, node.y - 1),
        Point(node.x - 1, node.y + 1),
        Point(node.x - 1, node.y),
        Point(node.x - 1, node.y - 1)
      ).filterNot(p =>
        p.x < 0 || p.x >= n || p.y < 0 || p.y >= n || map(p.x)(p.y) == 0
      )
    }

    override def heuristic(node: Point): Double =
      Math.sqrt(Math.pow(node.x - n + 1, 2) + Math.pow(node.y - n + 1, 2))

    override def cost(node1: Point, node2: Point): Double = 1
  })
}
