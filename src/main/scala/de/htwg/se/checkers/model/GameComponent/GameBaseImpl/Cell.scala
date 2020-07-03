package de.htwg.se.checkers.model.GameComponent.GameBaseImpl

import de.htwg.se.checkers.model.GameComponent.CellTrait

case class Cell(y:Int, x:Int, color:Color.Value, piece:Option[Piece] = None) extends CellTrait

object Cell {
  import play.api.libs.json._
  implicit val cellWrites = Json.writes[Cell]
  implicit  val cellReads = Json.reads[Cell]
}
