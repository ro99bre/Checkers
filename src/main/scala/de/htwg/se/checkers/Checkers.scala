package de.htwg.se.checkers

import de.htwg.se.checkers.model._

object Checkers {
  val piecesBlack = new Pieces(Color.black)
  val piecesWhite = new Pieces(Color.white)
  val board = new Board().createBoard(piecesBlack.pieces, piecesWhite.pieces)
  val game = Game(board,piecesBlack.pieces,piecesWhite.pieces,Color.white)

  def main(args: Array[String]): Unit = {
    println("Checkers")
    println(game.toString)
  }
}
