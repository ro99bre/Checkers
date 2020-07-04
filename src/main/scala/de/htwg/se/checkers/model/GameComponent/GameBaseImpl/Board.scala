package de.htwg.se.checkers.model.GameComponent.GameBaseImpl

import de.htwg.se.checkers.model.GameComponent.{CellTrait, GameBaseImpl}

case class Board(cells: Matrix[CellTrait]) {
  def this() = this(new Matrix[CellTrait](GameBaseImpl.Cell(0,0,Color.white)))

  def createBoard(piecesBlack:Vector[Piece], piecesWhite:Vector[Piece]): Board = {
    var temp: Board = this
    for (i <- 0 until 8;
         j <- 0 until 8;
         pb <- 0 until 12;
         pw <- 0 until 12){
      temp = temp.createBoardR(i,j, pb, pw, piecesBlack, piecesWhite)
    }
    temp
  }

  //colors the cells and puts the pieces in the starting position
  private def createBoardR(i:Int, j:Int, pb:Int, pw:Int, piecesBlack:Vector[Piece], piecesWhite:Vector[Piece]) :Board = {
    if(blackCell(i,j) && blackPiece(i)) copy(cells.replaceCell(i,j,GameBaseImpl.Cell(i, j, Color.black, Some(piecesBlack(pb)))))
    else if(blackCell(i,j) && whitePiece(i)) copy(cells.replaceCell(i,j,GameBaseImpl.Cell(i, j, Color.black, Some(piecesWhite(pw)))))
    else if(blackCell(i,j)) copy(cells.replaceCell(i,j,GameBaseImpl.Cell(i, j, Color.black)))
    else copy(cells.replaceCell(i,j,GameBaseImpl.Cell(i, j, Color.white)))
  }

  def setCell(y: Int, x: Int, color: Color.Value, piececolor: Option[Color.Value], queen: Option[Queen.Value], kicked: Option[Kicked.Value]): Board = {
    if (piececolor.isEmpty) this.copy(this.cells.replaceCell(y,x,Cell(y,x,color,None)))
    else this.copy(this.cells.replaceCell(y,x,Cell(y,x,color,Some(Piece(piececolor.get,queen.get,kicked.get)))))
  }

  private def blackCell(i:Int, j:Int): Boolean = (i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)

  private def blackPiece(i:Int): Boolean = i <= 2

  private def whitePiece(i:Int): Boolean = i >= 5
}
