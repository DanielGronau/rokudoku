/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rokudoku

import java.awt.BasicStroke
import java.awt.Graphics2D
import java.awt.RenderingHints

object GuiUtils {
   def alias(g2: Graphics2D) {
     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
   }

  def aliasText(g2: Graphics2D) {
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
  }

  def stroke(g2: Graphics2D, width: Float) {
    g2.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND))
  }
}
