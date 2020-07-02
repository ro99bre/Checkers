package de.htwg.se.checkers.model.GameComponent.GameBaseImpl

import de.htwg.se.checkers.model.GameComponent.GameBaseImpl
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameSpec extends AnyWordSpec with Matchers{

  "A Game" should {
    val piecesBlack = new Pieces(Color.black)
    val piecesWhite = new Pieces(Color.white)
    val board = new Board().createBoard(piecesBlack.pieces, piecesWhite.pieces)
    val game = Game(board,piecesBlack.pieces,piecesWhite.pieces,Color.white)
    val movedBlack : Game = game.updateGame(Cell(2,0,Color.black), Color.black).updateGame(GameBaseImpl.Cell(3,1,Color.black,Some(piecesBlack.pieces(0))), Color.black)
    val movedWhite : Game = movedBlack.updateGame(GameBaseImpl.Cell(5,3,Color.black), Color.black).updateGame(GameBaseImpl.Cell(4,2,Color.black,Some(piecesWhite.pieces(0))), Color.white)
    val kickBlack : Game = movedBlack.updateGame(GameBaseImpl.Cell(5,3,Color.black), Color.black).updateGame(GameBaseImpl.Cell(4,2,Color.black,Some(piecesWhite.pieces(0))), Color.black)
    val kickedBlack : Game = kickBlack.updateGame(GameBaseImpl.Cell(3,1,Color.black), Color.white, Some(Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(GameBaseImpl.Cell(4,2,Color.black), Color.white).updateGame(GameBaseImpl.Cell(2,0,Color.black,Some(piecesWhite.pieces(0))), Color.white)
    val kicked : Game = movedWhite.updateGame(GameBaseImpl.Cell(4,2,Color.black),Color.black,Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(GameBaseImpl.Cell(3,1,Color.black), Color.black).updateGame(GameBaseImpl.Cell(5,3,Color.black,Some(piecesBlack.pieces(0))), Color.black)
    val kickWhite : Game = game.updateGame(GameBaseImpl.Cell(3,1,Color.black,Some(piecesWhite.pieces(0))),Color.white).
      updateGame(GameBaseImpl.Cell(5,1,Color.black),Color.white)
    val kickedWhite : Game = kickWhite.updateGame(GameBaseImpl.Cell(2,2,Color.black),Color.white).
      updateGame(GameBaseImpl.Cell(4,0,Color.black,Some(GameBaseImpl.Piece(Color.black,Queen.notQueen,Kicked.notKicked))),Color.white).
      updateGame(GameBaseImpl.Cell(3,1,Color.black),Color.black,Some(GameBaseImpl.Piece(Color.white,Queen.notQueen,Kicked.isKicked)),Some(0))
    val crown : Game = kicked.updateGame(GameBaseImpl.Cell(7,3,Color.black),Color.white).updateGame(GameBaseImpl.Cell(5,5,Color.black),Color.white).
      updateGame(GameBaseImpl.Cell(6,4,Color.black,Some(piecesBlack.pieces(0))), Color.white)
    val crowned : Game = crown.updateGame(GameBaseImpl.Cell(6,4,Color.black), Color.black).updateGame(GameBaseImpl.Cell(7,3,Color.black,
      Some(GameBaseImpl.Piece(Color.black, Queen.isQueen, Kicked.notKicked))), Color.black,
      Some(GameBaseImpl.Piece(Color.black, Queen.isQueen, Kicked.notKicked)), Some(0))
    val crownedLMCWhite : Game = crown.updateGame(GameBaseImpl.Cell(6,4,Color.black), Color.white).updateGame(GameBaseImpl.Cell(7,3,Color.black,
      Some(GameBaseImpl.Piece(Color.black, Queen.isQueen, Kicked.notKicked))), Color.white,
      Some(GameBaseImpl.Piece(Color.black, Queen.isQueen, Kicked.notKicked)), Some(0))
    val queenMoved : Game = crowned.updateGame(GameBaseImpl.Cell(7,3,Color.black),Color.black).updateGame(GameBaseImpl.Cell(6,4,Color.black,
      Some(GameBaseImpl.Piece(Color.black, Queen.isQueen, Kicked.notKicked))),Color.black)
    val losingWhitePieces : Game = game.updateGame(GameBaseImpl.Cell(7,7,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(GameBaseImpl.Cell(7,5,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(1)).
      updateGame(GameBaseImpl.Cell(7,3,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(2)).
      updateGame(GameBaseImpl.Cell(7,1,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(3)).
      updateGame(GameBaseImpl.Cell(6,6,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(4)).
      updateGame(GameBaseImpl.Cell(6,4,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(5)).
      updateGame(GameBaseImpl.Cell(6,2,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(6)).
      updateGame(GameBaseImpl.Cell(6,0,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(7)).
      updateGame(GameBaseImpl.Cell(5,7,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(8)).
      updateGame(GameBaseImpl.Cell(5,5,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(9)).
      updateGame(GameBaseImpl.Cell(5,3,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(10)).
      updateGame(GameBaseImpl.Cell(4,0,Color.black, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.white).
      updateGame(GameBaseImpl.Cell(2,0,Color.black), Color.white)
    val lostWhitePieces : Game = losingWhitePieces.updateGame(GameBaseImpl.Cell(5,1,Color.black), Color.black, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(11)).
      updateGame(GameBaseImpl.Cell(6,2,Color.black, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.black).
      updateGame(GameBaseImpl.Cell(4,0,Color.black), Color.black, None,None, Some(Color.black))
    val losingBlackPieces : Game = game.updateGame(GameBaseImpl.Cell(0,0,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(11)).
      updateGame(GameBaseImpl.Cell(0,2,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(1)).
      updateGame(GameBaseImpl.Cell(0,4,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(2)).
      updateGame(GameBaseImpl.Cell(0,6,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(3)).
      updateGame(GameBaseImpl.Cell(1,1,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(4)).
      updateGame(GameBaseImpl.Cell(1,3,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(5)).
      updateGame(GameBaseImpl.Cell(1,5,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(6)).
      updateGame(GameBaseImpl.Cell(1,7,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(7)).
      updateGame(GameBaseImpl.Cell(2,2,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(8)).
      updateGame(GameBaseImpl.Cell(2,4,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(9)).
      updateGame(GameBaseImpl.Cell(2,6,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(GameBaseImpl.Cell(4,4,Color.black, Some(piecesBlack.pieces(10))), Color.white).
      updateGame(GameBaseImpl.Cell(2,0,Color.black), Color.black)
    val lostBlackPieces : Game = losingBlackPieces.updateGame(GameBaseImpl.Cell(4,4,Color.black), Color.white,Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(10)).
      updateGame(GameBaseImpl.Cell(5,3,Color.black), Color.white).updateGame(GameBaseImpl.Cell(3,5,Color.black,Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.notKicked))), Color.white, None, None, Some(Color.white))
    val losingBlocked : Game = game.updateGame(GameBaseImpl.Cell(7,7,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(GameBaseImpl.Cell(7,5,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(1)).
      updateGame(GameBaseImpl.Cell(7,3,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(2)).
      updateGame(GameBaseImpl.Cell(7,1,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(3)).
      updateGame(GameBaseImpl.Cell(6,6,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(4)).
      updateGame(GameBaseImpl.Cell(6,4,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(5)).
      updateGame(GameBaseImpl.Cell(6,2,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(6)).
      updateGame(GameBaseImpl.Cell(5,1,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(7)).
      updateGame(GameBaseImpl.Cell(5,7,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(8)).
      updateGame(GameBaseImpl.Cell(5,5,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(9)).
      updateGame(GameBaseImpl.Cell(5,3,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.isKicked)), Some(10)).
      updateGame(GameBaseImpl.Cell(2,0,Color.black), Color.white).updateGame(GameBaseImpl.Cell(2,2,Color.black), Color.white).
      updateGame(GameBaseImpl.Cell(5,1,Color.black, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.white).
      updateGame(GameBaseImpl.Cell(3,1,Color.black, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.white)
    val lostBlocked : Game = losingBlocked.updateGame(GameBaseImpl.Cell(3,1,Color.black), Color.black).
      updateGame(GameBaseImpl.Cell(4,2,Color.black, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.black, None, None, Some(Color.black))
    val lostNoMove : Game = losingBlocked.updateGame(GameBaseImpl.Cell(3,1,Color.black), Color.white).
      updateGame(GameBaseImpl.Cell(4,2,Color.black, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.white, None, None, Some(Color.black))
    val queenNotBlocked : Game = losingBlocked.updateGame(GameBaseImpl.Cell(6,0, Color.black, Some(GameBaseImpl.Piece(Color.white, Queen.isQueen,
      Kicked.notKicked))),Color.white,Some(GameBaseImpl.Piece(Color.white, Queen.isQueen, Kicked.notKicked)), Some(11))
    val queenNotBlockedMoved : Game = queenNotBlocked.updateGame(GameBaseImpl.Cell(3,1,Color.black), Color.white).
      updateGame(GameBaseImpl.Cell(4,2,Color.black, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.black)
    val queenBlackNotBlocked : Game = game.updateGame(GameBaseImpl.Cell(0,0,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(0)).
      updateGame(GameBaseImpl.Cell(0,2,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(1)).
      updateGame(GameBaseImpl.Cell(0,4,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(2)).
      updateGame(GameBaseImpl.Cell(0,6,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(3)).
      updateGame(GameBaseImpl.Cell(1,1,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(4)).
      updateGame(GameBaseImpl.Cell(1,3,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(5)).
      updateGame(GameBaseImpl.Cell(1,5,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(6)).
      updateGame(GameBaseImpl.Cell(1,7,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(7)).
      updateGame(GameBaseImpl.Cell(2,2,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(8)).
      updateGame(GameBaseImpl.Cell(2,4,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(9)).
      updateGame(GameBaseImpl.Cell(2,6,Color.black), Color.white, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.isKicked)), Some(10)).
      updateGame(GameBaseImpl.Cell(5,1,Color.black), Color.white).
      updateGame(GameBaseImpl.Cell(3,1,Color.black, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.notKicked))), Color.black).
      updateGame(GameBaseImpl.Cell(2,0,Color.black, Some(GameBaseImpl.Piece(Color.black, Queen.isQueen, Kicked.notKicked))), Color.black,
        Some(GameBaseImpl.Piece(Color.black, Queen.isQueen, Kicked.notKicked)), Some(11))
    val queenBlackNotBlockedMoved : Game = queenBlackNotBlocked.updateGame(GameBaseImpl.Cell(5,3,Color.black), Color.white).
      updateGame(GameBaseImpl.Cell(4,2,Color.black, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.notKicked))), Color.white)
    val losingBlackBlocked : Game = queenBlackNotBlocked.updateGame(GameBaseImpl.Cell(2,0,Color.black, Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.notKicked))), Color.black,
      Some(GameBaseImpl.Piece(Color.black, Queen.notQueen, Kicked.notKicked)), Some(11))
    val blockingBlackQueen : Game = game.updateGame(GameBaseImpl.Cell(4,0,Color.black), Color.black).updateGame(GameBaseImpl.Cell(7,1,Color.black), Color.black).
      updateGame(GameBaseImpl.Cell(7,3,Color.black), Color.black).updateGame(GameBaseImpl.Cell(7,5,Color.black), Color.black).
      updateGame(GameBaseImpl.Cell(7,7,Color.black), Color.black).updateGame(GameBaseImpl.Cell(6,0,Color.black), Color.black).
      updateGame(GameBaseImpl.Cell(6,2,Color.black), Color.black).updateGame(GameBaseImpl.Cell(6,4,Color.black), Color.black).
      updateGame(GameBaseImpl.Cell(2,2,Color.black,Some(GameBaseImpl.Piece(Color.black,Queen.isQueen,Kicked.notKicked))),Color.black,Some(GameBaseImpl.Piece(Color.black,Queen.isQueen,Kicked.notKicked)),Some(0)).
      updateGame(GameBaseImpl.Cell(4,0,Color.black,Some(GameBaseImpl.Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(GameBaseImpl.Cell(4,2,Color.black,Some(GameBaseImpl.Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(GameBaseImpl.Cell(4,6,Color.black,Some(GameBaseImpl.Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(GameBaseImpl.Cell(3,1,Color.black,Some(GameBaseImpl.Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(GameBaseImpl.Cell(3,3,Color.black,Some(GameBaseImpl.Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(GameBaseImpl.Cell(3,5,Color.black,Some(GameBaseImpl.Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black).
      updateGame(GameBaseImpl.Cell(3,7,Color.black,Some(GameBaseImpl.Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.black)
    val blockedBlackQueen : Game = blockingBlackQueen.updateGame(GameBaseImpl.Cell(5,5,Color.black),Color.white).
      updateGame(GameBaseImpl.Cell(4,4,Color.black,Some(GameBaseImpl.Piece(Color.white,Queen.notQueen,Kicked.notKicked))),Color.white,None,None,Some(Color.white))
    val crownWhite : Game = losingBlackBlocked.updateGame(GameBaseImpl.Cell(3,1,Color.black), Color.black).updateGame(GameBaseImpl.Cell(1,1,Color.black,Some(piecesWhite.pieces(0))), Color.black)
    val crownedWhite : Game = crownWhite.updateGame(GameBaseImpl.Cell(1,1,Color.black),Color.white).updateGame(GameBaseImpl.Cell(0,0,Color.black, Some(GameBaseImpl.Piece(Color.white, Queen.isQueen, Kicked.notKicked))), Color.white, Some(GameBaseImpl.Piece(Color.white, Queen.isQueen, Kicked.notKicked)), Some(0))
    val lostBlackBlocked : Game = losingBlackBlocked.updateGame(GameBaseImpl.Cell(5,3,Color.black), Color.white).
      updateGame(GameBaseImpl.Cell(4,2,Color.black, Some(GameBaseImpl.Piece(Color.white, Queen.notQueen, Kicked.notKicked))), Color.white, None, None, Some(Color.white))
    val whiteQueen : Game = game.updateGame(GameBaseImpl.Cell(5,1,Color.black,Some(GameBaseImpl.Piece(Color.white, Queen.isQueen, Kicked.notKicked))),
      Color.black, Some(GameBaseImpl.Piece(Color.white, Queen.isQueen, Kicked.notKicked)), Some(0))
    val whiteQueenMoved : Game = whiteQueen.updateGame(GameBaseImpl.Cell(5,1,Color.black), Color.white).updateGame(GameBaseImpl.Cell(4,2,Color.black,
      Some(GameBaseImpl.Piece(Color.white, Queen.isQueen, Kicked.notKicked))),Color.white)
    val string : String = game.toString
    val lostString : String = lostWhitePieces.toString

    "be able to create new game" in {
      new Game() should be(game)
    }
    "be able to move a black Piece" in {
      game.movePiece(game.cell(2,0), game.cell(3,1)) should be(movedBlack)
    }
    "be able to undo move of black piece" in {
      movedBlack.undoMove(movedBlack.cell(3,1), movedBlack.cell(2,0)) should be(game)
    }
    "be able to move a white Piece" in {
      movedBlack.movePiece(movedBlack.cell(5,3), movedBlack.cell(4,2)) should be(movedWhite)
    }
    "be able to undo move of white piece" in {
      movedWhite.undoMove(movedWhite.cell(4,2), movedWhite.cell(5,3)) should be(movedBlack)
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
      kickWhite.movePiece(kickWhite.cell(2,2), kickWhite.cell(4,0)) should be(kickedWhite)
    }
    "be able to undo kicking of white piece" in {
      kickedWhite.undoMove(kickedWhite.cell(4,0), kickedWhite.cell(2,2)) should be(kickWhite)
    }
    "be able to kick a black piece" in {
      kickBlack.movePiece(kickBlack.cell(4,2), kickBlack.cell(2,0)) should be(kickedBlack)
    }
    "be able do undo kicking of black piece" in {
      kickedBlack.undoMove(kickedBlack.cell(2,0), kickedBlack.cell(4,2)) should be(kickBlack)
    }
    "be able to crown a black piece" in {
      crown.movePiece(crown.cell(6,4), crown.cell(7,3)) should be(crowned)
    }
    "be able to undo crowning of a black piece" in {
      crowned.undoMove(crowned.cell(7,3), crowned.cell(6,4)) should be(crown)
    }
    "be able to crown a white piece" in {
      crownWhite.movePiece(crownWhite.cell(1,1), crownWhite.cell(0,0)) should be(crownedWhite)
    }
    "be able to undo crowning of a white piece" in {
      crownedWhite.undoMove(crownedWhite.cell(0,0), crownedWhite.cell(1,1)) should be(crownWhite)
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
    "be able to tell the last moved color" in {
      movedBlack.getLastMoveColor should be(Color.black)
    }
    "be able to tell the winning color" in {
      lostBlackPieces.getWinnerColor should be(Some(Color.white))
    }
    "be able to return a board" in {
      game.getBoard should be(board)
    }
  }
}
