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
    "be able to replace a cell without a piece" in {
      board.setCell(0,0,Color.white,None,None,None) should be(board.copy(board.cells.replaceCell(0,0,Cell(0,0,Color.white))))
    }
    "be able to replace a cell with a piece" in {
      board.setCell(0,0,Color.white,Some(Color.white),Some(Queen.isQueen),Some(Kicked.notKicked)) should be(board.copy(board.cells.replaceCell(0,0,Cell(0,0,Color.white,Some(Piece(Color.white,Queen.isQueen,Kicked.notKicked))))))
    }
  }
}
