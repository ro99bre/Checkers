package de.htwg.se.checkers.model

case class Board(cells: Matrix[Cell]) {
  def this() = this(new Matrix[Cell](Cell(0,0,Color.white)))

  /**
   * @param piecesBlack TODO
   * @param piecesWhite TODO
   *
   * @return Returns board with colored cells and pieces in starting position
   * */

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
    if(blackCell(i,j) && blackPiece(i)) {
      copy(cells.replaceCell(i,j,Cell(i, j, Color.black, Some(piecesBlack(pb)))))
    } else if(blackCell(i,j) && whitePiece(i)) {
      copy(cells.replaceCell(i,j,Cell(i, j, Color.black, Some(piecesWhite(pw)))))
    } else if(blackCell(i,j)) {
      copy(cells.replaceCell(i,j,Cell(i, j, Color.black)))
    } else {
      copy(cells.replaceCell(i,j,Cell(i, j, Color.white)))
    }
  }

  //checks if a cell should be black
  private def blackCell(i:Int, j:Int): Boolean = {
    (i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)
  }

  //checks if a piece should be black
  private def blackPiece(i:Int): Boolean = {
    i <= 2
  }

  //checks if a piece should be white
  private def whitePiece(i:Int): Boolean = {
    i >= 5
  }

  /**
   *  @param s TODO
   *  @param d TODO
   *  @param lmc TODO
   *  @param pc1 TODO
   *  @param pc2 TODO
   *
   *  Moves a pieces after checking all the rules
   *
   *  @return Returns a new board, two vectors of pieces and a color (lastMoveColor)
   * */

  def movePiece(s:Cell, d:Cell, lmc:Color.Value, pc1:Vector[Piece], pc2:Vector[Piece]): (Board, Vector[Piece], Vector[Piece], Color.Value) = {
    val pb:Vector[Piece] = color(pc1, pc2)._1 //piecesBlack
    val pw:Vector[Piece] = color(pc1, pc2)._2 //piecesWhite
    checkRules(s, d, lmc, pb, pw) match {
      case (Some(_), Some(_), _) => (this.updatePiece(s,d),checkRules(s, d, lmc, pb, pw)._1.get,
        checkRules(s, d, lmc, pb, pw)._2.get, checkRules(s, d, lmc, pb, pw)._3)
      case (_,_,_) => (this, allPieces(s, lmc, pb, pw)._1, allPieces(s,lmc, pb, pw)._2, lmc)
    }
  }

  //checks colors of the pieces, returns the vector of black pieces and the vector of white pieces
  private def color (pc1:Vector[Piece], pc2:Vector[Piece]): (Vector[Piece], Vector[Piece]) = {
    pc1(0).color match {
      case Color.black => (pc1, pc2)
      case Color.white => (pc2, pc1)
    }
  }

  //updates cells after piece has been moved (and kicked), returns new board
  private def updatePiece(s:Cell, d:Cell): Board = { //s=start d=destination
    var temp: Board = this.copy(this.cells.replaceCell(d.y,d.x,Cell(d.y, d.x, d.color, s.piece)))
    temp = temp.copy(temp.cells.replaceCell(s.y, s.x, Cell(s.y, s.x, s.color, None)))
    if(middleCellCalc(s,d).isDefined) {
      val middleCell : Cell= middleCellCalc(s,d).get
      temp = temp.copy(temp.cells.replaceCell(middleCell.y, middleCell.x, Cell(middleCell.y, middleCell.x, middleCell.color, None)))
    }
    temp
  }

  //checks all the rules, returns two optional vectors of pieces and a color
  private def checkRules(s:Cell, d:Cell, lmc:Color.Value, pb:Vector[Piece], pw:Vector[Piece]): (Option[Vector[Piece]], Option[Vector[Piece]], Color.Value) = { //return vector of pieces
    if (s.piece.isDefined) {
      if (pieceColorCheck(s, lmc) && cellColorCheck(d) && cellEmptyCheck(d)) {
        if (s.piece.get.queen == Queen.isQueen) {
          return (Some(allPieces(s, lmc, pb, pw)._1),moveQueenRules(s, d, lmc, pb, pw), updateLastMoveColor(s))
        } else if (s.piece.get.color == Color.black) {
          return (Some(queenDestinationCheck(s, d, pb, pw)),moveBlackRules(s, d, lmc, pb, pw), updateLastMoveColor(s))
        } else if (s.piece.get.color == Color.white) {
          return (Some(queenDestinationCheck(s, d, pb, pw)),moveWhiteRules(s, d, lmc, pb, pw), updateLastMoveColor(s))
        }
      }
    }
    (None, None, lmc)
  }

  //checks if the piece is allowed to move according to the color
  private def pieceColorCheck(start:Cell, lastMoveColor:Color.Value): Boolean = {
    start.piece.get.color != lastMoveColor
  }

  //checks if the color of the destination cell is black
  private def cellColorCheck(destination:Cell): Boolean = {
    destination.color == Color.black
  }

  //checks if the destination cell is empty
  private def cellEmptyCheck(destination:Cell): Boolean = {
    destination.piece.isEmpty
  }

  //returns your own pieces and opponent pieces
  private def allPieces(start:Cell, lastMoveColor:Color.Value, piecesBlack:Vector[Piece], piecesWhite:Vector[Piece]): (Vector[Piece], Vector[Piece]) = {
    var color:Color.Value = lastMoveColor
    if (start.piece.isDefined) {
      color = start.piece.get.color
    }
    color match {
      case Color.black => (piecesBlack, piecesWhite)
      case Color.white => (piecesWhite, piecesBlack)
    }
  }

  //returns the color of the moving piece for updating the color of the last move
  private def updateLastMoveColor(start:Cell): Color.Value = {
    start.piece.get.color
  }

  //checks if a queen is allowed to move to destination cell, returns an optional vector of the opponents' pieces
  private def moveQueenRules(s:Cell, d:Cell, lastMoveColor:Color.Value, pb:Vector[Piece], pw:Vector[Piece]): Option[Vector[Piece]] = {
    if ((d.x + 1 == s.x || d.x - 1 == s.x) && (d.y - 1 == s.y || d.y + 1 == s.y)) {
      return Some(allPieces(s, lastMoveColor, pb, pw)._2)
    } else if ((d.x + 2 == s.x || d.x - 2 == s.x) && (d.y - 2 == s.y || d.y + 2 == s.y)) {
      return kickPieceCheck(s, d, pb, pw)
    }
    None
  }

  //checks if a piece has arrived at the other side of the board and crowns it, returns updated vector of pieces
  private def queenDestinationCheck(start:Cell, destination:Cell, piecesBlack:Vector[Piece], piecesWhite:Vector[Piece]): Vector[Piece] = { //return array of pieces
    (start.piece.get.color, destination.x) match {
      case (Color.black, 7) => crown(piecesBlack, piecesBlack.indexOf(start.piece.get))
      case (Color.white, 0) => crown(piecesWhite, piecesWhite.indexOf(start.piece.get))
      case (Color.black,_) => piecesBlack
      case(Color.white,_) => piecesWhite
    }
  }

  //crowns a piece to be be king/queen, returns updated vector of pieces
  private def crown(pieces: Vector[Piece], index: Int): Vector[Piece] = {
    pieces.updated(index, Piece(pieces(index).color, Queen.isQueen, pieces(index).kicked))
  }

  //checks if a black piece (no queen) is allowed to move to destination cell, returns an optional vector of the opponents' pieces
  private def moveBlackRules(s:Cell, d:Cell, lastMoveColor:Color.Value, piecesBlack:Vector[Piece], piecesWhite:Vector[Piece]): Option[Vector[Piece]] = { //return option pieces, pieces in method for moving piece
    if (d.y - 1 == s.y && (d.x - 1 == s.x || d.x + 1 == s.x)) {
      return Some(allPieces(s, lastMoveColor, piecesBlack, piecesWhite)._2)
    } else if (d.y - 2 == s.y && (d.x - 2 == s.x || d.x + 2 == s.x)) {
      return kickPieceCheck(s, d, piecesBlack, piecesWhite)
    }
    None
  }

  //checks if a white piece (no queen) is allowed to move to destination cell, returns an optional vector of the opponents' pieces
  private def moveWhiteRules(s:Cell, d:Cell, lastMoveColor:Color.Value, piecesBlack:Vector[Piece], piecesWhite:Vector[Piece]): Option[Vector[Piece]] = {
    if (d.y + 1 == s.y && (d.x - 1 == s.x || d.x + 1 == s.x)) {
      return Some(allPieces(s, lastMoveColor, piecesBlack, piecesWhite)._2)
    } else if (d.y + 2 == s.y && (d.x - 2 == s.x || d.x + 2 == s.x)) {
      return kickPieceCheck(s, d, piecesBlack, piecesWhite)
    }
    None
  }

  //checks if a piece can be kicked, returns an optional vector of the opponents' pieces if piece can be kicked
  private def kickPieceCheck(s:Cell, d:Cell, piecesBlack:Vector[Piece], piecesWhite:Vector[Piece]): Option[Vector[Piece]] = {
    val middlePiece :Cell = middleCellCalc(s, d).get
    if (middleCellCheck(s, middlePiece)) {
      return Some(updatePiecesKicked(middlePiece.piece.get, piecesBlack, piecesWhite))
    }
    None
  }

  //kills/captures a game piece of according color, returns updated vector of pieces
  private def updatePiecesKicked(middlePiece: Piece, piecesBlack:Vector[Piece], piecesWhite:Vector[Piece]): Vector[Piece] = {
    middlePiece.color match {
      case Color.black => kickPiece(piecesBlack, piecesBlack.indexOf(middlePiece))
      case Color.white => kickPiece(piecesWhite, piecesWhite.indexOf(middlePiece))
    }
  }

  //kills/captures a game piece, returns updated vector of pieces
  private def kickPiece(pieces: Vector[Piece], index: Int): Vector[Piece] = {
    pieces.updated(index, Piece(pieces(index).color, pieces(index).queen, Kicked.isKicked))
  }

  //calculates a piece to be killed in case of jumping over it
  private def middleCellCalc(s:Cell, d:Cell): Option[Cell] = {
    //vals with upperCase first letter as "stable identifier"
    val YPlus :Int = d.y+2
    val YMinus :Int = d.y-2
    val XPlus :Int = d.x+2
    val XMinus :Int = d.x-2
    (s.y, s.x) match {
      case (YMinus, XMinus) => Some(cells.cell(d.y-1,d.x-1))
      case (YMinus, XPlus) => Some(cells.cell(d.y-1,d.x+1))
      case (YPlus, XMinus) => Some(cells.cell(d.y+1,d.x-1))
      case (YPlus, XPlus) => Some(cells.cell(d.y+1,d.x+1))
      case (_,_) => None
    }
  }

  //checks if a piece can jump over a piece to kill it
  private def middleCellCheck(start:Cell, middleCell:Cell): Boolean = {
    middleCell.piece.isDefined && (middleCell.piece.get.color != start.piece.get.color)
  }

  /**
   * @param pc1 TODO
   * @param pc2 TODO
   *
   *  counts kicked pieces of two vectors
   *
   * @return Returns an Integer representing the amount of kicked pieces per Player
   *         First the amount of the plack pieces
   *         Then the amount of the white pieces
   * */

  def countKickedPieces(pc1: Vector[Piece], pc2: Vector[Piece]): (Int, Int) = {
    (pc1(0).color, pc2(0).color) match {
      case (Color.black,_) => (countKickedPieces(pc1), countKickedPieces(pc2))
      case (Color.white,_) => (countKickedPieces(pc2), countKickedPieces(pc1))
    }
  }

  //counts kicked pieces of a given vector, returns amount in int
  private def countKickedPieces(pieces: Vector[Piece]): Int = {
    var counter :Int = 0
    for (i <- pieces.indices) {
      if (pieces(i).kicked == Kicked.isKicked) {
        counter += 1
      }
    }
    counter
  }
}
//@TODO isBlocked(), hasWon()