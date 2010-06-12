/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rokudoku

import com.sun.awt.AWTUtilities
import java.awt.Color
import java.awt.Polygon
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JRootPane
import javax.swing.SwingUtilities


/*  / \
 * |   |
 *  \ /
 * fx: half width of a hex cell
 * fy: 1/4 of the height of a hex cell
 *
 */

class HexWindow private(val fx: Int, val fy: Int) extends JFrame {

  var pos = Solver.make(List(Solver.OneValuePossible, Solver.NowhereElsePossible))
  //var pos = Solver.make(List(Solver.OneValuePossible, Solver.NowhereElsePossible, Solver.BruteForce))
  //var pos = Solver.make(List(Solver.BruteForce))

  val colors = List(0xFFD700, 0xADD8E6, 0xDDA0DD, 0xEEE8AA, 0xDEB887, 0xFFA500,
                    0x90EE90).map(new Color(_))
  val cells = Map[Pos, HexCell]() ++ Grid.regions.zip(colors).flatMap(
    t => t._1.map(c => (c, new HexCell(fx, fy, t._2))))
  val coords = Array(600,701,800,901,1000,1101,1200,1301,1303,1404,1406,1507,1509,
                     1410, 1412,1513,1515,1616,1618,1519,1521,1422,1321,1222,
                     1224,1125,1127,1028,927,828,727,725,624,525,424,325,224,
                     222,121,119,218,216,115,113,12,10,109,107,206,307,406,404,
                     503,501)
  val coordsX = coords.map(n => n / 100 * fx)
  val coordsY = coords.map(n => n % 100 * fy)

  setSize(new java.awt.Dimension(16*fx+1, 28*fy+1))
  setLocationRelativeTo(null)
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  setUndecorated(true)
  getRootPane.setWindowDecorationStyle(JRootPane.NONE)
  setVisible(true)
  val shape = new Polygon(coordsX, coordsY, coords.length)
  if (AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.PERPIXEL_TRANSPARENT)) {
    AWTUtilities.setWindowShape(this, shape)
  }
  val panel = new HexPanel(fx, fy, 5)
  panel.add(new MenuButton(fx, fy), 7, 0)
  panel.add(new ExitButton(fx, fy), 8, 0)
  cells.foreach{t => 
    t._2 addKeyListener new KeyAdapter{override def keyPressed(ke: KeyEvent) =
      HexWindow.this.keyPressed(ke, t._1)}
    panel.add(t._2, t._1.x, t._1.y)}
  setContentPane(panel)
  setGlassPane(MenuPane)
  invalidate
  validate
  repaint()

  if (! AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.PERPIXEL_TRANSPARENT)) {
    JOptionPane.showMessageDialog(this, "<html>I'm deeply sorry that Rokudoku looks ridiculous now,<br>" +
    "but your operating system doesn't support transparency.</html>", "Warning", JOptionPane.WARNING_MESSAGE);
  }

  pos.values.foreach{t => cells(t._1).value = (t._2 + '0').toChar
                     cells(t._1).editable = false}
  cells(Pos(4,4)).requestFocus

  def keyPressed(ke: KeyEvent, c: Pos) {
    val hc = cells(c)
    if (ke.getKeyCode == KeyEvent.VK_LEFT) 
      cells.get(rokudoku.Pos(c.x - 1, c.y)).foreach{ _.requestFocus }
    else if (ke.getKeyCode == KeyEvent.VK_RIGHT)
      cells.get(rokudoku.Pos(c.x + 1, c.y)).foreach{ _.requestFocus }
    else if (ke.getKeyCode == KeyEvent.VK_UP)
      cells.get(rokudoku.Pos(c.x + c.y % 2, c.y - 1)).foreach{ _.requestFocus }
    else if (ke.getKeyCode == KeyEvent.VK_DOWN)
      cells.get(rokudoku.Pos(c.x - (1 - c.y % 2), c.y + 1)).foreach{ _.requestFocus }
    else if (hc.editable) {
      val ch = ke.getKeyChar
      if ('1' <= ch && ch <= '7') {
        pos.add(c, ch - '0').foreach { newPos =>
          hc.value = ch
          hc.repaintMe
          pos = newPos
        }
      } else if (ke.getKeyCode == KeyEvent.VK_DELETE && hc.value != ' ') {
          hc.value = ' '
          hc.repaintMe
          pos = Board(pos.values - c)
      }
    }
  }
}

object HexWindow {

  def apply(factor : Int) {
    JFrame.setDefaultLookAndFeelDecorated(true);
    SwingUtilities.invokeLater(new Runnable{ def run: Unit = new HexWindow(7*factor, 4*factor)})
  }

} 
