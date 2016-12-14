package bongi

import java.awt.Graphics
import java.awt.Color

class Ball {
  
  private val playerOneStartingPositionX = (Constant.screenWidth*6/29)
  private val playerOneStartingPositionY = (Constant.screenHeight*15/34)
  private val playerTwoStartingPositionX = Constant.screenWidth - playerOneStartingPositionX
  private val playerTwoStartingPositionY = playerOneStartingPositionY
  
  var dx: Double = 0
  var dy: Double = 0
  var xCoord: Int = playerOneStartingPositionX
  var yCoord: Int = playerOneStartingPositionY
  private var dt: Double = 0.6
  var gravity: Double = 0
  private var hasHitTable = false
  
  private var homeTurn = false
  
  def turn = {
    if(homeTurn) "home"
    else "away"
  }
  
  def updateBallPosition() = {
    
    /*Leveyden muutokset*/
    if(gravity != 0) {
      if(xCoord + dx > Constant.screenWidth - Constant.radius - 1) {
        xCoord = Constant.radius//Constant.screenWidth - Constant.radius - 1
        dx = dx//-dx*Constant.energyloss
      } 
      else if(xCoord + dx < Constant.radius) {
        xCoord = Constant.screenWidth - Constant.radius - 1//Constant.radius
        dx = dx//-dx*Constant.energylossX
      }
      else {
        xCoord += dx.toInt
      }
      /*Kitkan vaikutus*/
      if(yCoord == Constant.screenHeight - Constant.radius - 1) {
        dx = dx * Constant.friction
        if(dx.abs < 0.3) {
          dx = 0
        }
      }
      /*Korkeuden muutokset*/
      if(yCoord > Constant.screenHeight - Constant.radius - 1) {
        yCoord = Constant.screenHeight - Constant.radius - 1
        dy = -dy * Constant.energyloss
      }
      /*Katto*/
//      else if(yCoord + dy < Constant.radius) {
//        yCoord = Constant.radius
//        dy = -dy
//      }
      /*Pöytään osuminen*/
      else if(Table.interLapsWithTable(xCoord, yCoord) && !hasHitTable) {
        hasHitTable = true
        yCoord = Table.tableTop - Table.strokeWidth/2 + 5
        dx = dx * Constant.friction
        dy = -dy * Constant.energyloss * 1.2 //pomppii paremmin pöydällä
      }
      else {
        dy += Constant.gravity * dt
        yCoord += (dy*dt + 0.5*Constant.gravity*dt*dt).toInt
        if(dy < 0 && yCoord < Table.tableTop - 15) {
          hasHitTable = false
        }
      }
    }
    if(speed < 1.7) {
      if(Constant.gravity != 0 && yCoord >= Constant.screenHeight - Constant.radius - 1) {
        Thread.sleep(250)
        resetBall()
      }
    }
        
  }
  
  
  def resetBall() = {
    if(homeTurn) {
      xCoord = playerOneStartingPositionX
      yCoord = playerOneStartingPositionY
      gravity = 0
      dx = 0
      dy = 0
      hasHitTable = false
      homeTurn = false
    } else {
      xCoord = playerTwoStartingPositionX
      yCoord = playerTwoStartingPositionY
      dx = 0
      dy = 0
      gravity = 0
      hasHitTable = false
      homeTurn = true
    }
  }
  
  
  /**Pallon maalaus metodi*/
  def paintBall(g: Graphics) = {
    g.setColor(Color.WHITE)
    g.fillOval((xCoord - Constant.radius).toInt, (yCoord - Constant.radius).toInt, (2 * Constant.radius).toInt, (2 * Constant.radius).toInt)
  }
  
  
  
  def speed: Float = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)).abs.toFloat
  
  
  
  
}