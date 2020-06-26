package de.htwg.se.checkers.model.GameComponent.GameBaseImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BoardSpec extends AnyWordSpec with Matchers{

  "A Board" should {
    val piecesBlack = new Pieces(Color.black)
    val piecesWhite = new Pieces(Color.white)
    val board = new Board().createBoard(piecesBlack.pieces, piecesWhite.pieces)
    "have a cell" in {
      board.cells.cell(0,0) should be(Cell(0,0,Color.black,Some(piecesBlack.pieces(0))))
    }
  }
}
