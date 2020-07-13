package de.htwg.se.checkers.model.GameComponent.GameBaseImpl

import de.htwg.se.checkers.model.GameComponent.{CellTrait, GameBaseImpl, GameTrait}

/**
 *
 * @param board, game board of 8x8 (pieces can only be on black cells)
 * @param pb, vector of black game pieces
 * @param pw, vector of white game pieces
 * @param lmc, color of last move (for checking rules)
 * @param winnerColor, optional color of winner (if winner exists)
 */
case class Game(board: Board, pb: Vector[Piece], pw: Vector[Piece], lmc: Color.Value, winnerColor : Option[Color.Value] = None) extends GameTrait{

  def this() = this(new Board().createBoard(new Pieces(Color.black).pieces, new Pieces(Color.white).pieces),new Pieces(Color.black).pieces, new Pieces(Color.white).pieces, Color.white)

  override def movePiece(s:CellTrait, d:CellTrait): Game = {
    if (winnerColor.isDefined) return this
    checkRules(s, d) match {
      case (Some(_), Some(_), _) => Game(this.updatePiece(s,d),checkRules(s, d)._1.get,
        checkRules(s, d)._2.get, checkRules(s, d)._3, hasWon(s, d))
      case (_,_,_) => this
    }
  }

  override def undoMove(s:CellTrait, d:CellTrait): Game = {
    var opponentColor = Color.white
    s.piece.get.color match {
      case Color.black => opponentColor = Color.white
      case Color.white => opponentColor = Color.black
    }
    var pbTemp = pb
    var pwTemp = pw
    var temp: Board = board.copy(board.cells.replaceCell(d.y,d.x,Cell(d.y, d.x, d.color, s.piece)))
    temp = temp.copy(temp.cells.replaceCell(s.y, s.x, Cell(s.y, s.x, s.color)))
    if (deKickPieceCheck(s,d,temp,opponentColor).isDefined) { //undoes kicking of piece
      temp = deKickPieceCheck(s,d,temp,opponentColor).get._1
      pbTemp = deKickPieceCheck(s,d,temp,opponentColor).get._2
      pwTemp = deKickPieceCheck(s,d,temp,opponentColor).get._3
    }
    if (deQueenDestinationCheck(s,d,temp).isDefined) { //undoes crowning of piece
      temp = deQueenDestinationCheck(s,d,temp).get._1
      pbTemp = deQueenDestinationCheck(s,d,temp).get._2
      pwTemp = deQueenDestinationCheck(s,d,temp).get._3
    }
    Game(temp,pbTemp,pwTemp,opponentColor)
  }

  //updates cells after piece has been moved (and kicked), returns new board
  override def updatePiece(s:CellTrait, d:CellTrait): Board = {//s=start d=destination
    var piece : Piece = s.piece.get
    piece.color match {
      case Color.black => piece = checkRules(s, d)._1.get(pb.indexOf(piece))
      case Color.white => piece = checkRules(s, d)._2.get(pw.indexOf(piece))
    }
    var temp: Board = board.copy(board.cells.replaceCell(d.y,d.x,Cell(d.y, d.x, d.color, Some(piece))))
    temp = temp.copy(temp.cells.replaceCell(s.y, s.x, Cell(s.y, s.x, s.color)))
    if(middleCellCalc(s,d).isDefined) {
      val middleCell : CellTrait = middleCellCalc(s,d).get
      temp = temp.copy(temp.cells.replaceCell(middleCell.y, middleCell.x, Cell(middleCell.y, middleCell.x, middleCell.color)))
    }
    temp
  }

  //returns black, then white pieces and color of move after rules have been checked; returns None if move is invalid
  override def checkRules(s:CellTrait, d:CellTrait): (Option[Vector[Piece]], Option[Vector[Piece]], Color.Value) = {
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

  override def pieceColorCheck(start:CellTrait): Boolean = start.piece.get.color != lmc

  override def cellColorCheck(destination:CellTrait): Boolean = destination.color == Color.black

  override def cellEmptyCheck(destination:CellTrait): Boolean = destination.piece.isEmpty

  override def opponentPieces(start:CellTrait): Vector[Piece] = {
    var color:Color.Value = lmc
    if (start.piece.isDefined) color = start.piece.get.color
    color match {
      case Color.black => pw
      case Color.white => pb
    }
  }

  override def moveQueenRules(s:CellTrait, d:CellTrait): Option[Vector[Piece]] = {
    if ((d.x + 1 == s.x || d.x - 1 == s.x) && (d.y - 1 == s.y || d.y + 1 == s.y)) return Some(opponentPieces(s))
    else if ((d.x + 2 == s.x || d.x - 2 == s.x) && (d.y - 2 == s.y || d.y + 2 == s.y)) return kickPieceCheck(s, d)
    None
  }

  //checks if a piece has arrived at the other side of the board and crowns it, returns updated vector of pieces
  override def queenDestinationCheck(start:CellTrait, destination:CellTrait): Vector[Piece] = {
    (start.piece.get.color, destination.y) match {
      case (Color.black, 7) => crown(pb, pb.indexOf(start.piece.get))
      case (Color.white, 0) => crown(pw, pw.indexOf(start.piece.get))
      case (Color.black,_) => pb
      case(Color.white,_) => pw
    }
  }

  override def deQueenDestinationCheck(s:CellTrait, d:CellTrait, tempboard:Board) : Option[(Board,Vector[Piece],Vector[Piece])] = {
    var pbTemp = pb
    var pwTemp = pw
    var index: Int = 0
    var temp = tempboard
    (s.piece.get.color, s.y) match {
      case (Color.black, 7) => index = pb.indexOf(s.piece.get); pbTemp=deCrown(pbTemp,index)
        temp = temp.copy(temp.cells.replaceCell(d.y,d.x,Cell(d.y, d.x, d.color, Some(pbTemp(index)))))
      case (Color.white, 0) => index = pw.indexOf(s.piece.get); pwTemp=deCrown(pwTemp,index)
        temp = temp.copy(temp.cells.replaceCell(d.y,d.x,Cell(d.y, d.x, d.color, Some(pwTemp(index)))))
      case _ => return None
    }
    Some(temp,pbTemp,pwTemp)
  }

  override def crown(pieces: Vector[Piece], index: Int): Vector[Piece] = pieces.updated(index, GameBaseImpl.Piece(pieces(index).color,
    Queen.isQueen, pieces(index).kicked))

  override def deCrown(pieces: Vector[Piece], index: Int): Vector[Piece] = pieces.updated(index, GameBaseImpl.Piece(pieces(index).color,
    Queen.notQueen, pieces(index).kicked))

  override def moveBlackRules(s:CellTrait, d:CellTrait): Option[Vector[Piece]] = {
    if (d.y - 1 == s.y && (d.x - 1 == s.x || d.x + 1 == s.x)) return Some(opponentPieces(s))
    else if (d.y - 2 == s.y && (d.x - 2 == s.x || d.x + 2 == s.x)) return kickPieceCheck(s, d)
    None
  }

  override def moveWhiteRules(s:CellTrait, d:CellTrait): Option[Vector[Piece]] = {
    if (d.y + 1 == s.y && (d.x - 1 == s.x || d.x + 1 == s.x)) return Some(opponentPieces(s))
    else if (d.y + 2 == s.y && (d.x - 2 == s.x || d.x + 2 == s.x)) return kickPieceCheck(s, d)
    None
  }

  override def kickPieceCheck(s:CellTrait, d:CellTrait): Option[Vector[Piece]] = {
    val middlePiece:CellTrait = middleCellCalc(s, d).get
    if (middleCellCheck(s, middlePiece)) return Some(updatePiecesKicked(middlePiece.piece.get))
    None
  }

  override def updatePiecesKicked(middlePiece: Piece): Vector[Piece] = {
    middlePiece.color match {
      case Color.black => kickPiece(pb, pb.indexOf(middlePiece))
      case Color.white => kickPiece(pw, pw.indexOf(middlePiece))
    }
  }

  override def deKickPieceCheck(s:CellTrait, d:CellTrait, tempboard:Board, opponentColor:Color.Value) : Option[(Board,Vector[Piece],Vector[Piece])] = {
    var pbTemp = pb
    var pwTemp = pw
    var index: Int = 0
    var temp = tempboard
    if(middleCellCalc(s,d).isDefined) {
      val middleCell:CellTrait= middleCellCalc(s,d).get
      for (i <- 11 to (0,-1)) {
        opponentColor match {
          case Color.black => if(pb(i).kicked == Kicked.isKicked) index=i;pbTemp=deKickPiece(pb,index)
            temp = temp.copy(temp.cells.replaceCell(middleCell.y, middleCell.x, Cell(middleCell.y, middleCell.x, middleCell.color, Some(pbTemp(index)))))
          case Color.white => if(pw(i).kicked == Kicked.isKicked) index=i;pwTemp=deKickPiece(pw,index)
            temp = temp.copy(temp.cells.replaceCell(middleCell.y, middleCell.x, Cell(middleCell.y, middleCell.x, middleCell.color, Some(pwTemp(index)))))
        }
        return Some(temp,pbTemp,pwTemp)
      }
    }
    None
  }

  override def kickPiece(pieces: Vector[Piece], index: Int): Vector[Piece] = pieces.updated(index, Piece(pieces(index).color, pieces(index).queen, Kicked.isKicked))

  override def deKickPiece(pieces: Vector[Piece], index: Int): Vector[Piece] = pieces.updated(index, Piece(pieces(index).color, pieces(index).queen, Kicked.notKicked))

  //calculates a piece to be killed in case of jumping over it
  override def middleCellCalc(s:CellTrait, d:CellTrait): Option[CellTrait] = {
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

  override def middleCellCheck(start:CellTrait, middleCell:CellTrait): Boolean = middleCell.piece.isDefined && (middleCell.piece.get.color != start.piece.get.color)

  override def countKickedPieces(): (Int, Int) = (countKickedPieces(pb), countKickedPieces(pw))

  override def countKickedPieces(pieces: Vector[Piece]): Int = {
    var counter :Int = 0
    for (i <- pieces.indices) {
      if (pieces(i).kicked == Kicked.isKicked) counter += 1
    }
    counter
  }

  override def cell(y:Int, x:Int): CellTrait = board.cells.cell(y,x)

  override def hasWon(start:CellTrait, destination:CellTrait) : Option[Color.Value] = {
    if(countKickedPieces(checkRules(start,destination)._1.get) == 12) Some(Color.white)
    else if(countKickedPieces(checkRules(start,destination)._2.get) == 12) Some(Color.black)
    else isBlocked(updatePiece(start,destination))
  }

  override def isBlocked(board: Board) : Option[Color.Value] = {//returns winner color
    if (isBlackBlocked(board)) Some(Color.white)
    else if (isWhiteBlocked(board)) Some(Color.black)
    else None
  }

  override def isBlackBlocked(board: Board): Boolean = {
    var bool : Boolean = false
    for (y <- 0 until 8;
         x <- 0 until 8) {
      var start : CellTrait = board.cells.cell(y,x)
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

  override def isWhiteBlocked(board: Board): Boolean = {
    var bool : Boolean = false
    for (y <- 0 until 8;
         x <- 0 until 8) {
      var start : CellTrait = board.cells.cell(y,x)
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

  //checks if cells around start cell are empty for possible moves
  override def plusCheck(start:CellTrait, board: Board): Boolean = {
    if (yxPlusOneCheck(start) && cellEmptyCheck(board.cells.cell(start.y+1, start.x+1))) return false
    if (yxPlusMinusOneCheck(start) && cellEmptyCheck(board.cells.cell(start.y+1, start.x-1))) return false
    if (yxPlusTwoCheck(start) && cellEmptyCheck(board.cells.cell(start.y+2, start.x+2))) return false
    if (yxPlusMinusTwoCheck(start) && cellEmptyCheck(board.cells.cell(start.y+2, start.x-2))) return false
    true
  }

  override def minusCheck(start:CellTrait, board: Board) : Boolean = {
    if (yxMinusOneCheck(start) && cellEmptyCheck(board.cells.cell(start.y-1, start.x-1))) return false
    if (yxMinusPlusOneCheck(start) && cellEmptyCheck(board.cells.cell(start.y-1, start.x+1))) return false
    if (yxMinusTwoCheck(start) && cellEmptyCheck(board.cells.cell(start.y-2, start.x-2))) return false
    if (yxMinusPlusTwoCheck(start) && cellEmptyCheck(board.cells.cell(start.y-2, start.x+2))) return false
    true
  }

  //tries different coordinates, which are in reach from the start cell; returns true if cell with those coordinates exists
  override def yxPlusOneCheck(s:CellTrait) : Boolean = s.y + 1 >= 0 && s.y + 1 <= 7 && s.x + 1 >= 0 && s.x + 1 <= 7

  override def yxPlusMinusOneCheck(s:CellTrait) : Boolean = s.y + 1 >= 0 && s.y + 1 <= 7 && s.x - 1 >= 0 && s.x - 1 <= 7

  override def yxMinusOneCheck(s:CellTrait) : Boolean = s.y - 1 >= 0 && s.y - 1 <= 7 && s.x - 1 >= 0 && s.x - 1 <= 7

  override def yxMinusPlusOneCheck(s:CellTrait) : Boolean = s.y - 1 >= 0 && s.y - 1 <= 7 && s.x + 1 >= 0 && s.x + 1 <= 7

  override def yxPlusTwoCheck(s:CellTrait) : Boolean = s.y + 2 >= 0 && s.y + 2 <= 7 && s.x + 2 >= 0 && s.x + 2 <= 7

  override def yxPlusMinusTwoCheck(s:CellTrait) : Boolean = s.y + 2 >= 0 && s.y + 2 <= 7 && s.x - 2 >= 0 && s.x - 2 <= 7

  override def yxMinusTwoCheck(s:CellTrait) : Boolean = s.y - 2 >= 0 && s.y - 2 <= 7 && s.x - 2 >= 0 && s.x - 2 <= 7

  override def yxMinusPlusTwoCheck(s:CellTrait) : Boolean = s.y - 2 >= 0 && s.y - 2 <= 7 && s.x + 2 >= 0 && s.x + 2 <= 7

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
  override def updateGame(cell: CellTrait, lmc:Color.Value, piece: Option[Piece] = None, index: Option[Int] = None, winner:Option[Color.Value] = None) : Game = {
    var piecesWhite : Vector[Piece] = pw
    var piecesBlack : Vector[Piece] = pb
    if (piece.isDefined && index.isDefined) {
      if (piece.get.color == Color.white) piecesWhite = pw.updated(index.get, piece.get)
      else piecesBlack = pb.updated(index.get, piece.get)
    }
    val updatedBoard = board.copy(board.cells.replaceCell(cell.y, cell.x, cell))
    Game(updatedBoard, piecesBlack, piecesWhite, lmc, winner)
  }

  override def getLastMoveColor(): Color.Value = lmc

  override def getWinnerColor(): Option[Color.Value] = winnerColor

  override def getBoard(): Board = board

  override def getPB(): Vector[Piece] = pb

  override def getPW(): Vector[Piece] = pw

  override def setPiece(index:Int, pieces:Vector[Piece], color: Color.Value, queen: Queen.Value, kicked: Kicked.Value): Vector[Piece] = pieces.updated(index,Piece(color,queen,kicked))
}
