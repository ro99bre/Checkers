package de.htwg.se.checkers

import de.htwg.se.checkers.aview.{GUI, TextUI}
import de.htwg.se.checkers.control.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Color, Game}
import de.htwg.se.checkers.model._

object CheckersTextUI {

  def main(args: Array[String]): Unit = {

    println("Started Checkers in TUI Mode")

    val controller = new Controller(new Game())
    val tui = new TextUI(controller)
    val gui = new GUI(controller)
    gui.main(Array())
    controller.notifyObservers()
    var input: String = ""
    if (args.length > 0) input = args(0)
    if(!input.isEmpty) {
      if (controller.game.winnerColor.isDefined) println("Winner: " + controller.game.winnerColor.get)
      else if (controller.game.lmc == Color.white) print("Next Player: Black\nNext move: ")
      else print("Next Player: White\nNext move: ")
      tui.tuiProcessor(input)
    } else do {
        if (controller.game.winnerColor.isDefined) println("Winner: " + controller.game.winnerColor.get)
        else if (controller.game.lmc == Color.white) print("Next Player: Black\nNext move: ")
        else print("Next Player: White\nNext move: ")
        input = scala.io.StdIn.readLine()
        tui.tuiProcessor(input)
      } while (input != "exit")
  }
}
