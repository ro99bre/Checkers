package de.htwg.se.checkers.control

import de.htwg.se.checkers.util.{Observable, UndoManager}
import de.htwg.se.checkers.model._

class Controller(var game:Game) extends Observable {

  private val undoManager = new UndoManager

  def createGame():Unit = {
    game = new Game()
    notifyObservers()
  }

  def move(sx:Int, sy:Int, dx:Int, dy:Int): Unit = {
    undoManager.doStep(new MoveCommand(sx,sy,dx,dy,this))
    notifyObservers()
  }

  def undo() : Unit = {
    undoManager.undoStep()
    notifyObservers()
  }

  def redo() : Unit = {
    undoManager.redoStep()
    notifyObservers()
  }

  def gameToString:String = game.toString
}