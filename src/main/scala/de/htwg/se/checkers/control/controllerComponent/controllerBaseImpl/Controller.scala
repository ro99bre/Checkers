package de.htwg.se.checkers.control.controllerComponent.controllerBaseImpl

import de.htwg.se.checkers.control.controllerComponent.ControllerTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Game
import de.htwg.se.checkers.util.UndoManager

class Controller(var game:Game) extends ControllerTrait {

  private val undoManager = new UndoManager

  override def createGame():Unit = {
    game = new Game()
    notifyObservers()
  }

  override def move(sx:Int, sy:Int, dx:Int, dy:Int): Unit = {
    undoManager.doStep(new MoveCommand(sx,sy,dx,dy,this))
    notifyObservers()
  }

  override def undo() : Unit = {
    undoManager.undoStep()
    notifyObservers()
  }

  override def redo() : Unit = {
    undoManager.redoStep()
    notifyObservers()
  }

  override def gameToString:String = game.toString
}