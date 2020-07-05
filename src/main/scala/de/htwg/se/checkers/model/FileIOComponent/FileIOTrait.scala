package de.htwg.se.checkers.model.FileIOComponent

import de.htwg.se.checkers.model.GameComponent.GameTrait

trait FileIOTrait {
  def load(): GameTrait
  def save(game: GameTrait): Unit
}
