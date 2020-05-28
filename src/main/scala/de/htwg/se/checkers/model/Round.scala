package de.htwg.se.checkers.model

case class Round() {
  val piecesBlack = new Pieces(Color.black)
  val piecesWhite = new Pieces(Color.white)
  val board = new Board().createBoard(piecesBlack.pieces, piecesWhite.pieces)
  var game = Game(board,piecesBlack.pieces,piecesWhite.pieces,Color.white)
}
