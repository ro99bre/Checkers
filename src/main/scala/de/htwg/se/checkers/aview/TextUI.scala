package de.htwg.se.checkers.aview

import de.htwg.se.checkers.model._
import de.htwg.se.checkers.util.Observer
import de.htwg.se.checkers.control.Controller

class TextUI(controller: Controller) extends Observer {

  controller.add(this)

  def tuiProcessor(input: String): Unit ={

    input.split(" |,").toList match {

      case "new" :: "Round" :: Nil => controller.createGame()

      case "move" :: xPosOld :: yPosOld :: xPosNew :: yPosNew :: Nil =>
        controller.move(xPosOld.toInt, yPosOld.toInt, xPosNew.toInt, yPosNew.toInt)

      case "exit" :: Nil => System.exit(0)

      case _ =>
       /* output.append("Possible Commands:\n")
        output.append("new Round:                 Starts a new Round of the game. The current scores will be lost.\n")
        output.append("move old<X,Y> new<X,Y>:    Moves the Piece from the old position to the new position specified\n")
        output.append("exit:                      Exit the Game.\n")*/
    }
  }
  override def update(): Unit = println(controller.gameToString)
}
