package de.htwg.se.checkers.model

class Queen extends SocialState {
  override def changeState(): SocialState = {
    return new Queen
  }
}
