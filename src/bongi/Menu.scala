package bongi

import java.awt.Graphics
import java.awt.Toolkit
import java.awt.Color
import java.awt.Image
import java.awt.Font
import java.awt.image.ImageObserver
import scala.util.Random

object Menu {
  
  private val button1 = Array(Constant.screenWidth/2 - 100, Constant.screenHeight/2 - 210, 200, 100)
  private val button2 = Array(Constant.screenWidth/2 - 100, Constant.screenHeight/2 -  60, 200, 100)
  private val button3 = Array(Constant.screenWidth/2 - 100, Constant.screenHeight/2 +  90, 200, 100)
  private val defaultColor = new Color(72,209,204)
  private val hoveringColor = new Color(70,130,180)
  private var button1Color = defaultColor
  private var button2Color = defaultColor
  private var button3Color = defaultColor
  private val ballImage = Toolkit.getDefaultToolkit.createImage("src/images/ballimage.png")
  
  def paintMenu(g: Graphics, epilepsy: Boolean, muted: Boolean, i: ImageObserver, mouseX: Int, mouseY: Int) = {
    g.setColor(Color.BLACK)
    g.fillRect(0, 0, Constant.screenWidth + 30, Constant.screenHeight + 30)
    menuButtonColors(mouseX, mouseY)
//    for(x <- 0 to Constant.screenWidth by 10; y <- 0 to Constant.screenHeight by 10) {
//      if(Random.nextInt(20) == 1 && epilepsy) {
//        setRandomColor(g)
//        g.fillOval(x, y, Random.nextInt(50), Random.nextInt(50))
//      }
//    }
    g.setColor(button1Color)
    g.fillRect(button1(0), button1(1), button1(2), button1(3))
    g.setColor(button2Color)
    g.fillRect(button2(0), button2(1), button2(2), button2(3))
    g.setColor(button3Color)
    g.fillRect(button3(0), button3(1), button3(2), button3(3))
    g.setColor(Color.WHITE)
    g.setFont(new Font("Arial", Font.BOLD, 160))
    g.drawString("Beer", 40, 170)
    g.drawString("pong", 40, 320)
    g.drawImage(ballImage, 125, 220, i)
    g.setFont(new Font("Arial", Font.PLAIN, 60))
    g.drawString("Play", button1(0) + 40, button1(1) + 70)
    
    g.setFont(new Font("Arial", Font.PLAIN, 40))
    g.drawString("Settings", button3(0) + 25, button3(1) + 65)
    g.setFont(new Font("Arial", Font.PLAIN, 45))
    g.drawString("Continue", button2(0) + 10, button2(1) + 70)
    rollCredits(g)
    displayAudioButtons(g, muted, i)
  }
  
  def whichButton(mouseX: Int, mouseY: Int): String = {
    if(mouseX >= Constant.screenWidth - 100 && mouseX <= Constant.screenWidth - 40 && mouseY >= Constant.screenHeight - 100 && mouseY <= Constant.screenHeight - 40) {
      "m"
    } else if(mouseX >= button1(0) && mouseX <= button1(0) + button1(2) && mouseY >= button1(1) && mouseY <= button1(1) + button1(3)) {
      "1"
    } else if(mouseX >= button2(0) && mouseX <= button2(0) + button2(2) && mouseY >= button2(1) && mouseY <= button2(1) + button2(3)) {
      "2"
    } else if(mouseX >= button3(0) && mouseX <= button3(0) + button3(2) && mouseY >= button3(1) && mouseY <= button3(1) + button3(3)) {
      "3"
    } else ""
  }
  
  private def menuButtonColors(mouseX: Int, mouseY: Int) = {
    if(mouseX >= button1(0) && mouseX <= button1(0) + button1(2) && mouseY >= button1(1) && mouseY <= button1(1) + button1(3)) {
      button1Color = hoveringColor
    } else if(mouseX >= button2(0) && mouseX <= button2(0) + button2(2) && mouseY >= button2(1) && mouseY <= button2(1) + button2(3)) {
      button2Color = hoveringColor
    } else if(mouseX >= button3(0) && mouseX <= button3(0) + button3(2) && mouseY >= button3(1) && mouseY <= button3(1) + button3(3)) {
      button3Color = hoveringColor
    } else {
      button1Color = defaultColor
      button2Color = defaultColor
      button3Color = defaultColor
    }
  }
  
  private def rollCredits(g: Graphics) = {
    g.setColor(Color.darkGray)
    val creditBoxSpace = Array(Constant.screenWidth - 420, 150, 300, 450)
//    g.drawRect(creditBoxSpace(0), creditBoxSpace(1), creditBoxSpace(2), creditBoxSpace(3))
//    g.drawRect(creditBoxSpace(0), creditBoxSpace(1), creditBoxSpace(2), creditBoxSpace(3))
    (0 to 10).foreach((x: Int) => g.drawRect(creditBoxSpace(0) + x, creditBoxSpace(1) - x, creditBoxSpace(2), creditBoxSpace(3)))
    g.drawString("Credits", creditBoxSpace(0), creditBoxSpace(1) - 10)
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
  
  private def setRandomColor(g: Graphics) = g.setColor(new Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
  
  
  
}