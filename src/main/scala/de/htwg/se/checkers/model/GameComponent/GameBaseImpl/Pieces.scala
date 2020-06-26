package de.htwg.se.checkers.model.GameComponent.GameBaseImpl

import de.htwg.se.checkers.model.GameComponent.GameBaseImpl

case class Pieces(pieces: Vector[Piece]) {
  def this(color: Color.Value) = this(Vector.fill[Piece](12)(GameBaseImpl.Piece(color, Queen.notQueen, Kicked.notKicked)))
}
