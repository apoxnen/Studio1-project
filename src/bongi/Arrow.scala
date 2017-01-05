package bongi

import java.awt.Graphics
import java.awt.Color
import scala.math._
import scala.util.Random

class Arrow {
  
  def drawVector(g: Graphics, b: Ball) = {
    /*''Vektori''*/
    if(b.gravity == 0) {
      g.setColor(Color.WHITE)
//      val speed = sqrt(pow(b.xCoord - BeerPong.mouseX, 2)/8f + pow(b.yCoord - BeerPong.mouseY, 2)/4f).toInt
//      g.setColor(new Color(min(255, speed), min(255, speed), 150))
      val arrowHeadX = ((b.xCoord * 2 - BeerPong.mouseX) - (b.xCoord - BeerPong.mouseX) / 6)
      val arrowHeadY = ((b.yCoord * 2 - BeerPong.mouseY) - (b.yCoord - BeerPong.mouseY) / 6)
      val arrowLengthX: Float = b.xCoord - arrowHeadX
      val arrowLengthY: Float = b.yCoord - arrowHeadY
      val arrowLength = hypot(arrowLengthX, arrowLengthY)
      /*Kulma vektorin x ja y komponenttien välillä*/
      val angle = atan2(arrowLengthY, arrowLengthX)
      val dAngle = 0.2
      val x1 = arrowHeadX + ((150 * cos(angle + dAngle))/3).toInt
      val x2 = arrowHeadX + ((150 * cos(angle - dAngle))/3).toInt
      val xPoints = Array(x1, x2, arrowHeadX)
      val y1 = arrowHeadY + ((150 * sin(angle + dAngle))/3).toInt
      val y2 = arrowHeadY + ((150 * sin(angle - dAngle))/3).toInt
      val yPoints = Array(y1, y2, arrowHeadY)
      
      g.drawLine(b.xCoord, b.yCoord, arrowHeadX, arrowHeadY)
      g.fillPolygon(xPoints, yPoints, 3)
      /*Speed*/
//      val speed2 = sqrt(pow(b.xCoord - BeerPong.mouseX, 2)/8f + pow(b.yCoord - BeerPong.mouseY, 2)/4f).toInt
//      g.setColor(new Color(min(1.2*speed2, 255).toInt, 150, 150))
//      g.drawString(speed.toString + " px/s", 
//                  (b.xCoord * 2 - BeerPong.mouseX) - (b.xCoord - BeerPong.mouseX) / 2 + 50, 
//                  (b.yCoord * 2 - BeerPong.mouseY) - (b.yCoord - BeerPong.mouseY) / 2 + 50)
    }
  }
  
  
  
}