package de.htwg.se.checkers.model.FileIOComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}

import com.google.inject.Guice
import de.htwg.se.checkers.CheckersModule
import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Piece
import de.htwg.se.checkers.model.GameComponent.GameTrait
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json.{JsObject, JsValue, Json, Writes}

import scala.io.{BufferedSource, Source}

class FileIO extends FileIOTrait {

  override def load(): GameTrait = {
    val source: BufferedSource = Source.fromFile("game.json")
    val sourceString : String = source.getLines().mkString
    val json : JsValue = Json.parse(sourceString)
    val injector = Guice.createInjector(new CheckersModule)
    var game : GameTrait = injector.instance[GameTrait]

    source.close()
    game
  }

  override def save(game: GameTrait): Unit = {
    val pw = new PrintWriter(new File("game.json"))
    //pw.write(Json.prettyPrint(gameToJson(game)))
    pw.close()
  }

  def gameToJson(game: GameTrait): JsObject = {
    Json.obj(
      "game" -> Json.obj(
        "board" -> Json.obj(
          "cells" -> Json.toJson(
            for {
              row <- 0 until 8;
              col <- 0 until 8
            } yield {
              Json.obj(
                "y" -> col,
                "x" -> row,
                "color" -> Json.toJson(game.cell(col,row).color),
                if (game.cell(col,row).piece.isEmpty) "piece" -> Json.toJson("None")
                else "piece" -> Json.obj(
                  "color" -> Json.toJson(game.cell(col,row).piece.get.color),
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
              "color" -> Json.toJson(game.getPB()(index).color),
              "queen" -> Json.toJson(game.getPB()(index).queen),
              "kicked" -> Json.toJson(game.getPB()(index).kicked)
            )
          }
        ),
        "pw" -> Json.toJson(
          for {index <- 0 until 12} yield {
            Json.obj(
              "index" -> index,
              "color" -> Json.toJson(game.getPW()(index).color),
              "queen" -> Json.toJson(game.getPW()(index).queen),
              "kicked" -> Json.toJson(game.getPW()(index).kicked)
            )
          }
        ),
        "lmc" -> Json.toJson(game.getLastMoveColor()),
        "winnerColor" -> Json.toJson(game.getWinnerColor())
      )
    )
  }
}
