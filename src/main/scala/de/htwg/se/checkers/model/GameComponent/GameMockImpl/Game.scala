package de.htwg.se.checkers.model.GameComponent.GameMockImpl

import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Board, Cell, Color, Piece}
import de.htwg.se.checkers.model.GameComponent.{CellTrait, GameTrait}

case class Game(board: Board, pb: Vector[Piece], pw: Vector[Piece], lmc: Color.Value, winnerColor : Option[Color.Value] = None) extends GameTrait{

  override def movePiece(s:Cell, d:Cell): GameTrait = this

  override def undoMove(s:Cell, d:Cell): GameTrait = this

  override def updatePiece(s:Cell, d:Cell): Board = this.board

  override def checkRules(s:Cell, d:Cell): (Option[Vector[Piece]], Option[Vector[Piece]], Color.Value) = (None, None, this.lmc)

  override def pieceColorCheck(start:Cell): Boolean = false

  override def cellColorCheck(destination:Cell): Boolean = false

  override def cellEmptyCheck(destination:Cell): Boolean = false

  override def opponentPieces(start:Cell): Vector[Piece] = this.pw

  override def moveQueenRules(s:Cell, d:Cell): Option[Vector[Piece]] = None

  override def queenDestinationCheck(start:Cell, destination:Cell): Vector[Piece] = this.pw

  override def deQueenDestinationCheck(s:Cell, d:Cell, tempboard:Board) : Option[(Board,Vector[Piece],Vector[Piece])] = None

  override def crown(pieces: Vector[Piece], index: Int): Vector[Piece] = this.pb

  override def deCrown(pieces: Vector[Piece], index: Int): Vector[Piece] = this.pb

  override def moveBlackRules(s:Cell, d:Cell): Option[Vector[Piece]] = None

  override def moveWhiteRules(s:Cell, d:Cell): Option[Vector[Piece]] = None

  override def kickPieceCheck(s:Cell, d:Cell): Option[Vector[Piece]] = None

  override def updatePiecesKicked(middlePiece: Piece): Vector[Piece] = this.pw

  override def deKickPieceCheck(s:Cell, d:Cell, tempboard:Board, opponentColor:Color.Value) : Option[(Board,Vector[Piece],Vector[Piece])] = None

  override def kickPiece(pieces: Vector[Piece], index: Int): Vector[Piece] = this.pw

  override def deKickPiece(pieces: Vector[Piece], index: Int): Vector[Piece] = this.pw

  override def middleCellCalc(s:Cell, d:Cell): Option[Cell] = None

  override def middleCellCheck(start:Cell, middleCell:Cell): Boolean = false

  override def countKickedPieces(): (Int, Int) = (0,0)

  override def countKickedPieces(pieces: Vector[Piece]): Int = 0

  override def cell(y:Int, x:Int): CellTrait = EmptyCell

  override def hasWon(start:Cell, destination:Cell) : Option[Color.Value] = None

  override def isBlocked(board: Board) : Option[Color.Value] = None

  override def isBlackBlocked(board: Board): Boolean = false

  override def isWhiteBlocked(board: Board): Boolean = false

  override def plusCheck(start:Cell, board: Board): Boolean = false

  override def minusCheck(start:Cell, board: Board) : Boolean = false

  override def yxPlusOneCheck(s:Cell) : Boolean = false

  override def yxPlusMinusOneCheck(s:Cell) : Boolean = false

  override def yxMinusOneCheck(s:Cell) : Boolean = false

  override def yxMinusPlusOneCheck(s:Cell) : Boolean = false

  override def yxPlusTwoCheck(s:Cell) : Boolean = false

  override def yxPlusMinusTwoCheck(s:Cell) : Boolean = false

  override def yxMinusTwoCheck(s:Cell) : Boolean = false

  override def yxMinusPlusTwoCheck(s:Cell) : Boolean = false

  override def toString: String = ""

  override def updateGame(cell: Cell, lmc:Color.Value, piece: Option[Piece] = None, index: Option[Int] = None, winner:Option[Color.Value] = None) : GameTrait = this
}

object EmptyCell extends CellTrait{
  override def x: Int = 0
  override def y: Int = 7
}

