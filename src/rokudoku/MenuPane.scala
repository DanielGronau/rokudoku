/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rokudoku

import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.KeyAdapter
import java.awt.event.MouseAdapter
import java.awt.event.MouseMotionAdapter
import javax.swing.JComponent

object MenuPane extends JComponent {

  setLayout(null)

  addMouseListener(new MouseAdapter{})
  addMouseMotionListener(new MouseMotionAdapter{})
  addKeyListener(new KeyAdapter{})

  addComponentListener(new ComponentAdapter{
      override def componentShown(evt: ComponentEvent) {
        requestFocusInWindow()
      }
   })

  setFocusTraversalKeysEnabled(false)

  override def paintComponent(g: Graphics) {
     val g2 = g.asInstanceOf[Graphics2D]
     val clip = g2.getClipBounds
     val alpha = AlphaComposite.SrcOver.derive(0.65f)
     g2.setComposite(alpha)
     g2.setColor(Color.white)
     g2.fillRect(clip.x, clip.y, clip.width, clip.height)
   }
}
