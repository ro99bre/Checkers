package de.htwg.se.checkers.model

case class Game(board: Board, pb: Vector[Piece], pw: Vector[Piece], lmc: Color.Value, winnerColor : Option[Color.Value] = None) {

  def movePiece(s:Cell, d:Cell): Game = {
    if (winnerColor.isDefined) return this
    checkRules(s, d) match {
      case (Some(_), Some(_), _) => Game(this.updatePiece(s,d),checkRules(s, d)._1.get,
        checkRules(s, d)._2.get, checkRules(s, d)._3, hasWon(s, d))
      case (_,_,_) => this
    }
  }

  //updates cells after piece has been moved (and kicked), returns new board
  private def updatePiece(s:Cell, d:Cell): Board = {//s=start d=destination
    var piece : Piece = s.piece.get
    piece.color match {
      case Color.black => piece = checkRules(s, d)._1.get(pb.indexOf(piece))
      case Color.white => piece = checkRules(s, d)._2.get(pw.indexOf(piece))
    }
    var temp: Board = board.copy(board.cells.replaceCell(d.y,d.x,Cell(d.y, d.x, d.color, Some(piece))))
    temp = temp.copy(temp.cells.replaceCell(s.y, s.x, Cell(s.y, s.x, s.color)))
    if(middleCellCalc(s,d).isDefined) {
      val middleCell : Cell= middleCellCalc(s,d).get
      temp = temp.copy(temp.cells.replaceCell(middleCell.y, middleCell.x, Cell(middleCell.y, middleCell.x, middleCell.color)))
    }
    temp
  }

  //returns black, then white pieces and color of move after rules have been checked; returns None if move is invalid
  private def checkRules(s:Cell, d:Cell): (Option[Vector[Piece]], Option[Vector[Piece]], Color.Value) = {
    if (s.piece.isDefined && pieceColorCheck(s) && cellColorCheck(d) && cellEmptyCheck(d)) {
      val startColor : Color.Value = s.piece.get.color
      if (s.piece.get.queen == Queen.isQueen) startColor match {
          case Color.black => return (Some(pb), moveQueenRules(s,d), startColor)
          case Color.white => return (moveQueenRules(s,d), Some(pw), startColor)
        }
      else startColor match {
          case Color.black => return (Some(queenDestinationCheck(s, d)), moveBlackRules(s, d), startColor)
          case Color.white => return (moveWhiteRules(s, d), Some(queenDestinationCheck(s, d)), startColor)
        }
    }
    (None, None, lmc)
  }

  private def pieceColorCheck(start:Cell): Boolean = start.piece.get.color != lmc

  private def cellColorCheck(destination:Cell): Boolean = destination.color == Color.black

  private def cellEmptyCheck(destination:Cell): Boolean = destination.piece.isEmpty

  private def opponentPieces(start:Cell): Vector[Piece] = {
    var color:Color.Value = lmc
    if (start.piece.isDefined) color = start.piece.get.color
    color match {
      case Color.black => pw
      case Color.white => pb
    }
  }

  private def moveQueenRules(s:Cell, d:Cell): Option[Vector[Piece]] = {
    if ((d.x + 1 == s.x || d.x - 1 == s.x) && (d.y - 1 == s.y || d.y + 1 == s.y)) return Some(opponentPieces(s))
    else if ((d.x + 2 == s.x || d.x - 2 == s.x) && (d.y - 2 == s.y || d.y + 2 == s.y)) return kickPieceCheck(s, d)
    None
  }

  //checks if a piece has arrived at the other side of the board and crowns it, returns updated vector of pieces
  private def queenDestinationCheck(start:Cell, destination:Cell): Vector[Piece] = {
    (start.piece.get.color, destination.y) match {
      case (Color.black, 7) => crown(pb, pb.indexOf(start.piece.get))
      case (Color.white, 0) => crown(pw, pw.indexOf(start.piece.get))
      case (Color.black,_) => pb
      case(Color.white,_) => pw
    }
  }

  private def crown(pieces: Vector[Piece], index: Int): Vector[Piece] = pieces.updated(index, Piece(pieces(index).color,
    Queen.isQueen, pieces(index).kicked))

  private def moveBlackRules(s:Cell, d:Cell): Option[Vector[Piece]] = {
    if (d.y - 1 == s.y && (d.x - 1 == s.x || d.x + 1 == s.x)) return Some(opponentPieces(s))
    else if (d.y - 2 == s.y && (d.x - 2 == s.x || d.x + 2 == s.x)) return kickPieceCheck(s, d)
    None
  }

  private def moveWhiteRules(s:Cell, d:Cell): Option[Vector[Piece]] = {
    if (d.y + 1 == s.y && (d.x - 1 == s.x || d.x + 1 == s.x)) return Some(opponentPieces(s))
    else if (d.y + 2 == s.y && (d.x - 2 == s.x || d.x + 2 == s.x)) return kickPieceCheck(s, d)
    None
  }

  private def kickPieceCheck(s:Cell, d:Cell): Option[Vector[Piece]] = {
    val middlePiece :Cell = middleCellCalc(s, d).get
    if (middleCellCheck(s, middlePiece)) return Some(updatePiecesKicked(middlePiece.piece.get))
    None
  }

  private def updatePiecesKicked(middlePiece: Piece): Vector[Piece] = {
    middlePiece.color match {
      case Color.black => kickPiece(pb, pb.indexOf(middlePiece))
      case Color.white => kickPiece(pw, pw.indexOf(middlePiece))
    }
  }

  private def kickPiece(pieces: Vector[Piece], index: Int): Vector[Piece] = pieces.updated(index, Piece(pieces(index).color, pieces(index).queen, Kicked.isKicked))

  //calculates a piece to be killed in case of jumping over it
  private def middleCellCalc(s:Cell, d:Cell): Option[Cell] = {
    //vals with upperCase first letter as "stable identifier"
    val YPlus :Int = d.y+2
    val YMinus :Int = d.y-2
    val XPlus :Int = d.x+2
    val XMinus :Int = d.x-2
    (s.y, s.x) match {
      case (YMinus, XMinus) => Some(board.cells.cell(d.y-1,d.x-1))
      case (YMinus, XPlus) => Some(board.cells.cell(d.y-1,d.x+1))
      case (YPlus, XMinus) => Some(board.cells.cell(d.y+1,d.x-1))
      case (YPlus, XPlus) => Some(board.cells.cell(d.y+1,d.x+1))
      case (_,_) => None
    }
  }

  private def middleCellCheck(start:Cell, middleCell:Cell): Boolean = middleCell.piece.isDefined && (middleCell.piece.get.color != start.piece.get.color)

  def countKickedPieces(): (Int, Int) = (countKickedPieces(pb), countKickedPieces(pw))

  private def countKickedPieces(pieces: Vector[Piece]): Int = {
    var counter :Int = 0
    for (i <- pieces.indices) {
      if (pieces(i).kicked == Kicked.isKicked) counter += 1
    }
    counter
  }

  def cell(y:Int, x:Int): Cell = board.cells.cell(y,x)

  private def hasWon(start:Cell, destination:Cell) : Option[Color.Value] = {
    if(countKickedPieces(checkRules(start,destination)._1.get) == 12) Some(Color.white)
    else if(countKickedPieces(checkRules(start,destination)._2.get) == 12) Some(Color.black)
    else isBlocked(updatePiece(start,destination))
  }

  private def isBlocked(board: Board) : Option[Color.Value] = {//returns winner color
    if (isBlackBlocked(board)) Some(Color.white)
    else if (isWhiteBlocked(board)) Some(Color.black)
    else None
  }

  private def isBlackBlocked(board: Board): Boolean = {
    var bool : Boolean = false
    for (y <- 0 until 8;
         x <- 0 until 8) {
      var start : Cell = board.cells.cell(y,x)
      if (start.piece.isDefined && start.piece.get.color == Color.black) {
        if (plusCheck(start, board)) bool = true
        else return false
        if (start.piece.get.queen == Queen.isQueen) {
          if (minusCheck(start, board)) bool = true
          else return false
        }
      }
    }
    bool
  }

  private def isWhiteBlocked(board: Board): Boolean = {
    var bool : Boolean = false
    for (y <- 0 until 8;
         x <- 0 until 8) {
      var start : Cell = board.cells.cell(y,x)
      if (start.piece.isDefined && start.piece.get.color == Color.white) {
        if (minusCheck(start, board)) bool = true
        else return false
        if (start.piece.get.queen == Queen.isQueen) {
          if (plusCheck(start, board)) bool = true
          else return false
        }
      }
    }
    bool
  }

  private def plusCheck(start:Cell, board: Board): Boolean = {
    if (yxPlusOneCheck(start) && cellEmptyCheck(board.cells.cell(start.y+1, start.x+1))) return false
    if (yxPlusMinusOneCheck(start) && cellEmptyCheck(board.cells.cell(start.y+1, start.x-1))) return false
    if (yxPlusTwoCheck(start) && cellEmptyCheck(board.cells.cell(start.y+2, start.x+2))) return false
    if (yxPlusMinusTwoCheck(start) && cellEmptyCheck(board.cells.cell(start.y+2, start.x-2))) return false
    true
  }

  private def minusCheck(start:Cell, board: Board) : Boolean = {
    if (yxMinusOneCheck(start) && cellEmptyCheck(board.cells.cell(start.y-1, start.x-1))) return false
    if (yxMinusPlusOneCheck(start) && cellEmptyCheck(board.cells.cell(start.y-1, start.x+1))) return false
    if (yxMinusTwoCheck(start) && cellEmptyCheck(board.cells.cell(start.y-2, start.x-2))) return false
    if (yxMinusPlusTwoCheck(start) && cellEmptyCheck(board.cells.cell(start.y-2, start.x+2))) return false
    true
  }

  private def yxPlusOneCheck(s:Cell) : Boolean = s.y + 1 >= 0 && s.y + 1 <= 7 && s.x + 1 >= 0 && s.x + 1 <= 7

  private def yxPlusMinusOneCheck(s:Cell) : Boolean = s.y + 1 >= 0 && s.y + 1 <= 7 && s.x - 1 >= 0 && s.x - 1 <= 7

  private def yxMinusOneCheck(s:Cell) : Boolean = s.y - 1 >= 0 && s.y - 1 <= 7 && s.x - 1 >= 0 && s.x - 1 <= 7

  private def yxMinusPlusOneCheck(s:Cell) : Boolean = s.y - 1 >= 0 && s.y - 1 <= 7 && s.x + 1 >= 0 && s.x + 1 <= 7

  private def yxPlusTwoCheck(s:Cell) : Boolean = s.y + 2 >= 0 && s.y + 2 <= 7 && s.x + 2 >= 0 && s.x + 2 <= 7

  private def yxPlusMinusTwoCheck(s:Cell) : Boolean = s.y + 2 >= 0 && s.y + 2 <= 7 && s.x - 2 >= 0 && s.x - 2 <= 7

  private def yxMinusTwoCheck(s:Cell) : Boolean = s.y - 2 >= 0 && s.y - 2 <= 7 && s.x - 2 >= 0 && s.x - 2 <= 7

  private def yxMinusPlusTwoCheck(s:Cell) : Boolean = s.y - 2 >= 0 && s.y - 2 <= 7 && s.x + 2 >= 0 && s.x + 2 <= 7

  override def toString: String = {
    var sb  = new StringBuilder()
    sb.append("\t\tx: 0\t\t\tx: 1\t\t\tx: 2\t\t\tx: 3\t\t\tx: 4\t\t\tx: 5\t\t\tx: 6\t\t\tx: 7 \n\n")
    sb.append("\t\t(Cell, Piece)\t(Cell, Piece)\t(Cell, Piece)\t(Cell, Piece)\t(Cell, Piece)\t(Cell, Piece)\t(Cell, Piece)\t(Cell, Piece)\n\n")
    sb.append("y: 7\t")
    for (y <- 7 to (0,-1);
         x <- 0 until 8) {
      if (cell(y,x).piece.isDefined) sb.append(cell(y,x).color,cell(y,x).piece.get.color).append("\t")
      else sb.append(cell(y,x).color,cell(y,x).piece).append("\t")
      if (x == 7 && y>0) sb.append("\ny: " + (y-1) + "\t")
    }
    if (winnerColor.isDefined) sb.append("\n\nWinner: " + winnerColor.get)
    sb.toString()
  }

  //test purposes only; updating game for comparison in tests
  def updateGame(cell: Cell, lmc:Color.Value, piece: Option[Piece] = None, index: Option[Int] = None, winner:Option[Color.Value] = None) : Game = {
    var piecesWhite : Vector[Piece] = pw
    var piecesBlack : Vector[Piece] = pb
    if (piece.isDefined && index.isDefined) {
      if (piece.get.color == Color.white) piecesWhite = pw.updated(index.get, piece.get)
      else piecesBlack = pb.updated(index.get, piece.get)
    }
    val updatedBoard = board.copy(board.cells.replaceCell(cell.y, cell.x, cell))
    Game(updatedBoard, piecesBlack, piecesWhite, lmc, winner)
  }
}