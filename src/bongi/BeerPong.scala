package bongi

import scala.math._
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.Image
import java.awt.MouseInfo
import java.awt.Toolkit
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.UIManager    //voi olla hyödyllinen joskus
import java.awt.event.KeyListener
import java.net.URL
import javax.sound.sampled.AudioSystem
import java.io.File
import scala.util.Random
import java.awt.event.KeyEvent
import java.awt.event.ActionListener
import java.awt.event.ActionEvent


//import java


/**'''BeerPong Game of The Year Edition'''*/
object BeerPong extends JPanel with MouseListener with KeyListener {
  
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
  
  private var state = State.MENU
  private val file = new File("src/sounds/music.wav")
  private val audioIn = AudioSystem.getAudioInputStream((file.toURI).toURL())
  private val clip = AudioSystem.getClip()
  clip.open(audioIn)
  //clip.start()
  private var muted = true
  private var showInstructions = true
  private var mlg = false
  private var gameOn = true
  
  private val icon: Image = Toolkit.getDefaultToolkit.createImage("src/images/thumbnail.png")
  private val partyPeople: Image = Toolkit.getDefaultToolkit.createImage("src/images/ppl.png")
  private val screenWidth = Constant.screenWidth
  private val screenHeight = Constant.screenHeight
  private val frameRate = 60
  setPreferredSize(new Dimension(screenWidth, screenHeight))
  val ball = new Ball
  
  val homeCups: Array[Cup] = Array.tabulate(4)( (x: Int) => new Cup(   60 + screenWidth/2 + (x + 1) * 65, Table.tableTop - 115, x + 1, true ) )
  val awayCups: Array[Cup] = Array.tabulate(4)( (x: Int) => new Cup( -160 + screenWidth/2 - (x + 1) * 65, Table.tableTop - 115, x + 1, false) )
  
  val gameThread: Thread = new Thread() {
    override def run(): Unit = {
      while(gameOn) {
        if(state == State.GAME) {
          ball.updateBallPosition()
          repaint()
          /*Some mlg shit*/
          if(checkForCollision) {
            var x = 0
            val length = 110
            while(x < length) {
             mlg = if (x >= length - 1) false else true
              repaint()
              x += 1
              Thread.sleep(1000 / frameRate)
              if (x >= 59) mlg = false
          }
          if (ball.turn == "home") Thread.sleep(1500)
          }
        } else {
            repaint()
          }
          Thread.sleep(1000 / frameRate)
      }
    } 
  }
  
  private def checkForCollision = {
    var truth = false
    homeCups.foreach((x: Cup) =>if(x.touchesCup(ball.xCoord, ball.yCoord, ball)) truth = true)
    awayCups.foreach((x: Cup) =>if(x.touchesCup(ball.xCoord, ball.yCoord, ball)) truth = true)
    truth
  }
  
  /**Kursorin x-koordinaatti ikkunassa*/
  def mouseX: Int = MouseInfo.getPointerInfo.getLocation.getX.toInt - this.getLocationOnScreen.getX.toInt
  /**Kursorin y-koordinaatti ikkunassa*/
  def mouseY: Int = MouseInfo.getPointerInfo.getLocation.getY.toInt - this.getLocationOnScreen.getY.toInt
  
