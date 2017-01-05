package bongi

import java.awt.Graphics
import java.awt.Color
import java.awt.Toolkit
import java.awt.Image
import scala.math._
import javax.sound.sampled.AudioSystem
import java.io.File


class Cup(x: Int, y: Int, lives: Int, val homeCup: Boolean) {
  
  private var livesLeft = lives
  
  private val cupX1 = x + 16
  private val cupX2 = x + 80
  private val cupY1 = y + 20
  private val cupY2 = y + 60
  
  def isAlive: Boolean = livesLeft > 0
  
  def cupsHere: Int = max(livesLeft, 0)
  
  private val cupIcon: Image = Toolkit.getDefaultToolkit.createImage("src/images/partycupsmall.png")
  val cupWidth = cupIcon.getWidth(BeerPong)
  val cupHeight = cupIcon.getHeight(BeerPong)
  
  private val file = new File("src/sounds/" + "dank_horn_420.wav")
  private val audioIn = AudioSystem.getAudioInputStream((file.toURI).toURL())
  private val clip = AudioSystem.getClip()
  
  
  def respawn = livesLeft = lives
  
  def spawnCup(g: Graphics) = {
    if(isAlive) {
      g.drawImage(cupIcon, x, y, BeerPong)
      g.setColor(Color.white)
      g.drawString(cupsHere.toString, x + 45, y + 150)
    }
    
    /*Hitbox*/
//    g.drawLine(cupX1, cupY1, cupX2, cupY1)//top part
//    g.drawLine(cupX1, cupY1, cupX1, cupY2)//left side
//    g.drawLine(cupX2, cupY1, cupX2, cupY2)//right side
//    g.drawLine(cupX1, cupY2, cupX2, cupY2)//bottom part
    
  }
  
  def touchesCup(coordX: Int, coordY: Int, ball: Ball) = {
    if(shouldDestroyCup(coordX, coordY, ball)) {
      ball.resetBall()
      livesLeft -= 1
      if(BeerPong.audioOn) {
       clip.close()
       clip.open(audioIn)
       clip.start()
      }
      true
    } else false
  }
  
  
  private def shouldDestroyCup(coordX: Int, coordY: Int, ball: Ball) = {
    coordX >= cupX1 && coordX <= cupX2 && 
    coordY >= cupY1 && coordY <= cupY2 && 
    isAlive && ball.getVelocities(1) >= 0 && 
    ((ball.turn == "home" && !homeCup) || ball.turn == "away" && homeCup)
  }
  
  
  
}