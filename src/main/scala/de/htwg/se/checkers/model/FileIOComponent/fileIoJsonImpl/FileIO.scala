package de.htwg.se.checkers.model.FileIOComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}

import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.{CellTrait, GameTrait}
import com.google.inject.{Guice, Inject}
import de.htwg.se.checkers.CheckersModule
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Piece
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json.{JsNumber, JsObject, JsValue, Json, Writes}

import scala.io.{BufferedSource, Source}

class FileIO extends FileIOTrait {

  override def load: GameTrait = {
    val source: BufferedSource = Source.fromFile("game.json")
    val sourceString : String = source.getLines().mkString
    val json : JsValue = Json.parse(sourceString)
    val injector = Guice.createInjector(new CheckersModule)
    var game : GameTrait = injector.instance[GameTrait]
    //loop over file, set game/board/pieces/colors...
    source.close()
    game
  }

  /*implicit val cellWrites = new Writes[CellTrait] {
    def writes(cell: CellTrait): JsObject = Json.obj(
      "y" -> cell.y,
      "x" -> cell.x,
      "cellColor" -> cell.color.toString//,//???
      //if (cell.piece.isDefined) "piece" -> cell.piece
      //"piece" -> cell.piece
      //implicit val for piece
    )
  }

  implicit val pieceWrites = new Writes[Piece] {
    def writes(piece: Piece): JsObject = Json.obj(
      "color" -> piece.color,
      "queen" -> piece.queen,
      "kicked" -> piece.kicked
    )
  }*/

/*
  def gameToJson(game: GameTrait): JsObject = {
    Json.obj(
      "game" -> Json.obj(
        "board" -> Json.toJson(
          for {
            y <- 0 until 8
            x <- 0 until 8
          } yield {
            Json.obj(
              "y" -> y,
              "x" -> x,
              "cell" -> Json.toJson(game.cell(y,x))//implicit val
            )
          }
        ),
        "pb" -> Json.toJson(//obj???
          for (index <- 0 until 12 )
            yield {
              Json.obj(
                "index" -> index,
                "piece" -> Json.toJson(game.getPB(index))//implicit val??
              )
            }
        ),
        "pw" -> Json.toJson(//obj???
          for (index <- 0 until 12 )
            yield {
              Json.obj(
                "index" -> index,
                "piece" -> Json.toJson(game.getPW(index))
              )
            }
        ) //,
        //"lmc" -> Json.toJson(game.getLastMoveColor()),//obj???
        //"winnercolor"-> Json.toJson(game.getWinnerColor())//obj???
      )
    )
  }*/

  override def save(game: GameTrait): Unit = {
    val pw = new PrintWriter(new File("game.json"))
    //pw.write(Json.prettyPrint(gameToJson(game)))
    pw.close()
  }
}
