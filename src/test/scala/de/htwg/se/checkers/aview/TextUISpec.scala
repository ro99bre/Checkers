package de.htwg.se.checkers.aview

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.checkers.model.Game
import de.htwg.se.checkers.control.Controller

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

    "move a piece" in {
      tui.tuiProcessor("move 0,2 1,3")
      controller.game should be(movedGame)
    }

    "not change on wrong input" in {
      tui.tuiProcessor("ioe")
      controller.game should be(movedGame)
    }
  }
}
