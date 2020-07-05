package de.htwg.se.checkers.model.FileIOComponent.fileIoXmlImpl

import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.checkers.CheckersModule
import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Board, Color, Game, Kicked, Piece, Pieces, Queen}
import de.htwg.se.checkers.model.GameComponent.GameTrait

import scala.xml._

class FileIO extends FileIOTrait{

  override def load(): GameTrait = {
    val injector = Guice.createInjector(new CheckersModule)
    var game : GameTrait = injector.instance[GameTrait]
    var board : Board = game.getBoard()
    var pb : Vector[Piece] = game.getPB()
    var pw : Vector[Piece] = game.getPW()
    val file = scala.xml.XML.loadFile("game.xml")

    val cellNodes = (file \\ "game" \\ "board" \\ "cell")
    for (cell <- cellNodes) {
      var color : Color.Value = Color.white
      val y : Int = (cell \ "@y").text.toInt
      val x : Int = (cell \ "@x").text.toInt
      if ((cell \ "@color").text == "black") {color = Color.black}
      if ((cell \\ "piece" \ "@color").text == "None") {
        board = board.setCell(y,x,color,None,None,None)
      } else {
        var pieceColor:Color.Value = Color.white
        var queen:Queen.Value = Queen.notQueen
        var kicked:Kicked.Value = Kicked.notKicked
        if ((cell \\ "piece" \ "@color").text == "black") {pieceColor=Color.black}
        if ((cell \\ "piece" \ "@queen").text == "isQueen") {queen=Queen.isQueen}
        if ((cell \\ "piece" \ "@kicked").text == "isKicked") {kicked=Kicked.isKicked}
        board = board.setCell(y,x,color,Some(pieceColor),Some(queen),Some(kicked))
      }
    }

    val pieceNodesBlack = (file \\ "game" \\ "pb" \\ "piece")
    for (piece <- pieceNodesBlack) {
      val index : Int = (piece \\ "@index").text.toInt
      val color : Color.Value = Color.black
      var queen : Queen.Value = Queen.notQueen
      var kicked : Kicked.Value = Kicked.notKicked
      if ((piece \\ "@queen").text == "isQueen") {queen=Queen.isQueen}
      if ((piece \\ "@kicked").text == "isKicked") {kicked=Kicked.isKicked}
      pb = game.setPiece(index,pb,color,queen,kicked)
    }

    val pieceNodesWhite = (file \\ "game" \\ "pw" \\ "piece")
    for (piece <- pieceNodesWhite) {
      val index : Int = (piece \\ "@index").text.toInt
      val color : Color.Value = Color.white
      var queen:Queen.Value = Queen.notQueen
      var kicked:Kicked.Value = Kicked.notKicked
      if ((piece \\ "@queen").text == "isQueen") {queen=Queen.isQueen}
      if ((piece \\ "@kicked").text == "isKicked") {kicked=Kicked.isKicked}
      pw = game.setPiece(index,pw,color,queen,kicked)
    }

    var lmc : Color.Value = Color.white
    var winnerColor : Option[Color.Value] = None
    if((file \\ "game" \\ "lmc" \ "@color").text == "black") {lmc = Color.black}
    if ((file \\ "game" \\ "winnerColor" \ "@color").text == "black") {winnerColor = Some(Color.black)}
    else if ((file \\ "game" \\ "winnerColor" \ "@color").text == "white") {winnerColor = Some(Color.white)}

    game = Game(board,pb,pw,lmc,winnerColor)
    game
  }

  override def save(game: GameTrait): Unit = saveString(game)

  def saveXML(game: GameTrait): Unit = {
    scala.xml.XML.save("game.xml", gameToXML(game))
  }

  def saveString(game: GameTrait) : Unit = {
    import java.io._
    val pw = new PrintWriter(new File("game.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameToXML(game))
    pw.write(xml)
    pw.close()
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
      <lmc color={game.getLastMoveColor().toString}></lmc>
      <winnerColor color={game.getWinnerColor().toString}></winnerColor>
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
      <piece color={game.cell(col,row).piece.toString}></piece>
    } else {
      <piece color={game.cell(col,row).piece.get.color.toString} queen={game.cell(col,row).piece.get.queen.toString} kicked={game.cell(col,row).piece.get.kicked.toString}></piece>
    }
  }

  def piecesToXML(pieces:Vector[Piece], index:Int): Elem = {
    <piece index={index.toString} color={pieces(index).color.toString} queen={pieces(index).queen.toString} kicked={pieces(index).kicked.toString}></piece>
  }
}
