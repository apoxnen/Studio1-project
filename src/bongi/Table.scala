package bongi

import java.awt.Graphics
import java.awt.Color

object Table {
  
  
  private val tableHeight = Constant.screenHeight * 2 / 3
  private val tableX = Constant.screenWidth / 6
  private val tableX1 = Constant.screenWidth * 5 / 6
  val width = Constant.screenWidth * 2 / 3
  val strokeWidth = 32
  val tableBottom = tableHeight - strokeWidth
  val tableTop = tableHeight
  
  
  /**Piirtää pöydän*/
  def setTable(g: Graphics) = {
    (0 to strokeWidth/2).foreach( (x: Int) => {
      if(x % 2 == 0) {
        g.setColor(Color.RED)
      } else {
        g.setColor(Color.WHITE)
      }
      g.drawLine(Constant.screenWidth / 2 + x, tableHeight, Constant.screenWidth / 2 + x, Constant.screenHeight + 2)
      g.drawLine(Constant.screenWidth / 2 - x, tableHeight, Constant.screenWidth / 2 - x, Constant.screenHeight + 2)
    })
    (0 to strokeWidth/2).foreach( (x: Int) => {
      if(x % 2 == 0) {
        g.setColor(Color.RED)
      } else {
        g.setColor(Color.WHITE)
      }
      g.drawLine(tableX, tableHeight + x, tableX1 , tableHeight + x)
      g.drawLine(tableX, tableHeight - x, tableX1 , tableHeight - x)
      
    })
          /*Hitbox*/
//      g.setColor(Color.WHITE)
//      g.drawLine(tableX        , tableHeight + strokeWidth, tableX        , tableHeight - strokeWidth)
//      g.drawLine(tableX        , tableHeight + strokeWidth, tableX + width, tableHeight + strokeWidth)
//      g.drawLine(tableX        , tableHeight - strokeWidth, tableX + width, tableHeight - strokeWidth)
//      g.drawLine(tableX + width, tableHeight + strokeWidth, tableX + width, tableHeight - strokeWidth)
  }
  
  def interLapsWithTable(x: Double, y: Double) = {
    x >= tableX && x <= width + tableX && //x
    y <= tableHeight + strokeWidth && y >= tableHeight - strokeWidth//y
  }
  
  
  override def toString = "Beer pong table"
  
  
  
  
}