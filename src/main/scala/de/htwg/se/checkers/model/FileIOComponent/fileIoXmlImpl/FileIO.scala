package de.htwg.se.checkers.model.FileIOComponent.fileIoXmlImpl

import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.checkers.CheckersModule
import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Board, Color, Piece}
import de.htwg.se.checkers.model.GameComponent.GameTrait

import scala.xml._

class FileIO extends FileIOTrait{

  override def load: GameTrait = {//GameTrait or Unit??? setGame method in Controller?
    val injector = Guice.createInjector(new CheckersModule)
    var game : GameTrait = injector.instance[GameTrait]
    val file = scala.xml.XML.loadFile("game.xml")
    game
  }

  override def save(game: GameTrait): Unit = {
    scala.xml.XML.save("game.xml", gameToXML(game))
  }

  def gameToXML(game: GameTrait): Elem = {
    <game>
    {boardToXML(game)}
      <pb>
        {for {index <- 0 until 12}
        yield {piecesToXML(game.getPB(),index)}}
      </pb>
      <pw>
        {for {index <- 0 until 12}
        yield {piecesToXML(game.getPW(),index)}}
      </pw>
      <lmc>
        {game.getLastMoveColor().toString}
      </lmc>
      <winnerColor>
        {if (game.getWinnerColor().isDefined) game.getWinnerColor().toString else "None"}
      </winnerColor>
    </game>
  }

  def boardToXML(game: GameTrait): Elem = {
    <board>
      {
      for {
        row <- 0 until 8
        col <- 0 until 8
      } yield cellToXML(game, row, col)
      }
    </board>
  }

  def cellToXML(game:GameTrait, row:Int, col:Int): Elem = {
    <cell y={col.toString} x={row.toString} color={game.cell(col,row).color.toString}>
    {pieceToXML(game,row,col)}
    </cell>
  }

  def pieceToXML(game: GameTrait, row:Int, col:Int): Elem = {
    if (game.cell(col,row).piece.isEmpty) {
      <piece>
        "None"
      </piece>
    } else {
      <piece color={game.cell(col,row).piece.get.color.toString} queen={game.cell(col,row).piece.get.queen.toString} kicked={game.cell(col,row).piece.get.kicked.toString}>
      </piece>
    }
  }

  def piecesToXML(pieces:Vector[Piece], index:Int): Elem = {
    <piece index={index.toString} color={pieces(index).color.toString} queen={pieces(index).queen.toString} kicked={pieces(index).kicked.toString}>
    </piece>
  }
}
