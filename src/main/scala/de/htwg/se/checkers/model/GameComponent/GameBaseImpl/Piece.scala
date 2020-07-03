package de.htwg.se.checkers.model.GameComponent.GameBaseImpl

case class Piece(color:Color.Value, queen:Queen.Value, kicked:Kicked.Value)

object Piece {
  import play.api.libs.json._
  implicit val pieceWrites = Json.writes[Piece]
  implicit  val pieceReads = Json.reads[Piece]
}
