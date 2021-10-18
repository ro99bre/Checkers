package de.htwg.se.checkers.model.FileIOComponent.fileIoJsonImpl

import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Game
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.{JsValue, Json}

import scala.io.{BufferedSource, Source}

class FileIOSpec extends AnyWordSpec with Matchers{
  "A FileIO system" should {
    val fileIO = new FileIO
    val testSource: BufferedSource = Source.fromFile("test.json")
    val testSourceString : String = testSource.getLines().mkString
    val testJson : JsValue = Json.parse(testSourceString)
    testSource.close()

    "be able to save a game to a file" in {
      fileIO.save(new Game(), "game.json")
      val source: BufferedSource = Source.fromFile("game.json")
      val sourceString : String = source.getLines().mkString
      val json : JsValue = Json.parse(sourceString)
      source.close()
      json should be(testJson)
    }
    "be able to load a game from a file" in {
      fileIO.load("game.json").getBoard() should be(new Game().board)
    }
  }
}
