package de.htwg.se.checkers.model.FileIOComponent.fileIoJsonImpl

import com.google.inject.Guice
import de.htwg.se.checkers.CheckersModule
import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Board, Color, Game, Kicked, Piece, Queen}
import de.htwg.se.checkers.model.GameComponent.GameTrait
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json.{JsObject, JsValue, Json, Writes}

import scala.io.{BufferedSource, Source}

class FileIO extends FileIOTrait {

  override def load(fileName: String): GameTrait = {
    val source: BufferedSource = Source.fromFile(fileName)
    val sourceString : String = source.getLines().mkString
    val json : JsValue = Json.parse(sourceString)
    val injector = Guice.createInjector(new CheckersModule)
    var game : GameTrait = injector.instance[GameTrait]
    var board : Board = game.getBoard()
    var pb : Vector[Piece] = game.getPB()
    var pw : Vector[Piece] = game.getPW()

    for (index <- 0 until 64) {
      val cell = (json \\ "cells").head
      val x : Int = (cell \\ "x")(index).as[Int]
      val y : Int = (cell \\ "y")(index).as[Int]
      var color : Color.Value = Color.white
      if ((cell \\ "color")(index).as[String] == "black") color = Color.black
      if ((cell(index) \ "piece" \ "piececolor").as[String] == "None") {
        board = board.setCell(y,x,color,None,None,None)
      } else {
        var pieceColor:Color.Value = Color.white
        var queen:Queen.Value = Queen.notQueen
        var kicked:Kicked.Value = Kicked.notKicked
        if ((cell(index) \ "piece" \ "piececolor").as[String] == "black") pieceColor = Color.black
        if ((cell(index) \ "piece" \ "queen").as[String] == "isQueen") queen = Queen.isQueen
        if ((cell(index) \ "piece" \ "kicked").as[String] == "isKicked") kicked = Kicked.isKicked
        board = board.setCell(y,x,color,Some(pieceColor),Some(queen),Some(kicked))
      }
    }

    for (index <- 0 until 12) {
      val piece = (json \\ "pb").head
      val color : Color.Value = Color.black
      var queen : Queen.Value = Queen.notQueen
      var kicked : Kicked.Value = Kicked.notKicked
      if ((piece \\ "queen")(index).as[String] == "isQueen") queen = Queen.isQueen
      if ((piece \\ "kicked")(index).as[String] == "isKicked") kicked = Kicked.isKicked
      pb = game.setPiece(index,pb,color,queen,kicked)
    }

    for (index <- 0 until 12) {
      val piece = (json \\ "pw").head
      val color : Color.Value = Color.white
      var queen : Queen.Value = Queen.notQueen
      var kicked : Kicked.Value = Kicked.notKicked
      if ((piece \\ "queen")(index).as[String] == "isQueen") queen = Queen.isQueen
      if ((piece \\ "kicked")(index).as[String] == "isKicked") kicked = Kicked.isKicked
      pw = game.setPiece(index,pw,color,queen,kicked)
    }

    var lmc : Color.Value = Color.white
    var winnerColor : Option[Color.Value] = None
    if ((json \ "game" \ "lmc").as[String] == "black") lmc = Color.black
    if ((json \ "game" \ "winnerColor").as[String] == "black") winnerColor = Some(Color.black)
    if ((json \ "game" \ "winnerColor").as[String] == "white") winnerColor = Some(Color.white)

    source.close()
    game = Game(board,pb,pw,lmc,winnerColor)
    game
  }

  implicit val gameWrites = new Writes[GameTrait] {
    def writes(game: GameTrait): JsObject = Json.obj(
      "game" -> Json.obj(
        "board" -> Json.obj(
          "cells" -> Json.toJson(
            for {
              row <- 0 until 8
              col <- 0 until 8
            } yield {
              Json.obj(
                "y" -> col,
                "x" -> row,
                "color" -> Json.toJson(game.cell(col,row).color),
                if (game.cell(col,row).piece.isEmpty) "piece" -> Json.obj(
                  "piececolor" -> Json.toJson("None"))
                else "piece" -> Json.obj(
                  "piececolor" -> Json.toJson(game.cell(col,row).piece.get.color),
                  "queen" -> Json.toJson(game.cell(col,row).piece.get.queen),
                  "kicked" -> Json.toJson(game.cell(col,row).piece.get.kicked)
                )
              )
            }
          )
        ),
        "pb" -> Json.toJson(
          for {index <- 0 until 12} yield {
            Json.obj(
              "index" -> index,
              "piececolor" -> Json.toJson(game.getPB()(index).color),
              "queen" -> Json.toJson(game.getPB()(index).queen),
              "kicked" -> Json.toJson(game.getPB()(index).kicked)
            )
          }
        ),
        "pw" -> Json.toJson(
          for {index <- 0 until 12} yield {
            Json.obj(
              "index" -> index,
              "piececolor" -> Json.toJson(game.getPW()(index).color),
              "queen" -> Json.toJson(game.getPW()(index).queen),
              "kicked" -> Json.toJson(game.getPW()(index).kicked)
            )
          }
        ),
        "lmc" -> Json.toJson(game.getLastMoveColor()),
        if (game.getWinnerColor().isDefined) "winnerColor" -> Json.toJson(game.getWinnerColor())
        else "winnerColor" -> "None"
      )
    )
  }

  override def save(game: GameTrait, fileName: String): Unit = {
    import java.io._
    val pw = new PrintWriter(new File(fileName))
    pw.write(Json.prettyPrint(Json.toJson(game)))
    pw.close()
  }
}
