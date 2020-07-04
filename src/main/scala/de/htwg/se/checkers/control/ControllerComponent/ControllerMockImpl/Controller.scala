package de.htwg.se.checkers.control.ControllerComponent.ControllerMockImpl

import de.htwg.se.checkers.control.ControllerComponent.ControllerTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Color, Game}
import de.htwg.se.checkers.model.GameComponent.GameTrait

class Controller(var game:Game) extends ControllerTrait {

  def createGame():Unit = {}

  def move(sx:Int, sy:Int, dx:Int, dy:Int): Unit = {}

  def undo() : Unit = {}

  def redo() : Unit = {}

  override def save(): Unit = {}

  override def load(): Unit = {}

  override def getGame(): GameTrait = new Game()

  def gameToString:String = ""
}
