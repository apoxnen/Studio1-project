package bongi

import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Image
import java.awt.Toolkit
import java.awt.image.ImageObserver

object Settings {
  
  
  private val button1 = Array(Constant.screenWidth/2 - 100, Constant.screenHeight/2 - 255, 200, 100)
  private val button2 = Array(Constant.screenWidth/2 - 100, Constant.screenHeight/2 - 105, 200, 100)
  private val button3 = Array(Constant.screenWidth/2 - 100, Constant.screenHeight/2 +  45, 200, 100)
  private val button4 = Array(Constant.screenWidth/2 - 100, Constant.screenHeight/2 + 195, 200, 100)
  private val defaultColor = new Color(72,209,204)
  private val hoveringColor = new Color(70,130,180)
  private var button1Color = defaultColor
  private var button2Color = defaultColor
  private var button3Color = defaultColor
  private var button4Color = defaultColor
  
  def paintSettings(g: Graphics, muted: Boolean, epilepsy: Boolean, i: ImageObserver, mouseX: Int, mouseY: Int) = {
    g.setColor(Color.BLACK)
    g.fillRect(0, 0, Constant.screenWidth + 30, Constant.screenHeight + 30)
    menuButtonColors(mouseX, mouseY)
    g.setColor(button1Color)
    g.fillRect(button1(0), button1(1), button1(2), button1(3))
    g.setColor(button2Color)
    g.fillRect(button2(0), button2(1), button2(2), button2(3))
    g.setColor(button3Color)
    g.fillRect(button3(0), button3(1), button3(2), button3(3))
    g.setColor(button4Color)
    g.fillRect(button4(0), button4(1), button4(2), button4(3))
    g.setColor(Color.WHITE)
    g.setFont(new Font("Arial", Font.PLAIN, 50))
    g.drawString("KEK", button1(0) + 50, button1(1) + 50)
    
    g.setFont(new Font("Arial", Font.PLAIN, 35))
    val info = if(epilepsy) "on" else "off"  
    g.drawString("Epilepsy " + info, button2(0) + 12, button2(1) + 65)
    g.setFont(new Font("Arial", Font.PLAIN, 9))
    g.drawString("Recommended turned off if you have epilepsy", button2(0) + 2, button2(1) + 97)
    
    val difficult = "Difficulty: " + AI.difficulty.toString
    g.setFont(new Font("Arial", Font.PLAIN, 35))
    g.drawString(difficult, button3(0) + 15, button3(1) + 65)
    
    g.setFont(new Font("Arial", Font.PLAIN, 50))
    g.drawString("Back", button4(0) + 40, button4(1) + 65)
//    g.drawString("New Game", button1(0) + 20, button1(1) + 65)
//    
//    g.setFont(new Font("Arial", Font.PLAIN, 40))
//    g.drawString("Settings", button3(0) + 6, button3(1) + 70)
//    g.setFont(new Font("Arial", Font.PLAIN, 45))
//    g.drawString("Continue", button2(0) + 10, button2(1) + 70)
    displayAudioButtons(g, muted, i)
  }
  
  /**Sets the button colors depending on 
   * wether the mouse is being hovered over it*/
  private def menuButtonColors(mouseX: Int, mouseY: Int) = {
    if(mouseX >= button1(0) && mouseX <= button1(0) + button1(2) && mouseY >= button1(1) && mouseY <= button1(1) + button1(3)) {
      button1Color = hoveringColor
    } else if(mouseX >= button2(0) && mouseX <= button2(0) + button2(2) && mouseY >= button2(1) && mouseY <= button2(1) + button2(3)) {
      button2Color = hoveringColor
    } else if(mouseX >= button3(0) && mouseX <= button3(0) + button3(2) && mouseY >= button3(1) && mouseY <= button3(1) + button3(3)) {
      button3Color = hoveringColor
    } else if(mouseX >= button4(0) && mouseX <= button4(0) + button4(2) && mouseY >= button4(1) && mouseY <= button4(1) + button4(3)) {
      button4Color = hoveringColor
    } else {
      button1Color = defaultColor
      button2Color = defaultColor
      button3Color = defaultColor
      button4Color = defaultColor
    }
  }
  
  def whichButton(mouseX: Int, mouseY: Int): String = {
    if(mouseX >= button1(0) && mouseX <= button1(0) + button1(2) && mouseY >= button1(1) && mouseY <= button1(1) + button1(3)) {
      "1"
    } else if(mouseX >= button2(0) && mouseX <= button2(0) + button2(2) && mouseY >= button2(1) && mouseY <= button2(1) + button2(3)) {
      "2"
    } else if(mouseX >= button3(0) && mouseX <= button3(0) + button3(2) && mouseY >= button3(1) && mouseY <= button3(1) + button3(3)) {
      "3"
    } else if(mouseX >= button4(0) && mouseX <= button4(0) + button4(2) && mouseY >= button4(1) && mouseY <= button4(1) + button4(3)) {
      "4"
    } else ""
  }
  
  private val mute: Image = Toolkit.getDefaultToolkit.createImage("src/images/mute.png")
  private val unmute: Image = Toolkit.getDefaultToolkit.createImage("src/images/unmute.png")
  private def displayAudioButtons(g: Graphics, muted: Boolean, i: ImageObserver) = {
    if(muted) {
      g.drawImage(mute, Constant.screenWidth - 100, Constant.screenHeight - 100, i)
    } else {
      g.drawImage(unmute, Constant.screenWidth - 100, Constant.screenHeight - 100, i)
    }
  }
  
  
  
}