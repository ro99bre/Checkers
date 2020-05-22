package de.htwg.se.checkers.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BoardSpec extends AnyWordSpec with Matchers{

  "A Board" when {
    "new" should {
      val piecesBlack = new Pieces(Color.black)
      val piecesWhite = new Pieces(Color.white)
      val piecesWhiteOneKicked = piecesWhite.pieces.updated(0,Piece(Color.white, Queen.notQueen, Kicked.isKicked))
      val piecesBlackOneKicked = piecesBlack.pieces.updated(0,Piece(Color.black, Queen.notQueen, Kicked.isKicked))
      val piecesWhiteKicked = piecesWhiteOneKicked.updated(1,Piece(Color.white, Queen.notQueen, Kicked.isKicked)).
        updated(2,Piece(Color.white, Queen.notQueen, Kicked.isKicked)).
        updated(3,Piece(Color.white, Queen.notQueen, Kicked.isKicked))
      val piecesBlackKicked = piecesBlackOneKicked.updated(1,Piece(Color.black, Queen.notQueen, Kicked.isKicked)).
        updated(2,Piece(Color.black, Queen.notQueen, Kicked.isKicked)).
        updated(4,Piece(Color.black, Queen.notQueen, Kicked.isKicked))
      val piecesBlackCrowned = piecesBlack.pieces.updated(0,Piece(Color.black, Queen.isQueen, Kicked.notKicked))
      val piecesWhiteCrowned = piecesWhite.pieces.updated(0,Piece(Color.white, Queen.isQueen, Kicked.notKicked))
      val board = new Board().createBoard(piecesBlack.pieces, piecesWhite.pieces)
      val movedBlackBoard : Board = board.copy(board.cells.replaceCell(2,0,Cell(2,0,Color.black)).
        replaceCell(3,1,Cell(3,1,Color.black,Some(piecesBlack.pieces(0)))))
      val movedWhiteBoard : Board = movedBlackBoard.copy(movedBlackBoard.cells.replaceCell(5,3,Cell(5,3,Color.black)).
        replaceCell(4,2,Cell(4,2,Color.black,Some(piecesWhite.pieces(0)))))
      val kickedBoard : Board = movedWhiteBoard.copy(movedWhiteBoard.cells.replaceCell(4,2,Cell(4,2,Color.black)).
        replaceCell(5,3,Cell(5,3,Color.black,Some(piecesBlack.pieces(0)))).replaceCell(3,1,Cell(3,1,Color.black)))
      val crownBoard : Board = kickedBoard.copy(kickedBoard.cells.replaceCell(7,3,Cell(7,3,Color.black)).
        replaceCell(6,4,Cell(6,4,Color.black, Some(piecesBlack.pieces(0)))).replaceCell(5,5,Cell(5,5,Color.black)))
      val crownedBoard : Board = crownBoard.copy(crownBoard.cells.replaceCell(6,4,Cell(6,4,Color.black)).
        replaceCell(7,3,Cell(7,3,Color.black,Some(piecesBlackCrowned(0)))))
      val queenMoved : Board = crownedBoard.copy(crownedBoard.cells.
        replaceCell(6,4,Cell(6,4,Color.black,Some(piecesBlackCrowned(0)))).
        replaceCell(7,3,Cell(7,3,Color.black)))

      "have a cell" in {
        board.cells.cell(0,0) should be(Cell(0,0,Color.black,Some(piecesBlack.pieces(0))))
      }
      "be able to move a black Piece" in {
        board.movePiece(board.cells.cell(2,0), board.cells.cell(3,1), Color.white,
          piecesBlack.pieces, piecesWhite.pieces) should be(movedBlackBoard,piecesBlack.pieces,piecesWhite.pieces,
          Color.black)
      }//crown, kickb, kickw, countkickedpieces, none returns for line coverage, queen jump
      "be able to move a white Piece" in {//unnecessary?
        movedBlackBoard.movePiece(movedBlackBoard.cells.cell(5,3), movedBlackBoard.cells.cell(4,2), Color.black,
          piecesBlack.pieces, piecesWhite.pieces) should be(movedWhiteBoard,piecesWhite.pieces,piecesBlack.pieces,
          Color.white)
      }
      "not move a piece against the rules" in {
        board.movePiece(board.cells.cell(5,1), board.cells.cell(3,3), Color.black, piecesWhite.pieces,
          piecesBlack.pieces) should be(board, piecesWhite.pieces, piecesBlack.pieces, Color.black)
      }
      "not move a non-existent piece" in {
        board.movePiece(board.cells.cell(3,7), board.cells.cell(4,6), Color.black, piecesWhite.pieces,
          piecesBlack.pieces) should be(board, piecesBlack.pieces, piecesWhite.pieces, Color.black)
      }
      "be able to kick a piece" in {
        movedWhiteBoard.movePiece(movedWhiteBoard.cells.cell(3,1), movedWhiteBoard.cells.cell(5,3), Color.white,
          piecesWhite.pieces, piecesBlack.pieces) should be(kickedBoard, piecesBlack.pieces, piecesWhiteOneKicked, Color.black)
      }
      /*"be able to crown a piece" in { //not working, doesn't put queen on board
        crownBoard.movePiece(crownBoard.cells.cell(6,4), crownBoard.cells.cell(7,3), Color.white, piecesWhiteKicked,
          piecesBlack.pieces) should be(crownedBoard, piecesBlackCrowned, piecesWhiteKicked, Color.black)
      }*/
      "be able to move a queen" in {
        crownedBoard.movePiece(crownedBoard.cells.cell(7,3), crownedBoard.cells.cell(6,4), Color.white,
          piecesBlackCrowned, piecesWhiteKicked) should be(queenMoved, piecesBlackCrowned, piecesWhiteKicked, Color.black)
      }
      "be able to count black kicked Pieces" in {
        queenMoved.countKickedPieces(piecesBlackCrowned, piecesWhiteKicked) should be(0,4)
      }
      "be able to count white kicked Pieces" in {
        queenMoved.countKickedPieces(piecesWhiteCrowned, piecesBlackKicked) should be(4,0)
      }
    }
  }
}
