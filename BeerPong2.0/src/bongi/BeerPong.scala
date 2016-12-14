package bongi

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.Image
import java.awt.MouseInfo
import java.awt.Toolkit
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import scala.math._
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.UIManager
import javax.swing.JPopupMenu
import java.awt.event.KeyListener
import java.awt.Button
import java.net.MalformedURLException
import java.net.URL


/**'''BeerPong Game of The Year Edition'''*/
object BeerPong extends JPanel with MouseListener {
  
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
  
  private val icon: Image = Toolkit.getDefaultToolkit.createImage("thumbnail.png")
  private val partyPeople: Image = Toolkit.getDefaultToolkit.createImage("ppl.png")
  
  private val screenWidth = Constant.screenWidth
  private val screenHeight = Constant.screenHeight
  private var gameOn = true
  /*paikan muutos x- ja y- suunnassa*/
  private val frameRate = 60
  
  private val ball = new Ball
  
  val homeCups: Array[Cup] = Array.tabulate(4)( (x: Int) => new Cup(   60 + screenWidth/2 + (x + 1) * 65, Table.tableTop - 115, x + 1) )
  val awayCups: Array[Cup] = Array.tabulate(4)( (x: Int) => new Cup( -160 + screenWidth/2 - (x + 1) * 65, Table.tableTop - 115, x + 1) )
  
  this.setPreferredSize(new Dimension(screenWidth, screenHeight))
  
  val gameThread: Thread = new Thread() {
    override def run(): Unit = {
      while(gameOn) {
        ball.updateBallPosition()
        repaint()
        /*Tarkistetaan osuuko pallo*/
        homeCups.foreach(_.touchesCup(ball.xCoord, ball.yCoord, ball))
        awayCups.foreach(_.touchesCup(ball.xCoord, ball.yCoord, ball))
        Thread.sleep(1000 / frameRate)
      }
    } 
  }
  /**Kursorin x-koordinaatti ikkunassa*/
  private def mouseX: Int = MouseInfo.getPointerInfo.getLocation.getX.toInt - this.getLocationOnScreen.getX.toInt
  /**Kursorin y-koordinaatti ikkunassa*/
  private def mouseY: Int = MouseInfo.getPointerInfo.getLocation.getY.toInt - this.getLocationOnScreen.getY.toInt
  
//  private def gameOn = !homeCups.forall(!_.isAlive) && !awayCups.forall(!_.isAlive)
    
  
  /**Paints everything on the screen*/
  override def paintComponent(g: Graphics) = {
    super.paintComponent(g)
    g.setFont(new Font("TimesRoman", Font.ITALIC, 20))
    /*Background*/
    g.setColor(Color.BLACK)
    g.fillRect(3, 3, screenWidth, screenHeight)
//    g.drawImage(partyPeople, screenWidth*2/11, screenHeight*4/15, this)
    /*Pallo*/
    ball.paintBall(g)
    /*Pöytä*/
    Table.setTable(g)
    /*Kupit*/
    homeCups.foreach(_.spawnCup(g))
    awayCups.foreach(_.spawnCup(g))
    /*Vektori*/
    drawVector(g)
    
    printScoreOnScreen(g)
  }
  
  /**Pallon ampuminen*/
  override def mouseClicked(e: MouseEvent) = {
    if(ball.dx == 0 && ball.dy == 0) {
      ball.gravity = Constant.gravity
      ball.dx = (ball.xCoord - e.getX) / 8f
      ball.dy = (ball.yCoord - e.getY) / 4f
    }
  }
  
  private def didGameEnd: Boolean =  {
    if(homeCups.forall(!_.isAlive) || awayCups.forall(!_.isAlive)) {
      true
    } else {
      false
    }
  }
  
  private def drawVector(g: Graphics) = {
    /*''Vektori''*/
    if(ball.gravity == 0) {
      if(ball.turn == "home") {
        g.setColor(Color.RED)
      } else {
        g.setColor(Color.YELLOW)
      }
      val arrowHeadX = ((ball.xCoord * 2 - mouseX) - (ball.xCoord - mouseX) / 6)
      val arrowHeadY = ((ball.yCoord * 2 - mouseY) - (ball.yCoord - mouseY) / 6)
      g.drawLine(ball.xCoord, ball.yCoord, arrowHeadX, arrowHeadY)
      val arrowDeltaX: Float = ball.xCoord - arrowHeadX
      val arrowDeltaY: Float = ball.yCoord - arrowHeadY
      val arrowLength = hypot(arrowDeltaX, arrowDeltaY)
      /*Tarvitsee remppaa ehkä*/
      val xPoints = Array(arrowHeadX - 10, arrowHeadX + 10, ball.xCoord * 2 - mouseX)
      val yPoints = Array( arrowHeadY - 10, arrowHeadY + 10, ball.yCoord * 2 - mouseY)
      
      g.drawLine(arrowHeadX - 10, arrowHeadY - 10, arrowHeadX + 10, arrowHeadY + 10)
      g.fillPolygon(xPoints, yPoints, 3)
      /*Speed*/
      g.drawString((sqrt(pow(ball.xCoord - mouseX, 2)/8f + pow(ball.yCoord - mouseY, 2)/4f)).toInt.toString + " px/s", 
                   (ball.xCoord * 2 - mouseX) - (ball.xCoord - mouseX) / 2, 
                   (ball.yCoord * 2 - mouseY) - (ball.yCoord - mouseY) / 2)
    }
  }
  
  
  /**Prints the amount of cups and each players scores*/
  private def printScoreOnScreen(g: Graphics) = {
    /*Score*/
    g.setColor(Color.white)
    var awaySum = 0
    awayCups.foreach( awaySum += _.cupsHere )
    g.drawString(awaySum.toString + " cups left", 45, 50)
    var homeSum = 0
    homeCups.foreach( homeSum += _.cupsHere )
    g.drawString(homeSum.toString + " cups left", screenWidth - 155, 50)
    /*Pojot*/
    g.drawString("Score: "+ (10 - homeSum).toString, 45, 80)
    g.drawString("Score: " + (10 - awaySum).toString, screenWidth - 155, 80)
    
  }
  
  
  def main(args: Array[String]) = {
    
    val frame = new JFrame("Beerpong Game of The Year Edition")
    
    frame.setIconImage(icon)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setContentPane(this)
    frame.pack()
    frame.setResizable(false)
    frame.setLocationRelativeTo(null)
    frame.addMouseListener(this)
    frame.setVisible(true)
    
    gameThread.run()
    
  }
  
  
  override def mouseExited(e: MouseEvent) = {
    
  }
  
  override def mouseEntered(e: MouseEvent) = {
    
  }
  
  override def mouseReleased(e: MouseEvent) = {
    
  }
  
  override def mousePressed(e: MouseEvent) = {
    
  }
  
  
}