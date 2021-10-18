package de.htwg.se.checkers.model.FileIOComponent

import de.htwg.se.checkers.model.GameComponent.GameTrait

trait FileIOTrait {
  def load(fileName: String): GameTrait
  def save(game: GameTrait, fileName: String): Unit
}
