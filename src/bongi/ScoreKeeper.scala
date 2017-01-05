package bongi

import scala.io.Source
import java.io.PrintWriter

class ScoreKeeper {
  
  private val scores = Source.fromFile("src/score/scores.txt")
  val saveFile = new PrintWriter("src/score/scores.txt")
  private val topThree = Array(0, 0, 0)
  
  def getHightscores() = {
    try {
      var lineNumber = 1
      for(line <- scores.getLines()) {
        topThree(lineNumber) = line.toInt
        lineNumber += 1
      }
  
    } finally {
      scores.close()
    }
    topThree.toVector
  }
  
  def setHighscores(score: Int) = {
    try {
      var lineNumber = 1
      var scoreSet = false
      for(line <- scores.getLines() if(!scoreSet)) {
        if(score > line.toInt) saveFile.write(score.toString)
        else lineNumber += 1
      }
  
    } finally {
      scores.close()
    }
  }
  
  
  
}