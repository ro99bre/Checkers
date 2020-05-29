package de.htwg.se.checkers.aview
import de.htwg.se.checkers.model._

class TextUI {

  var game = new Game()

  def tuiProcessor(input: String): String ={

    val output = new StringBuilder()

    input.split(" |,").toList match {

      case "new" :: "Round" :: Nil =>
        game = new Game()
        output.append("Started new Round\n")
        output.append(game.toString)


      case "move" :: xPosOld :: yPosOld :: xPosNew :: yPosNew :: Nil =>

        if (game != game.movePiece(game.cell(yPosOld.toInt, xPosOld.toInt), game.cell(yPosNew.toInt, xPosNew.toInt)))
          output.append("valid move\n")
        else
          output.append("invalid move\n")

        game = game.movePiece(game.cell(yPosOld.toInt, xPosOld.toInt), game.cell(yPosNew.toInt, xPosNew.toInt))

        if (game.winnerColor.isDefined) output.append("Winner: " + game.winnerColor + "\n")
        else if (game.lmc == Color.white) output.append("Next Player: Black\n")
        else output.append("Next Player: White\n")

        output.append(game.toString)


      case "exit" :: Nil =>

      case _ =>
        output.append("Possible Commands:\n")
        output.append("new Round:                 Starts a new Round of the game. The current scores will be lost.\n")
        output.append("move old<X,Y> new<X,Y>:    Moves the Piece from the old position to the new position specified\n")
        output.append("exit:                      Exit the Game.\n")
    }

    output.toString()
  }
}
