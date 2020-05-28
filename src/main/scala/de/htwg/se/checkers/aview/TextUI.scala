package de.htwg.se.checkers.aview
import de.htwg.se.checkers.model._

class TextUI {

  var round = Round()

  /*
  val piecesBlack = new Pieces(Color.black)
  val piecesWhite = new Pieces(Color.white)
  val board = new Board().createBoard(piecesBlack.pieces, piecesWhite.pieces)
  val game = Game(board,piecesBlack.pieces,piecesWhite.pieces,Color.white)
  */

  def tuiProcessor(input: String): String ={

    val output = new StringBuilder()

    input.split(" |,").toList match {

      case "new" :: "Round" :: Nil =>
        round = new Round()
        output.append("Started new Round\n")
        output.append(round.game.toString)


      case "move" :: xPosOld :: yPosOld :: xPosNew :: yPosNew :: Nil =>

        round.game.movePiece(round.game.cell(xPosOld.toInt,yPosOld.toInt), round.game.cell(xPosNew.toInt,yPosNew.toInt))

        //TODO? Output if move not allowed / If there is a Winner

        output.append("Moved a piece\n")
        output.append(round.game.toString)

        //TODO? Alternate between players


      case "exit" :: Nil =>
        System.exit(0)

      case _ =>
        output.append("Possible Commands:\n")
        output.append("new Round:                 Starts a new Round of the game. The current scores will be lost.\n")
        output.append("move old<X,Y> new<X,Y>:    Moves the Piece from the old position to the new position specified\n")
        output.append("exit:                      Exit the Game.\n")
    }

    return output.toString()
  }
}
