package de.htwg.se.checkers.model.GameComponent

import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Board, Cell, Color, Piece}

trait GameTrait {

  def movePiece(s:Cell, d:Cell): GameTrait
  def undoMove(s:Cell, d:Cell): GameTrait
  def updatePiece(s:Cell, d:Cell): Board
  def checkRules(s:Cell, d:Cell): (Option[Vector[Piece]], Option[Vector[Piece]], Color.Value)
  def pieceColorCheck(start:Cell): Boolean
  def cellColorCheck(destination:Cell): Boolean
  def cellEmptyCheck(destination:Cell): Boolean
  def opponentPieces(start:Cell): Vector[Piece]
  def moveQueenRules(s:Cell, d:Cell): Option[Vector[Piece]]
  def queenDestinationCheck(start:Cell, destination:Cell): Vector[Piece]
  def deQueenDestinationCheck(s:Cell, d:Cell, tempboard:Board) : Option[(Board,Vector[Piece],Vector[Piece])]
  def crown(pieces: Vector[Piece], index: Int): Vector[Piece]
  def deCrown(pieces: Vector[Piece], index: Int): Vector[Piece]
  def moveBlackRules(s:Cell, d:Cell): Option[Vector[Piece]]
  def moveWhiteRules(s:Cell, d:Cell): Option[Vector[Piece]]
  def kickPieceCheck(s:Cell, d:Cell): Option[Vector[Piece]]
  def updatePiecesKicked(middlePiece: Piece): Vector[Piece]
  def deKickPieceCheck(s:Cell, d:Cell, tempboard:Board, opponentColor:Color.Value) : Option[(Board,Vector[Piece],Vector[Piece])]
  def kickPiece(pieces: Vector[Piece], index: Int): Vector[Piece]
  def deKickPiece(pieces: Vector[Piece], index: Int): Vector[Piece]
  def middleCellCalc(s:Cell, d:Cell): Option[Cell]
  def middleCellCheck(start:Cell, middleCell:Cell): Boolean
  def countKickedPieces(): (Int, Int)
  def countKickedPieces(pieces: Vector[Piece]): Int
  def cell(y:Int, x:Int): CellTrait
  def hasWon(start:Cell, destination:Cell) : Option[Color.Value]
  def isBlocked(board: Board) : Option[Color.Value]
  def isBlackBlocked(board: Board): Boolean
  def isWhiteBlocked(board: Board): Boolean
  def plusCheck(start:Cell, board: Board): Boolean
  def minusCheck(start:Cell, board: Board) : Boolean
  def yxPlusOneCheck(s:Cell) : Boolean
  def yxPlusMinusOneCheck(s:Cell) : Boolean
  def yxMinusOneCheck(s:Cell) : Boolean
  def yxMinusPlusOneCheck(s:Cell) : Boolean
  def yxPlusTwoCheck(s:Cell) : Boolean
  def yxPlusMinusTwoCheck(s:Cell) : Boolean
  def yxMinusTwoCheck(s:Cell) : Boolean
  def yxMinusPlusTwoCheck(s:Cell) : Boolean
  def updateGame(cell: Cell, lmc:Color.Value, piece: Option[Piece] = None, index: Option[Int] = None, winner:Option[Color.Value] = None) : GameTrait


  def getLastMoveColor() : Color.Value
  def getWinnerColor() : Option[Color.Value]
}

trait CellTrait {

  def x:Int
  def y:Int

}
