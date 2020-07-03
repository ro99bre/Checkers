package de.htwg.se.checkers.model.FileIOComponent.FileIoJsonImpl

import java.io.{File, PrintWriter}

import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.{CellTrait, GameTrait}
import com.google.inject.{Guice, Inject}
import de.htwg.se.checkers.CheckersModule
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json.{JsValue, Json, Writes}

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

  override def save(game: GameTrait): Unit = {
    val pw = new PrintWriter(new File("game.json"))
    //pw.write(Json.prettyPrint(gameToJson(game)))
    pw.close()
  }

  implicit val cellWrites = new Writes[CellTrait] {
    def writes(cell: CellTrait) = Json.obj(
      "y" -> cell.y,
      "x" -> cell.x,
      "cellColor" -> cell.color.toString//,//???
      //"piece" -> cell.piece
    )
  }
}