  private var epilepsy = true
  /**Paints everything on the screen*/
  override def paintComponent(g: Graphics) = {
    super.paintComponent(g)
    if(state == State.GAME) {
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20))
    /*Background*/
      g.setColor(new Color(245,222,179))
      g.fillRect(0, 0, screenWidth + 20, screenHeight + 20)
      setBackgroundColor(g)
      g.fillRect(1, 3, screenWidth + 14, screenHeight)
      /*Pallo*/
      ball.paintBall(g)
      /*Pöytä*/
      Table.setTable(g)
      /*Kupit*/
      homeCups.foreach(_.spawnCup(g))
      awayCups.foreach(_.spawnCup(g))
      /*Vektori*/
      val arrow = new Arrow
      arrow.drawVector(g, ball)
      printScoreOnScreen(g)
    } else if(state == State.MENU){
      Menu.paintMenu(g, epilepsy, muted, this, mouseX, mouseY)
      /*Settings*/
    } else {
      Settings.paintSettings(g, muted, epilepsy, this, mouseX, mouseY)
    }
  }
  
  override def mouseEntered(e: MouseEvent) = {
    
  }
  override def mouseExited(e: MouseEvent) = {
    
  }
  
  private def setBackgroundColor(g: Graphics) = {
    if(mlg && epilepsy) {
      setRandomColor(g)
    } else {
      g.setColor(Color.BLACK)
    }
  }
  
  /**Pallon ampuminen*/
  override def mouseClicked(e: MouseEvent) = {
    if(state == State.GAME) {
      val velocities = ball.getVelocities
      if(velocities(0) == 0 && velocities(1) == 0) {
        ball.gravity = Constant.gravity
        ball.setBallSpeed((ball.xCoord - e.getX) / 8f, (ball.yCoord - e.getY) / 4f)
      }
    } else if(state == State.MENU) {
      val btn = Menu.whichButton(mouseX, mouseY)
      if(btn == "m") {
        muted = !muted
      } else if(btn == "1") {
        if(didGameEnd) resetCups
        state = State.GAME
      } else if(btn == "2") {
        state = State.GAME
      } else if(btn == "3") {
        state = State.SETTINGS
      } else {
        //clicking anywhere except the buttons
      }
      
      //state == State.SETTINGS
    } else {
      val btn = Settings.whichButton(mouseX, mouseY)
      if(btn == "1") {
        
      } else if(btn == "2") {
        epilepsy = !epilepsy
      } else if(btn == "3") {
        AI.adjustDifficulty()
      } else if(btn == "4") {
        state = State.MENU
      }
    }
  }
  
  def audioOn = !muted
  
  private def didGameEnd: Boolean =  homeCups.forall(!_.isAlive) || awayCups.forall(!_.isAlive)
  
  /**Index 0 is player one cups and 1 is player 2 cups*/
  private def getPlayerScores = {
    var awaySum = 0
    awayCups.foreach( awaySum += _.cupsHere )
    var homeSum = 0
    homeCups.foreach( homeSum += _.cupsHere )
    Array(homeSum, awaySum)
  }
  
  /**Prints the amount of cups and each players scores*/
  private def printScoreOnScreen(g: Graphics) = {
    g.setColor(Color.white)
    g.drawString(getPlayerScores(1).toString + " cups left", 45, 50)
    g.drawString(getPlayerScores(0).toString + " cups left", screenWidth - 155, 50)
    /*Pojot*/
    g.drawString("Score: "+ (10 - getPlayerScores(0)).toString, 45, 80)
    g.drawString("Score: " + (10 - getPlayerScores(1)).toString, screenWidth - 155, 80)
  }
  
  private def setRandomColor(g: Graphics) = g.setColor(new Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
  
  private def resetCups = {
    homeCups.foreach(_.respawn)
    awayCups.foreach(_.respawn)
  }
  
  override def keyPressed(k: KeyEvent): Unit = {
    if(k.getKeyCode() == KeyEvent.VK_ESCAPE) {
      if(state == State.MENU) state = State.GAME
      else state = State.MENU
    }
  }
  override def keyReleased(k: KeyEvent): Unit = {
    
  }
  override def keyTyped(k: KeyEvent): Unit = {
    
  }
  
  
  def main(args: Array[String]) = {
    val frame = new JFrame("Beer pong")
    frame.setIconImage(icon)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setContentPane(this)
    frame.pack()
    frame.setResizable(false)
    frame.setLocationRelativeTo(null)
    frame.addMouseListener(this)
    frame.addKeyListener(this)
    frame.setVisible(true)
    gameThread.run()
  }
  
  override def mouseReleased(e: MouseEvent) = {
    
  }
  
  override def mousePressed(e: MouseEvent) = {
    
  }
  
  
}