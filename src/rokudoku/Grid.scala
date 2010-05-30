/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rokudoku



case class Pos(x: Int, y: Int)

case class Board(values : Map[Pos,Int]) {
  def this() = this(Map())

  def add(c : Pos, value : Int ) = {
    if (values.keySet.contains(c) ||
        Grid.groups.filter(_.contains(c)).flatten.exists(values.get(_) == Some(value))) None
    else Some(Board(values + (c -> value)))
  }

  def remove(c : Pos) = if (values contains c) Board(values - c) else this

  def empty = values.size == 0
  def full = values.size == 49
}

object Grid {

  val regions = {
    def region(x : Int, y: Int) = List(Pos(x, y - 1), Pos(x + 1, y - 1),
      Pos(x - 1, y), Pos(x, y), Pos(x + 1, y), Pos(x - 1, y + 1), Pos(x, y + 1))
    Vector(region(5, 1), region(7, 2), region(2, 3), region(4, 4),
       region(6, 5), region(1, 6), region(3,7))
  }

  val cells = regions.flatten

  val xDiagonals = {
    def diag(x:Int, y:Int) = for(xk <- x to (x+6)) yield Pos(xk, y)
    Vector(diag(2,2), diag(1,3), diag(1,4), diag(1,5), diag(0,6))
  }

  val yDiagonals = {
    def diag(x:Int, y:Int) = for(yk <- y to (y+6)) yield Pos(x, yk)
    Vector(diag(2,2), diag(3,2), diag(4,1), diag(5,0),diag(6,0))
  }

  val zDiagonals = {
    def diag(x:Int, y:Int) = for(d <- 0 to 6) yield Pos(x-d, y+d)
    Vector(diag(6,0), diag(6,1), diag(7,1), diag(8,1),diag(8,2))
  }

  def groups = Vector[Seq[Pos]]() ++ regions ++ xDiagonals ++ yDiagonals ++ zDiagonals

}
