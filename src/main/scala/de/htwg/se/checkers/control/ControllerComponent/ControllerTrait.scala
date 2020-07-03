package de.htwg.se.checkers.control.ControllerComponent

import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Color
import de.htwg.se.checkers.model.GameComponent.GameTrait
import de.htwg.se.checkers.util.Observable

trait ControllerTrait extends Observable {
  def createGame():Unit

  def move(sx:Int, sy:Int, dx:Int, dy:Int): Unit

  def undo(): Unit

  def redo(): Unit

  def gameToString: String

  def save() : Unit

  def getGame(): GameTrait
}
