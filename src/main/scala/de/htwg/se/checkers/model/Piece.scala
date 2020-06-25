package de.htwg.se.checkers.model

case class Piece(color:Color.Value, socialState:SocialState, kicked:Kicked.Value) {
  def makeQueen: SocialState = {
    return socialState.changeState
  }
}
