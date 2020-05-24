package de.htwg.se.checkers.model

case class Board(cells: Matrix[Cell]) {
  def this() = this(new Matrix[Cell](Cell(0,0,Color.white)))

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
    if(blackCell(i,j) && blackPiece(i)) copy(cells.replaceCell(i,j,Cell(i, j, Color.black, Some(piecesBlack(pb)))))
    else if(blackCell(i,j) && whitePiece(i)) copy(cells.replaceCell(i,j,Cell(i, j, Color.black, Some(piecesWhite(pw)))))
    else if(blackCell(i,j)) copy(cells.replaceCell(i,j,Cell(i, j, Color.black)))
    else copy(cells.replaceCell(i,j,Cell(i, j, Color.white)))
  }

  private def blackCell(i:Int, j:Int): Boolean = (i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)

  private def blackPiece(i:Int): Boolean = i <= 2

  private def whitePiece(i:Int): Boolean = i >= 5
}