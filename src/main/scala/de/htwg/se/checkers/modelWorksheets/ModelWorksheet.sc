//Typ-Objekt Color
object Color extends Enumeration{
  val white, black = Value
}
//Testaufrufe
Color.black
Color.white

//Typ-Objekt Queen
object Queen extends Enumeration{
  val isQueen, notQueen = Value
}
//Testaufrufe
Queen.isQueen
Queen.notQueen

//Typ-Objekt Kicked
object Kicked extends Enumeration{
  val isKicked, notKicked = Value
}
//Testaufrufe
Kicked.isKicked
Kicked.notKicked


//class Piece, zum Erstellen der Spielsteine; class instead of case class, because queen and kicked have to be mutable
class Piece(c:Color.Value, q:Queen.Value, k:Kicked.Value) {
  val clr :Color.Value = c
  var queen :Queen.Value = q
  var kicked :Kicked.Value = k
}

//def kickPiece killt einen Spielstein
def kickPiece(piece: Piece): Unit = {
  piece.kicked = Kicked.isKicked
}

//def crown macht einen Spielstein zur Dame
def crown(piece: Piece): Unit = {
  piece.queen = Queen.isQueen
}


//Pieces erstellt einen Array aus Spielsteinen
case class Pieces(pieces: Array[Piece])

val amountOfPieces = 12 //avoids magic number

//je Spieler/Farbe ein Array mit 12 Spielsteinen
val piecesWhite = Pieces(Array.fill[Piece](amountOfPieces)(new Piece(Color.white, Queen.notQueen, Kicked.notKicked)))
val piecesBlack = Pieces(Array.fill[Piece](amountOfPieces)(new Piece(Color.black, Queen.notQueen, Kicked.notKicked)))

//Testaufrufe
piecesWhite.pieces(2).clr
piecesWhite.pieces(11).queen
piecesWhite.pieces(9).kicked
piecesBlack.pieces(0).clr
piecesBlack.pieces(3).queen
piecesBlack.pieces(7).kicked
kickPiece(piecesBlack.pieces(7))
piecesBlack.pieces(7).kicked
piecesBlack.pieces(5).queen
crown(piecesBlack.pieces(5))
piecesBlack.pieces(5).queen


//erstellt ein Quadrat auf dem Spielfeld mit Koordinaten, Farbe und optionalem Spielstein
class Cell(xc:Int, yc:Int, c:Color.Value, p:Option[Piece] = None) {
  val x :Int = xc
  val y :Int = yc
  val color :Color.Value = c
  var piece :Option[Piece] = p
}

//bewegt einen Spielstein
def movePiece(start:Cell, destination:Cell): Unit = {
  //check rules
  destination.piece = start.piece
  start.piece = None
}

//Testaufrufe
var testCell1 = new Cell(0,0,Color.black)
var testCell2 = new Cell(0,1,Color.white,Some(piecesBlack.pieces(1))) //var weil piece mutable
testCell1.color
testCell1.piece
testCell2.color
testCell2.piece
testCell2.piece.get.kicked
crown(testCell2.piece.get)
testCell2.piece.get.queen
movePiece(testCell2, testCell1)
testCell2.piece
testCell1.piece
//testCell2.piece.get.kicked -> NoSuchElementException @TODO: None abfangen NoSuchElementException
testCell1.piece.get.kicked


case class Board() {}

//creates a board of 8x8
val board = Array.ofDim[Cell](8,8)
for (i <- 0 until 8) {
  for (j <- 0 until 8) {
    //x&y gerade || x&y ungerade
    if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) {
      board(i)(j) = new Cell(i,j,Color.black)
    } else {
      board(i)(j) = new Cell(i,j,Color.white)
    }
  }
}

//tests accessing the cells on the board
board(0)(0).color
board(0)(1).color
board(0)(1).x
board(0)(1).y
board(0)(2).color
board(1)(0).color
board(2)(0).color
board(3)(0).color

//puts game pieces in starting position
def piecesOnBoard(board: Array[Array[Cell]]): Unit = {
  for (i <- 0 until 8) {
    for (j <- 0 until 8) {
      for (p <- 0 until 12) {
        if (board(i)(j).color == Color.black && i <= 2 ) {
          board(i)(j).piece = Some(piecesBlack.pieces(p))
        }
      }
      for (p <- 0 until 12) {
        if (board(i)(j).color == Color.black && i >= 5 ) {
          board(i)(j).piece = Some(piecesWhite.pieces(p))
        }
      }
    }
  }
}

piecesOnBoard(board)

//prints out the board incl coordinates, color and piece
for (i <- 7 to (0, -1)) {
  for (j <- 0 until 8) {
    if (board(i)(j).piece.isDefined) {
      print(i,j,board(i)(j).color,board(i)(j).piece.get.clr)
    } else {
      print(i,j,board(i)(j).color,board(i)(j).piece)
    }

  }
  print("\n")
}

board(2)(0).piece
board(3)(1).piece
movePiece(board(2)(0), board(3)(1))
board(2)(0).piece
board(3)(1).piece