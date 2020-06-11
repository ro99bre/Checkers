package de.htwg.se.checkers.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameSpec extends AnyWordSpec with Matchers{

  "A Game" should {
    val piecesBlack = new Pieces(Color.black)
    val piecesWhite = new Pieces(Color.white)
    val board = new Board().createBoard(piecesBlack.pieces, piecesWhite.pieces)
    val game = Game(board,piecesBlack.pieces,piecesWhite.pieces,Color.white)
    val movedBlack : Game = game.updateGame(Cell(2,0,Color.black), Color.black).updateGame(Cell(3,1,Color.black,Some(piecesBlack.pieces(0))), Color.black)
    val movedWhite : Game = movedBlack.updateGame(Cell(5,3,Color.black), Color.black).updateGame(Cell(4,2,Color.black,Some(piecesWhite.pieces(0))), Color.white)
    val kickBlack : Game = movedBlack.updateGame(Cell(5,3,Color.black), Color.black).updateGame(Cell(4,2,Color.black,Some(piecesWhite.pieces(0))), Color.black)
    val kickedBlack : Game = kickBlack.updateGame(Cell(3,1,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(Cell(4,2,Color.black), Color.white).updateGame(Cell(2,0,Color.black,Some(piecesWhite.pieces(0))), Color.white)
    val kicked : Game = movedWhite.updateGame(Cell(4,2,Color.black),Color.black,Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(Cell(3,1,Color.black), Color.black).updateGame(Cell(5,3,Color.black,Some(piecesBlack.pieces(0))), Color.black)
    val crown : Game = kicked.updateGame(Cell(7,3,Color.black),Color.white).updateGame(Cell(5,5,Color.black),Color.white).
      updateGame(Cell(6,4,Color.black,Some(piecesBlack.pieces(0))), Color.white)
    val crowned : Game = crown.updateGame(Cell(6,4,Color.black), Color.black).updateGame(Cell(7,3,Color.black,
      Some(Piece(Color.black, Queen.isQueen, Kicked.notKicked))), Color.black,
      Some(Piece(Color.black, Queen.isQueen, Kicked.notKicked)), Some(0))
    val crownedLMCWhite : Game = crown.updateGame(Cell(6,4,Color.black), Color.white).updateGame(Cell(7,3,Color.black,
      Some(Piece(Color.black, Queen.isQueen, Kicked.notKicked))), Color.white,
      Some(Piece(Color.black, Queen.isQueen, Kicked.notKicked)), Some(0))
    val queenMoved : Game = crowned.updateGame(Cell(7,3,Color.black),Color.black).updateGame(Cell(6,4,Color.black,
      Some(Piece(Color.black, Queen.isQueen, Kicked.notKicked))),Color.black)
    val losingWhitePieces : Game = game.updateGame(Cell(7,7,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(Cell(7,5,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(1)).
      updateGame(Cell(7,3,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(2)).
      updateGame(Cell(7,1,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(3)).
      updateGame(Cell(6,6,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(4)).
      updateGame(Cell(6,4,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(5)).
      updateGame(Cell(6,2,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(6)).
      updateGame(Cell(6,0,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(7)).
      updateGame(Cell(5,7,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(8)).
      updateGame(Cell(5,5,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(9)).
      updateGame(Cell(5,3,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(10)).
      updateGame(Cell(4,0,Color.black, Some(Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.white).
      updateGame(Cell(2,0,Color.black), Color.white)
    val lostWhitePieces : Game = losingWhitePieces.updateGame(Cell(5,1,Color.black), Color.black, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(11)).
      updateGame(Cell(6,2,Color.black, Some(Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.black).
      updateGame(Cell(4,0,Color.black), Color.black, None,None, Some(Color.black))
    val losingBlackPieces : Game = game.updateGame(Cell(0,0,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(11)).
      updateGame(Cell(0,2,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(1)).
      updateGame(Cell(0,4,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(2)).
      updateGame(Cell(0,6,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(3)).
      updateGame(Cell(1,1,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(4)).
      updateGame(Cell(1,3,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(5)).
      updateGame(Cell(1,5,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(6)).
      updateGame(Cell(1,7,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(7)).
      updateGame(Cell(2,2,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(8)).
      updateGame(Cell(2,4,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(9)).
      updateGame(Cell(2,6,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(Cell(4,4,Color.black, Some(piecesBlack.pieces(10))), Color.white).
      updateGame(Cell(2,0,Color.black), Color.black)
    val lostBlackPieces : Game = losingBlackPieces.updateGame(Cell(4,4,Color.black), Color.white,Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(10)).
      updateGame(Cell(5,3,Color.black), Color.white).updateGame(Cell(3,5,Color.black,Some(Piece(Color.white, Queen.notQueen, Kicked.notKicked))), Color.white, None, None, Some(Color.white))
    val losingBlocked : Game = game.updateGame(Cell(7,7,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(Cell(7,5,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(1)).
      updateGame(Cell(7,3,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(2)).
      updateGame(Cell(7,1,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(3)).
      updateGame(Cell(6,6,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(4)).
      updateGame(Cell(6,4,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(5)).
      updateGame(Cell(6,2,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(6)).
      updateGame(Cell(5,1,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(7)).
      updateGame(Cell(5,7,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(8)).
      updateGame(Cell(5,5,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(9)).
      updateGame(Cell(5,3,Color.black), Color.white, Some(Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(10)).
      updateGame(Cell(2,0,Color.black), Color.white).updateGame(Cell(2,2,Color.black), Color.white).
      updateGame(Cell(5,1,Color.black, Some(Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.white).
      updateGame(Cell(3,1,Color.black, Some(Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.white)
    val lostBlocked : Game = losingBlocked.updateGame(Cell(3,1,Color.black), Color.black).
      updateGame(Cell(4,2,Color.black, Some(Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.black, None, None, Some(Color.black))
    val lostNoMove : Game = losingBlocked.updateGame(Cell(3,1,Color.black), Color.white).
      updateGame(Cell(4,2,Color.black, Some(Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.white, None, None, Some(Color.black))
    val queenNotBlocked : Game = losingBlocked.updateGame(Cell(6,0, Color.black, Some(Piece(Color.white, Queen.isQueen,
      Kicked.notKicked))),Color.white,Some(Piece(Color.white, Queen.isQueen, Kicked.notKicked)), Some(11))
    val queenNotBlockedMoved : Game = queenNotBlocked.updateGame(Cell(3,1,Color.black), Color.white).
      updateGame(Cell(4,2,Color.black, Some(Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.black)
    val queenBlackNotBlocked : Game = game.updateGame(Cell(0,0,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(Cell(0,2,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(1)).
      updateGame(Cell(0,4,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(2)).
      updateGame(Cell(0,6,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(3)).
      updateGame(Cell(1,1,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(4)).
      updateGame(Cell(1,3,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(5)).
      updateGame(Cell(1,5,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(6)).
      updateGame(Cell(1,7,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(7)).
      updateGame(Cell(2,2,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(8)).
      updateGame(Cell(2,4,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(9)).
      updateGame(Cell(2,6,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(10)).
      updateGame(Cell(5,1,Color.black), Color.white).
      updateGame(Cell(3,1,Color.black, Some(Piece(Color.white, Queen.notQueen, Kicked.notKicked))), Color.black).
      updateGame(Cell(2,0,Color.black, Some(Piece(Color.black, Queen.isQueen, Kicked.notKicked))), Color.black,
        Some(Piece(Color.black, Queen.isQueen, Kicked.notKicked)), Some(11))
    val queenBlackNotBlockedMoved : Game = queenBlackNotBlocked.updateGame(Cell(5,3,Color.black), Color.white).
      updateGame(Cell(4,2,Color.black, Some(Piece(Color.white, Queen.notQueen, Kicked.notKicked))), Color.white)
    val losingBlackBlocked : Game = queenBlackNotBlocked.updateGame(Cell(2,0,Color.black, Some(Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.black,
      Some(Piece(Color.black, Queen.notQueen, Kicked.notKicked)), Some(11))
    val blockingBlackQueen : Game = game.updateGame(Cell(4,0,Color.black), Color.black).updateGame(Cell(7,1,Color.black), Color.black).
      updateGame(Cell(7,3,Color.black), Color.black).updateGame(Cell(7,5,Color.black), Color.black).
      updateGame(Cell(7,7,Color.black), Color.black).updateGame(Cell(6,0,Color.black), Color.black).
      updateGame(Cell(6,2,Color.black), Color.black).updateGame(Cell(6,4,Color.black), Color.black).
      updateGame(Cell(2,2,Color.black,Some(Piece(Color.black,Queen.isQueen,Kicked.notKicked))),Color.black,Some(Piece(Color.black,Queen.isQueen,Kicked.notKicked)),Some(0)).
      updateGame(Cell(4,0,Color.black,Some(Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(Cell(4,2,Color.black,Some(Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(Cell(4,6,Color.black,Some(Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(Cell(3,1,Color.black,Some(Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(Cell(3,3,Color.black,Some(Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(Cell(3,5,Color.black,Some(Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(Cell(3,7,Color.black,Some(Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black)
    val blockedBlackQueen : Game = blockingBlackQueen.updateGame(Cell(5,5,Color.black),Color.white).
      updateGame(Cell(4,4,Color.black,Some(Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.white,None,None,Some(Color.white))
    val crownWhite : Game = losingBlackBlocked.updateGame(Cell(3,1,Color.black), Color.black).updateGame(Cell(1,1,Color.black,Some(piecesWhite.pieces(0))), Color.black)
    val crownedWhite : Game = crownWhite.updateGame(Cell(1,1,Color.black),Color.white).updateGame(Cell(0,0,Color.black, Some(Piece(Color.white, Queen.isQueen, Kicked.notKicked))), Color.white, Some(Piece(Color.white, Queen.isQueen, Kicked.notKicked)), Some(0))
    val lostBlackBlocked : Game = losingBlackBlocked.updateGame(Cell(5,3,Color.black), Color.white).
      updateGame(Cell(4,2,Color.black, Some(Piece(Color.white, Queen.notQueen, Kicked.notKicked))), Color.white, None, None, Some(Color.white))
    val whiteQueen : Game = game.updateGame(Cell(5,1,Color.black,Some(Piece(Color.white, Queen.isQueen, Kicked.notKicked))),
      Color.black, Some(Piece(Color.white, Queen.isQueen, Kicked.notKicked)), Some(0))
    val whiteQueenMoved : Game = whiteQueen.updateGame(Cell(5,1,Color.black), Color.white).updateGame(Cell(4,2,Color.black,
      Some(Piece(Color.white, Queen.isQueen, Kicked.notKicked))),Color.white)
    val string : String = game.toString
    val lostString : String = lostWhitePieces.toString

    "be able to create new game" in {
      new Game() should be(game)
    }
    "be able to move a black Piece" in {
      game.movePiece(game.cell(2,0), game.cell(3,1)) should be(movedBlack)
    }
    "be able to move a white Piece" in {
      movedBlack.movePiece(movedBlack.cell(5,3), movedBlack.cell(4,2)) should be(movedWhite)
    }
    "not make invalid move with black Piece" in {
      game.movePiece(game.cell(2,0), game.cell(4,6)) should be(game)
    }
    "not make invalid move with white Piece" in {
      movedBlack.movePiece(movedBlack.cell(5,3), movedBlack.cell(4,6)) should be(movedBlack)
    }
    "not move a piece against the rules" in {
      game.movePiece(game.cell(5,1), game.cell(3,3)) should be(game)
    }
    "not move a non-existent piece" in {
      game.movePiece(game.cell(3,7), game.cell(4,6)) should be(game)
    }
    "be able to kick a white piece" in {
      movedWhite.movePiece(movedWhite.cell(3,1), movedWhite.cell(5,3)) should be(kicked)
    }
    "be able to kick a black piece" in {
      kickBlack.movePiece(kickBlack.cell(4,2), kickBlack.cell(2,0)) should be(kickedBlack)
    }
    "be able to crown a black piece" in {
      crown.movePiece(crown.cell(6,4), crown.cell(7,3)) should be(crowned)
    }
    "be able to crown a white piece" in {
      crownWhite.movePiece(crownWhite.cell(1,1), crownWhite.cell(0,0)) should be(crownedWhite)
    }
    "be able to move a black queen" in {
      crownedLMCWhite.movePiece(crownedLMCWhite.cell(7,3), crownedLMCWhite.cell(6,4)) should be(queenMoved)
    }
    "be able to move a white queen" in {
      whiteQueen.movePiece(whiteQueen.cell(5,1), whiteQueen.cell(4,2)) should be(whiteQueenMoved)
    }
    "not jump with over empty cell with queen" in {
      crownedLMCWhite.movePiece(crownedLMCWhite.cell(7,3), crownedLMCWhite.cell(5,5)) should be(crownedLMCWhite)
    }
    "not make invalid move with queen" in {
      crownedLMCWhite.movePiece(crownedLMCWhite.cell(7,3), crownedLMCWhite.cell(3,5)) should be(crownedLMCWhite)
    }
    "be able to count kicked Pieces" in {
      losingWhitePieces.countKickedPieces() should be(0,11)
    }
    "have a winner when white doesn't have any pieces left" in {
      losingWhitePieces.movePiece(losingWhitePieces.cell(4,0), losingWhitePieces.cell(6,2)) should be(lostWhitePieces)
    }
    "have winner when black doesn't have any pieces left" in {
      losingBlackPieces.movePiece(losingBlackPieces.cell(5,3), losingBlackPieces.cell(3,5)) should be(lostBlackPieces)
    }
    "have a winner when white is blocked" in {
      losingBlocked.movePiece(losingBlocked.cell(3,1), losingBlocked.cell(4,2)) should be(lostBlocked)
    }
    "not have winner if white queen is not blocked" in {
      queenNotBlocked.movePiece(queenNotBlocked.cell(3,1), queenNotBlocked.cell(4,2)) should be(queenNotBlockedMoved)
    }
    "not move if a winner exists" in {
      lostNoMove.movePiece(lostNoMove.cell(4,2), lostNoMove.cell(5,3)) should be(lostNoMove)
    }
    "have winner when black is blocked" in {
      losingBlackBlocked.movePiece(losingBlackBlocked.cell(5,3), losingBlackBlocked.cell(4,2)) should be(lostBlackBlocked)
    }
    "have winner when black queen is blocked" in {
      blockingBlackQueen.movePiece(blockingBlackQueen.cell(5,5), blockingBlackQueen.cell(4,4)) should be(blockedBlackQueen)
    }
    "not have winner if black queen is not blocked" in {
      queenBlackNotBlocked.movePiece(queenBlackNotBlocked.cell(5,3), queenBlackNotBlocked.cell(4,2)) should be(queenBlackNotBlockedMoved)
    }
    "have String representation" in {
      game.toString should be(string)
    }
    "have String representation if a winner exists" in {
      lostWhitePieces.toString should be(lostString)
    }
  }
}
