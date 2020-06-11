package de.htwg.se.checkers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CheckersTextUISpec extends AnyWordSpec with Matchers {

  "The CheckersTextUI main class" should {
    "accept text input from command line without loop" in {
      CheckersTextUI.main(Array[String]("v"))
    }
  }
}
