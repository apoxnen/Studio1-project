package bongi

import java.awt.Graphics
import scala.util.Random
import scala.math._

object AI {
  
  private var difficultyLevel = 1  // 1, 2, 3 or 4. The bigger the number the harder the game.''
  
  
  def throwBall(b: Ball) = {
    if(!BeerPong.homeCups.forall(!_.isAlive)) {
      val force = this.searchCups
      BeerPong.ball.gravity = Constant.gravity
      BeerPong.ball.setBallSpeed(( force(0)) / 8f, ( force(1)) / 4f)
    }
  }
  
  def difficulty = difficultyLevel
  
  def adjustDifficulty() = {
    difficultyLevel = max((difficultyLevel + 1) % 5, 1)
  }

  private def searchCups: Array[Int] = {
    //Impossible. The computer can't miss.
    if (difficultyLevel == 4) {
      //Not implemented
      
      
      Array(0,0)
    }
    
    //Hard difficulty
    else if (difficultyLevel == 3) {
      val random = new Random
      Array( -125 - random.nextInt(75), -175 - random.nextInt(25))
    
    //Medium difficulty
    } else if (difficultyLevel == 2) {
      val random = new Random
      Array( -150 - random.nextInt(50), -175 - random.nextInt(50))
    
    //Easiest difficulty
    } else {
      val random = new Random
      Array( -100 - random.nextInt(100), -150 - random.nextInt(100))
    }
    
  }  
  
}