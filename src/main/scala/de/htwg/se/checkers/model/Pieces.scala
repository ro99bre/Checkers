package de.htwg.se.checkers.model

case class Pieces(pieces: Vector[Piece]) {
  //creates a vector of 12 game pieces of the given color
  def this(color: Color.Value) = this(Vector.fill[Piece](12)(Piece(color, Queen.notQueen, Kicked.notKicked)))
}