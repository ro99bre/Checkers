package de.htwg.se.checkers

import com.google.inject.Guice
import de.htwg.se.checkers.aview.{GUI, TextUI}
import de.htwg.se.checkers.control.controllerComponent.ControllerTrait
import de.htwg.se.checkers.control.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Color, Game}
import de.htwg.se.checkers.model.GameComponent.GameTrait

object CheckersTextUI {

  val injector = Guice.createInjector(new CheckersModule)
  //val controller = injector.getInstance(classOf[ControllerTrait])
  //val game = injector.getInstance(classOf[GameTrait])
  val controller = injector.getInstance(classOf[ControllerTrait])

  def main(args: Array[String]): Unit = {

    println("Started Checkers in TUI Mode")

    //val controller = new Controller(game)
    val tui = new TextUI(controller)
    val gui = new GUI(controller)
    gui.main(Array())
    controller.notifyObservers()
    var input: String = ""
    if (args.length > 0) input = args(0)
    if(!input.isEmpty) {
      if (controller.getWinnerColor().isDefined) println("Winner: " + controller.getWinnerColor)
      else if (controller.getLastMoveColor() == Color.white) print("Next Player: Black\nNext move: ")
      else print("Next Player: White\nNext move: ")
      tui.tuiProcessor(input)
    } else do {
        if (controller.getWinnerColor().isDefined) println("Winner: " + controller.getWinnerColor())
        else if (controller.getLastMoveColor() == Color.white) print("Next Player: Black\nNext move: ")
        else print("Next Player: White\nNext move: ")
        input = scala.io.StdIn.readLine()
        tui.tuiProcessor(input)
      } while (input != "exit")
  }
}
