//Farb-Typ-Objekt
object Colour extends Enumeration {
  val black = Value("Black")
  val white = Value("White")
}
Colour.black
Colour.white

//Queen-Typ-Objekt
object Queen extends Enumeration {
  val isQueen = Value("Is Queen")
  val notQueen = Value("Not Queen")
}
Queen.isQueen
Queen.notQueen

//Kicked-Typ-Objekt
object Kicked extends Enumeration {
  val isKicked = Value("Is Kicked")
  val notKicked = Value("Not Kicked")
}
Kicked.isKicked
Kicked.notKicked

//Realisierung der Spielsteine, Jeder Spieler hat ein Array von Spielsteinen
case class Piece(clr: Colour.Value,isQueen: Queen.Value, isKicked: Kicked.Value)
//Initialer Zustand einer Feldzelle
val Piece1 = new Piece(Colour.black, Queen.notQueen, Kicked.notKicked)

case class PiecesPlayer1(tile: Array[Piece])
case class PiecesPlayer2(tile: Array[Piece])
//Initialisierung über Schleife??

//Einzelnes Quadrat des Spielfeldes
/*
* Wie aktualisiert man die Position des Spielsteines?
* Wie Stellt man sicher, dass ein Stein immer nur auf einem Feld steht?
* */


case class Square(clr: Colour.Value, pce: Piece)
/*Funktion um die Farbe abfragen zu können?
* Abfragen ob Spielstein daruf steht?
* Wie Spielstein verschieben?
* */

case class FieldLines(line: Vector[Square])
case class FieldRows(Row: Vector[FieldLines])

//val field1 = new FieldLines(8)
/*
* Wie neues Spielfeld aufbauen?
* */

//--Alt--
//Realisierung des Spielfeldes => Besser Vektor verwenden, Als Matrix bauen
//case class Field(squares: Array[Square])
//val field = Field(Array.ofDim[Square](1))


