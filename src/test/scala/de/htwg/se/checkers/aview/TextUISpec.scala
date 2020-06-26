package de.htwg.se.checkers.aview

import de.htwg.se.checkers.control.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Game
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TextUISpec extends AnyWordSpec with Matchers {

  "A TUI" should {
    val controller = new Controller(new Game())
    val tui = new TextUI(controller)
    val game = new Game()
    val movedGame = game.movePiece(game.cell(2,0), game.cell(3,1))

    "create new game" in {
      tui.tuiProcessor("new Round")
      controller.game should be(game)
    }

    "not change on undo when game is new" in {
      tui.tuiProcessor("undo")
      controller.game should be(game)
    }

    "not change on redo when game is new" in {
      tui.tuiProcessor("redo")
      controller.game should be(game)
    }

    "move a piece" in {
      tui.tuiProcessor("move 0,2 1,3")
      controller.game should be(movedGame)
    }

    "undo moving a piece" in {
      tui.tuiProcessor("move 0,2 1,3")
      controller.game should be(movedGame)
      tui.tuiProcessor("undo")
      controller.game should be(game)
    }

    "redo moving a piece" in {
      tui.tuiProcessor("move 0,2 1,3")
      controller.game should be(movedGame)
      tui.tuiProcessor("undo")
      controller.game should be(game)
      tui.tuiProcessor("redo")
      controller.game should be(movedGame)
    }

    "not change on wrong input" in {
      tui.tuiProcessor("ioe")
      controller.game should be(movedGame)
    }

    "end game on exit" in {
      tui.tuiProcessor("exit")
    }
  }
}
