/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rokudoku

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Polygon
import java.awt.RadialGradientPaint
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.Point2D
import javax.swing.JComponent

abstract class HexComponent(fx: Int, fy:Int) extends JComponent {

  val polygon = new Polygon(Array(0, fx, 2*fx, 2*fx, fx, 0), Array(fy,0,fy,3*fy,4*fy,3*fy), 6)

  setOpaque(false)
  setSize(2*fx, 4*fy)
  addMouseListener(new MouseAdapter {
      override def mouseClicked(me: MouseEvent) = if(polygon.contains(me.getX, me.getY))
        HexComponent.this.mouseClicked(me)
      override def mousePressed(me: MouseEvent) = if(polygon.contains(me.getX, me.getY))
        HexComponent.this.mousePressed(me)
      override def mouseReleased(me: MouseEvent) = if(polygon.contains(me.getX, me.getY))
        HexComponent.this.mouseReleased(me)
  })

  override def paint(g: Graphics) {
    val gp = new RadialGradientPaint(new Point2D.Double(fx + fy, fy), 3*fy,
     Array(0f, 1f), Array(getForeground, getBackground))
    g.asInstanceOf[Graphics2D].setPaint(gp)
    g.fillPolygon(polygon);
    paintComponent(g)
  }

  def mouseClicked(me: MouseEvent): Unit = {}
  def mousePressed(me: MouseEvent): Unit = {}
  def mouseReleased(me: MouseEvent): Unit = {}

}
