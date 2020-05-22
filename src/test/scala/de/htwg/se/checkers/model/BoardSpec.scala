package de.htwg.se.checkers.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec


class BoardSpec extends AnyWordSpec with Matchers {

  //@TODO insert right creation call for Board
  val testBoard: Board = new Board()
  //val testBoardString: String = testBoard.toString()

  /**
   * Test 01: Check if initial state of a new created board is as it should be
   * Missing Test Criteria: Are Pieces on their correct Positions? Position enumerations correct?
   * */

  "A new created Board" should {

    val testBoardString: String = "test"

    //@TODO fix Regular Expressions
    "contain 64 Cells" in {
      testBoardString should fullyMatch regex "test"
    }

      "32 of them" should {

        "be white" in {
          testBoardString should fullyMatch regex "test"
        }
        "be black" in {
          testBoardString should fullyMatch regex "test"
        }
      }

      "8 of the white ones" should {

        "contain black pieces" in {
          testBoardString should fullyMatch regex "test"
        }

        "contain white pieces" in {
          testBoardString should fullyMatch regex "test"
        }
      }
  }

  /**
   * Test 02: Check if pieces can only be moved to white Cells
   * */

  "A piece can be moved" should {

    "to a white Cell" in {
      //@TODO How to test if new Board is valid?
    }

    "but not to a black Cell" in {
      //@TODO What happens on illegal move?
    }
  }

  /**
   * Test 03: Check if a piece can only be moved to the front-left ot front-right white Cell
   * */

  "A piece can only be moved" should {
    "to a front-left Cell" in {
      //testBoard.movePiece()
    }
    "to a front-right Cell" in {
      //testBoard.movePiece()
    }
    "but not to white Cell in after-next row" in {
      //testBoard.movePiece()
      //assertThrows()

    }
  }

  /**
   * Test 04: Check if after the moves from the tests before the board is as it should be
   * */

  "After the moves from the tests before" should {
    "the board should be" in {
      val correctBoard: String = "test"
      testBoard.toString shouldEqual correctBoard
    }
  }
}
