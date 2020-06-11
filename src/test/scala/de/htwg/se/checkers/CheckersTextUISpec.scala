package de.htwg.se.checkers

import de.htwg.se.checkers.aview.TextUI
import de.htwg.se.checkers.control.Controller
import de.htwg.se.checkers.model.Game
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CheckersTextUISpec extends AnyWordSpec with Matchers {

  "The CheckersTextUI main class" should {

    "accept text input from command line without loop" in {
      CheckersTextUI.main(Array[String]("move 0,2 1,3"))
      CheckersTextUI.main(Array[String]("h"))
    }
  }
}
