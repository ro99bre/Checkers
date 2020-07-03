package de.htwg.se.checkers.model.GameComponent.GameBaseImpl

import de.htwg.se.checkers.model.GameComponent.CellTrait

case class Cell(y:Int, x:Int, color:Color.Value, piece:Option[Piece] = None) extends CellTrait

