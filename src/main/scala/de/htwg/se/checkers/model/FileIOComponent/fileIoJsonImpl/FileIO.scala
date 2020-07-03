package de.htwg.se.checkers.model.FileIOComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}

import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.checkers.CheckersModule
import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Game
import de.htwg.se.checkers.model.GameComponent.{CellTrait, GameTrait}
import play.api.libs.json.{JsValue, Json, Writes}

import scala.io.{BufferedSource, Source}

class FileIO extends FileIOTrait{

  override def load: GameTrait = {
    val source: BufferedSource = Source.fromFile("game.json")
    val sourceString : String = source.getLines().mkString
    val json : JsValue = Json.parse(sourceString)
    val injector = Guice.createInjector(new CheckersModule)
    var game : GameTrait = injector.instance[GameTrait]

    //loop
    /*for (index <- 0 until 64) {
      val y = (json \\ "y")(index).as[Int]
      val x = (json \\ "x")(index).as[Int]
    }

    game.cell()//set Cell, if piece.isDefined setPiece
    if (game.getWinnerColor().isDefined)
      //setWinnerColor, setLMC, setBoard/Cells, setPieces
      //loop over cells, pieces
    */
    source.close()
    game
  }

  override def save(game: GameTrait): Unit = {
    val pw = new PrintWriter(new File("game.json"))
    pw.write(Json.prettyPrint(gameToJson(game)))
    pw.close()
  }
  implicit val cellWrites = new Writes[CellTrait] {
    def writes(cell: CellTrait) = Json.obj(
      "y" -> cell.y,
      "x" -> cell.x,
      "cellColor" -> cell.color.toString,//???
      "piece" -> cell.piece
    )
  }

  def gameToJson(game: GameTrait) = {
    Json.obj(
      "game" -> Json.obj(
        "board" -> Json.toJson(
          for {
            i <- 0 until 8;
            j <- 0 until 8
          } yield {
            Json.obj(
              "y" -> game.cell(i,j).y,
              "x" -> game.cell(i,j).x,
              /*"cellColor" -> game.cell(i,j).color,
              if (game.cell(i,j).piece.isDefined) "piece" -> Json.toJson(
                //piececolor
                //kicked
                //queen
              )
              else "piece" -> game.cell(i,j).piece,
              //color
              //piece*/
              //nur y,x um später feld aufbauen zu können?
              "cell" -> Json.toJson(game.cell(i,j))//implicit val
            )
          }
        ),
        "pb" -> Json.toJson(//obj???
          for (index <- 0 until 12 )
            yield {
              Json.obj(
                "index" -> index,
                "piece" -> Json.toJson(game.getPB())//implicit val??
              )
            }
        ),
        "pw" -> Json.toJson(//obj???
          for (index <- 0 until 12 )
            yield {
              Json.obj(
                "index" -> index,
                "piece" -> Json.toJson(game.getPW())
              )
            }
        )//,
        //"lmc" -> Json.toJson(game.getLastMoveColor()),//obj???
        //"winnercolor"-> Json.toJson(game.getWinnerColor())//obj???
      )
    )
  }

}
