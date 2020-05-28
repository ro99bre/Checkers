package de.htwg.se.checkers.aview

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.checkers.model.{Board, Color, Game, Pieces}

class TextUISpec extends AnyWordSpec with Matchers {

  "A TUI" when {

    "has a command" should {

      val tui = new TextUI

      val piecesBlack = new Pieces(Color.black)
      val piecesWhite = new Pieces(Color.white)
      val board = new Board().createBoard(piecesBlack.pieces, piecesWhite.pieces)
      val game = Game(board, piecesBlack.pieces, piecesWhite.pieces, Color.white)

      val newRound = "Started new Round\n" + game.toString
      val movedPiece = "Moved a piece\n" + game.toString

      val helpText = "Possible Commands:\n" +
        "new Round:                 Starts a new Round of the game. The current scores will be lost.\n" +
        "move old<X,Y> new<X,Y>:    Moves the Piece from the old position to the new position specified\n" +
        "exit:                      Exit the Game.\n"


      "to start a new round" in {
        val input = "new Round"
        tui.tuiProcessor(input) should be(newRound)
      }

      "to move a piece" in {
        val input = "move 1,2 3,4"
        tui.tuiProcessor(input) should be(movedPiece)
      }

      "to print a help text" in {
        val input = "help"
        tui.tuiProcessor(input) should be(helpText)
      }

      /*
      "to exit the game" in {
        input = "exit"
        tui.tuiProcessor(input) should be(newRound)
      }
      */
    }
  }
}
