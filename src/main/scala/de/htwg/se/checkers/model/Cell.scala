package de.htwg.se.checkers.model

case class Cell(y:Int, x:Int, color:Color.Value, piece:Option[Piece] = None)
