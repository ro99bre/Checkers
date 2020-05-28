package de.htwg.se.checkers.aview

import de.htwg.se.checkers.CheckersTextUI
import de.htwg.se.checkers.model._

class textui {

  def tuiProcessor(input: String): String ={

    val output = new StringBuilder()

    input.split(" |,").toList match {

      case "new" :: "Board" :: Nil =>
        CheckersTextUI.newGame()
        //output.append(input)

      case "move" :: xPosOld :: yPosOld :: xPosNew :: yPosNew :: Nil =>

        //CheckersTextUI.game.movePiece(new Cell(xPosOld.toInt, yPosOld.toInt), new Cell(xPosNew.toInt,yPosNew.toInt))

        //output.append(xPosOld.toInt + "\n")
        //xPosOld.isInstanceOf[Int]
        //println(xPosOld.toInt.getClass)
        //output.append(yPosOld)
        //println(yPosOld.getClass)

      case "exit" :: Nil =>
        CheckersTextUI.exit()

      case _ =>
        output.append("Print Help")
    }

    return output.toString()
  }

}
