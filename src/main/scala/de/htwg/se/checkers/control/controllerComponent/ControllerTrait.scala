package de.htwg.se.checkers.control.controllerComponent

import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Color
import de.htwg.se.checkers.util.Observable

trait ControllerTrait extends Observable {
  def createGame():Unit

  def move(sx:Int, sy:Int, dx:Int, dy:Int): Unit

  def undo(): Unit

  def redo(): Unit

  def gameToString: String

  def getLastMoveColor() : Color.Value

  def getWinnerColor() : Option[Color.Value]
}
