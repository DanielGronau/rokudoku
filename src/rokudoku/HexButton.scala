/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rokudoku

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Point
import java.awt.RenderingHints
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.swing.JFrame
import javax.swing.SwingUtilities
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.event.MouseEvent

abstract class HexButton(fx: Int, fy: Int) extends HexComponent(fx, fy)  {
  /*this.addMouseMotionListener(new MouseMotionAdapter {
      override def mouseDragged(me : MouseEvent) {
         
      }
  })

  private var mouseStart = new Point(0,0)

  override def mousePressed(me: MouseEvent) {
    mouseStart = me.getLocationOnScreen
  }

  override def mouseReleased(me: MouseEvent) {
    val dx = me.getLocationOnScreen.x - mouseStart.x
    val dy = me.getLocationOnScreen.y - mouseStart.y
    if (dx*dx + dy*dy > 50) {
      SwingUtilities.getRoot(this) match {
        case jf: JFrame => val loc = new Point(jf.getLocation.x + dx, jf.getLocation.y + dy)
          jf.setLocation(loc)
      }
    }
  }*/
}



class MenuButton(fx: Int, fy: Int) extends HexButton(fx, fy) {
  this.setBackground(Color.GREEN.darker)
  this.setForeground(Color.GREEN)
  override def mouseClicked(me: MouseEvent) {
    /*SwingUtilities.getRoot(this) match {
     case jf: JFrame => jf.dispose
     }*/
  }

  override def paintComponent(g: Graphics) {
    val g2 = g.asInstanceOf[Graphics2D]
    g2.setColor(Color.BLACK)
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND))
    g2.drawRoundRect(fx/2, fy, fx, 2*fy, fy, fy)
    g2.drawLine(fx/2 + fy/2, fy + fy/2, 3*fx/2 - fy/2, fy + fy/2)
    g2.drawLine(fx/2 + fy/2, 2*fy, 3*fx/2 - fy/2, 2*fy)
    g2.drawLine(fx/2 + fy/2, 3*fy - fy/2, 3*fx/2 - fy/2, 3*fy - fy/2)
  }
}


class ExitButton(fx: Int, fy: Int) extends HexButton(fx, fy) {
  this.setBackground(Color.RED.darker)
  this.setForeground(Color.RED)
  override def mouseReleased(me: MouseEvent) {
    SwingUtilities.getRoot(this) match {
      case jf: JFrame => jf.dispose
    }
  }

  override def paintComponent(g: Graphics) {
    val g2 = g.asInstanceOf[Graphics2D]
    g2.setColor(Color.BLACK)
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND))
    g2.drawRoundRect(fx/2, fy, fx, 2*fy, fy, fy)
    g2.drawLine(fx/2 + fy/2, fy + fy/2, 3*fx/2 - fy/2, 3*fy - fy/2)
    g2.drawLine(fx/2 + fy/2, 3*fy - fy/2, 3*fx/2 - fy/2, fy + fy/2)
  }
}



