package de.htwg.se.checkers.model.FileIOComponent.fileIoXmlImpl

import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Board, Game}
import de.htwg.se.checkers.model.GameComponent.GameTrait
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FileIOSpec extends AnyWordSpec with Matchers{
  "A FileIO system" should {
    val fileIO = new FileIO
    val game : GameTrait = new Game()
    val board : Board = game.getBoard()
    val testFile = scala.xml.XML.loadFile("test.xml")

    "be able to save a game to a file" in {
      fileIO.save(game, "game.xml")
      scala.xml.XML.loadFile("game.xml") should be(testFile)
    }
    "be able to load a game from a file" in {
      fileIO.load("game.xml").getBoard() should be(board)
    }
  }
}
