package de.htwg.se.checkers.control.controllerComponent.ControllerMockImpl

import de.htwg.se.checkers.control.controllerComponent.ControllerTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Game

class Controller(var game:Game) extends ControllerTrait {

  def createGame():Unit = {}

  def move(sx:Int, sy:Int, dx:Int, dy:Int): Unit = {}

  def undo() : Unit = {}

  def redo() : Unit = {}

  def gameToString:String = ""
}
