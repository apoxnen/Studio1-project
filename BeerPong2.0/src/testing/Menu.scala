package testing

import javax.swing.JFrame
import javax.swing.JMenuBar
import javax.swing.JMenu
import javax.swing.JMenuItem
import java.awt.Event
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

object Menu extends JFrame {
  
  
  val menubar = new JMenuBar
  val file = new JMenu
  val menuItem = new JMenuItem
  
  setJMenuBar(menubar)
  menubar.add(file)
  
  val exit = new JMenuItem("Exit")
  file.add(exit)
  
  val event = new event
  class event extends ActionListener {
    def actionPerformed(e: ActionEvent) = {
    System.exit(0)
    }
  }
  exit.addActionListener(event)
  
  def main(args: Array[String]) = {
    val frame = new JFrame("MenuTesti")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.pack()
    frame.setResizable(false)
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
  }
}

