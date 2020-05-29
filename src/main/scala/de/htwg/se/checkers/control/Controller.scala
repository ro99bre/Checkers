package de.htwg.se.checkers.control

import de.htwg.se.checkers.util.Observable
import de.htwg.se.checkers.model._

class Controller(var game:Game) extends Observable {

  def createGame():Unit = {
    game = new Game()
    notifyObservers()
  }

  def move(sx:Int, sy:Int, dx:Int, dy:Int): Unit = {
    game = game.movePiece(game.cell(sy,sx), game.cell(dy,dx))
    notifyObservers()
  }

  def gameToString:String = game.toString
}