/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rokudoku

import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Polygon
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import GuiUtils._


class HexCell(fx: Int, fy: Int, col: Color) extends HexComponent(fx, fy) {

  setBackground(col.darker)
  setForeground(col)
  setFont(new Font("Arial", Font.BOLD, 30))
  this.addFocusListener(new FocusListener {
      def focusGained(e: FocusEvent) = repaintMe
      def focusLost(e: FocusEvent) = repaintMe
  })

  this.addKeyListener(new KeyAdapter{
       override def keyPressed(ke: KeyEvent) {
         
       }
  })

  def repaintMe {
        invalidate
        validate
        repaint()
  }

  var value = ' '

  var editable = true

  override def mouseReleased(me: MouseEvent) {
    requestFocus
  }

  override def paintComponent(g: Graphics) {
    val g2 = g.asInstanceOf[Graphics2D]
    g2.setColor(if (editable) Color.BLUE else Color.BLACK)
    aliasText(g2)
    val rect = g2.getFontMetrics.getStringBounds(value.toString, g2)
    g2.drawString(value.toString, (fx - rect.getWidth / 2).toInt, (2*fy + rect.getHeight / 2.7).toInt)
    if(hasFocus) {
      g2.setColor(Color.YELLOW)
      alias(g2)
      stroke(g2, 4)
      g2.drawPolygon(new Polygon(Array(0+2, fx, 2*fx-2, 2*fx-2, fx, 0+2),
                           Array(fy+1,0+2,fy+1,3*fy-1,4*fy-2,3*fy-1), 6))
    }
  }
}

