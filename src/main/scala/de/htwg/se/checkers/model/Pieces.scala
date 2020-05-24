package de.htwg.se.checkers.model

case class Pieces(pieces: Vector[Piece]) {
  def this(color: Color.Value) = this(Vector.fill[Piece](12)(Piece(color, Queen.notQueen, Kicked.notKicked)))
}