package de.htwg.se.checkers

import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Board, Color, Game, Pieces}

object Checkers {
  val piecesBlack = new Pieces(Color.black)
  val piecesWhite = new Pieces(Color.white)
  val board : Board = new Board().createBoard(piecesBlack.pieces, piecesWhite.pieces)
  val game : Game = Game(board,piecesBlack.pieces,piecesWhite.pieces,Color.white)

  def main(args: Array[String]): Unit = {
    println("Checkers")
    println(game.toString)
  }
}
