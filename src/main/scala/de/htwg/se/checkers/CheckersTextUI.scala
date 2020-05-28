package de.htwg.se.checkers

import de.htwg.se.checkers.aview.textui
import de.htwg.se.checkers.model._

object CheckersTextUI {

  val piecesBlack = new Pieces(Color.black)
  val piecesWhite = new Pieces(Color.white)
  val board = new Board().createBoard(piecesBlack.pieces, piecesWhite.pieces)
  var game = new Game(board,piecesBlack.pieces,piecesWhite.pieces,Color.white)

  def main(args: Array[String]): Unit = {

    val tui = new textui()
    var input: String = ""

    println("Started Checkers in TUI Mode")

    while (1 == 1) {
      print("Next move: ")
      input = scala.io.StdIn.readLine()

      println(tui.tuiProcessor(input))
    }

  }

  def exit(): Unit = {
    //Exit
    System.exit(0)
  }

  def newGame(): Unit = {
    //create new Game
    val piecesBlack = new Pieces(Color.black)
    val piecesWhite = new Pieces(Color.white)
    val board = new Board().createBoard(piecesBlack.pieces, piecesWhite.pieces)
    game = new Game(board,piecesBlack.pieces,piecesWhite.pieces,Color.white)
  }
}
