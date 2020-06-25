package de.htwg.se.checkers.model

class NormalPiece extends SocialState {
  override def changeState: SocialState = {
    return new Queen
  }
}
