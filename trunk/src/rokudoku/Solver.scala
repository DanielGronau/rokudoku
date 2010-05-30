/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rokudoku

object Solver {

  import scala.util.Random

  Random.setSeed(System.currentTimeMillis)

  val values = (1 to 7).toList

  def solve(pos : Board) : Option[Board] = Grid.cells.find(! pos.values.keySet.contains(_)) match {
    case None => Some(pos) //pos is full
    case Some(cell) => Random.shuffle(values).foreach{ i =>
        val p = pos.add(cell, i)
        if (! p.isEmpty) {
          val q = solve(p.get)
          if(! q.isEmpty) return q
        }
      }
      None
  }

  def make(rules: Seq[Rule]) = {
    def reduce(c : Pos, pos : Board) : Option[Board] = {
      val newPos = Board(pos.values - c)
      if (rules.find(_.find(newPos, c) != None) != None) Some(newPos)
      else None
    }

    def loop(pos : Board) : Board = {
      val newPos = Random.shuffle(pos.values.keys).foldLeft(None : Option[Board])((opt, cell) =>
        if (opt != None) opt else reduce(cell, pos))
      if (newPos == None) pos else loop(newPos.get)
    }

    loop (solve(Board(Map())).get)
  }

  trait Rule {
    def find(pos : Board, c : Pos) : Option[Int]
  }

  object BruteForce extends Rule {
    def find(pos : Board, c : Pos) : Option[Int] =
      values.map(v => pos.add(c,v).map(p => solve(p)).getOrElse(None)).filter(_ != None) match {
        case List(Some(p)) => Some(p.values(c))
        case _ => None
      }
  }

  object OneValuePossible extends Rule {
    def find(pos : Board, c : Pos) : Option[Int] =
    values.map(v => pos.add(c,v)).filter(_ != None) match {
        case List(Some(p)) => Some(p.values(c))
        case _ => None
      }
  }

  object NowhereElsePossible extends Rule {
    def find(pos : Board, c : Pos) : Option[Int] = {
      val groups = Grid.groups.filter(_.contains(c))
      values.find(v => pos.add(c,v) != None &&
                  groups.exists(_.filter(_ != c).forall(pos.add(_,v) == None)))
    }
  }

}
