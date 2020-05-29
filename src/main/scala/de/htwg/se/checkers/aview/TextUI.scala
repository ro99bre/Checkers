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

      //case "exit" :: Nil =>

      case _ =>
    }
  }
  override def update(): Unit = println(controller.gameToString)
}
