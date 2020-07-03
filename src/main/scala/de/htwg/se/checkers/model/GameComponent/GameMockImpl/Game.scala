package de.htwg.se.checkers.model.GameComponent.GameMockImpl

import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Board, Cell, Color, Kicked, Piece, Queen}
import de.htwg.se.checkers.model.GameComponent.{CellTrait, GameTrait}

case class Game(board: Board, pb: Vector[Piece], pw: Vector[Piece], lmc: Color.Value, winnerColor : Option[Color.Value] = None) extends GameTrait{

  override def movePiece(s:CellTrait, d:CellTrait): GameTrait = this

  override def undoMove(s:CellTrait, d:CellTrait): GameTrait = this

  override def updatePiece(s:CellTrait, d:CellTrait): Board = this.board

  override def checkRules(s:CellTrait, d:CellTrait): (Option[Vector[Piece]], Option[Vector[Piece]], Color.Value) = (None, None, this.lmc)

  override def pieceColorCheck(start:CellTrait): Boolean = false

  override def cellColorCheck(destination:CellTrait): Boolean = false

  override def cellEmptyCheck(destination:CellTrait): Boolean = false

  override def opponentPieces(start:CellTrait): Vector[Piece] = this.pw

  override def moveQueenRules(s:CellTrait, d:CellTrait): Option[Vector[Piece]] = None

  override def queenDestinationCheck(start:CellTrait, destination:CellTrait): Vector[Piece] = this.pw

  override def deQueenDestinationCheck(s:CellTrait, d:CellTrait, tempboard:Board) : Option[(Board,Vector[Piece],Vector[Piece])] = None

  override def crown(pieces: Vector[Piece], index: Int): Vector[Piece] = this.pb

  override def deCrown(pieces: Vector[Piece], index: Int): Vector[Piece] = this.pb

  override def moveBlackRules(s:CellTrait, d:CellTrait): Option[Vector[Piece]] = None

  override def moveWhiteRules(s:CellTrait, d:CellTrait): Option[Vector[Piece]] = None

  override def kickPieceCheck(s:CellTrait, d:CellTrait): Option[Vector[Piece]] = None

  override def updatePiecesKicked(middlePiece: Piece): Vector[Piece] = this.pw

  override def deKickPieceCheck(s:CellTrait, d:CellTrait, tempboard:Board, opponentColor:Color.Value) : Option[(Board,Vector[Piece],Vector[Piece])] = None

  override def kickPiece(pieces: Vector[Piece], index: Int): Vector[Piece] = this.pw

  override def deKickPiece(pieces: Vector[Piece], index: Int): Vector[Piece] = this.pw

  override def middleCellCalc(s:CellTrait, d:CellTrait): Option[Cell] = None

  override def middleCellCheck(start:CellTrait, middleCell:CellTrait): Boolean = false

  override def countKickedPieces(): (Int, Int) = (0,0)

  override def countKickedPieces(pieces: Vector[Piece]): Int = 0

  override def cell(y:Int, x:Int): CellTrait = EmptyCell

  override def hasWon(start:CellTrait, destination:CellTrait) : Option[Color.Value] = None

  override def isBlocked(board: Board) : Option[Color.Value] = None

  override def isBlackBlocked(board: Board): Boolean = false

  override def isWhiteBlocked(board: Board): Boolean = false

  override def plusCheck(start:CellTrait, board: Board): Boolean = false

  override def minusCheck(start:CellTrait, board: Board) : Boolean = false

  override def yxPlusOneCheck(s:CellTrait) : Boolean = false

  override def yxPlusMinusOneCheck(s:CellTrait) : Boolean = false

  override def yxMinusOneCheck(s:CellTrait) : Boolean = false

  override def yxMinusPlusOneCheck(s:CellTrait) : Boolean = false

  override def yxPlusTwoCheck(s:CellTrait) : Boolean = false

  override def yxPlusMinusTwoCheck(s:CellTrait) : Boolean = false

  override def yxMinusTwoCheck(s:CellTrait) : Boolean = false

  override def yxMinusPlusTwoCheck(s:CellTrait) : Boolean = false

  override def toString: String = ""

  override def updateGame(cell: CellTrait, lmc:Color.Value, piece: Option[Piece] = None, index: Option[Int] = None, winner:Option[Color.Value] = None) : GameTrait = this

  override def getLastMoveColor(): Color.Value = Color.white

  override def getWinnerColor(): Option[Color.Value] = winnerColor

  override def getBoard(): Board = board

  override def getPB(index: Int): Piece = pb(0)

  override def getPW(index: Int): Piece = pw(0)
}

object EmptyCell extends CellTrait{
  override def x: Int = 0
  override def y: Int = 7

  override def color: Color.Value = Color.black

  override def piece: Option[Piece] = Option(Piece(Color.white, Queen.notQueen, Kicked.notKicked))
}
