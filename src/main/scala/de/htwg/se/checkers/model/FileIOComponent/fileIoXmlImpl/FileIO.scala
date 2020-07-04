package de.htwg.se.checkers.model.FileIOComponent.fileIoXmlImpl

import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.checkers.CheckersModule
import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Board, Color, Game, Kicked, Piece, Queen}
import de.htwg.se.checkers.model.GameComponent.GameTrait

import scala.xml._

class FileIO extends FileIOTrait{

  override def load: GameTrait = {//GameTrait or Unit??? setGame method in Controller?
    val injector = Guice.createInjector(new CheckersModule)
    var game : GameTrait = injector.instance[GameTrait]
    var board : Board = game.getBoard()
    var pb : Vector[Piece] = game.getPB()
    var pw : Vector[Piece] = game.getPW()
    val file = scala.xml.XML.loadFile("game.xml")

    val cellNodes = (file \\ "cell")
    for (cell <- cellNodes) {
      var color : Color.Value = Color.white
      val y : Int = (cell \ "@y").text.toInt
      val x : Int = (cell \ "@x").text.toInt
      if ((cell \ "@color").text == "black") {color = Color.black}
      if (cell \\ "piece" == "None") {board=game.setCell(y,x,color,None,None,None)}
      else {
        var pieceColor:Color.Value = Color.white
        var queen:Queen.Value = Queen.notQueen
        var kicked:Kicked.Value = Kicked.notKicked
        if ((cell \\ "piece" \ "@color").text == "black") {pieceColor=Color.black}
        if ((cell \\ "piece" \ "@queen").text == "isQueen") {queen=Queen.isQueen}
        if ((cell \\ "piece" \ "@kicked").text == "isKicked") {kicked=Kicked.isKicked}
        board=game.setCell(y,x,color,Some(pieceColor),Some(queen),Some(kicked))
      }
    }

    //pieces

    var lmc : Color.Value = Color.white
    var winnerColor : Option[Color.Value] = None
    if((file \\ "lmc").text == "black") {lmc = Color.black}
    if ((file \\ "winnerColor").text == "black") {winnerColor = Some(Color.black)}
    else if ((file \\ "winnerColor").text == "white") {winnerColor = Some(Color.white)}

    game = Game(board,pb,pw,lmc,winnerColor)
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
