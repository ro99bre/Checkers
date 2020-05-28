package de.htwg.se.checkers.aview

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class textuiSpec extends AnyWordSpec with Matchers {
  "A TUI" when {
    "has a command" should{

      val tui = new textui
      var input = ""

      "to creata a new board" in {
        input = "new"
        tui.tuiProcessor(input) should be("Created new Board")
      }
    }
  }
}
