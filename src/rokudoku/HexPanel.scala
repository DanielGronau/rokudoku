/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rokudoku

import javax.swing.JPanel

class HexPanel(fx: Int, fy: Int, xOffset: Int) extends JPanel {

  setLayout(null)

  def add(hc: HexComponent, xk: Int, yk: Int) {
    add(hc)
    hc.setLocation((-xOffset + 2*xk + yk)*fx, 3*yk*fy)
  }

}