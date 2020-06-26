package de.htwg.se.checkers.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CellSpec extends AnyWordSpec with Matchers{

  "A Cell" when {
    "white" should {
      val whiteCell = Cell(7,0,Color.white)
      "have color white" in {
        whiteCell.color should be(Color.white)
      }
      "have no piece" in {
        whiteCell.piece.isDefined should be(false)
      }
      "have y" in {
        whiteCell.y should be(7)
      }
      "have x" in {
        whiteCell.x should be(0)
      }
    }
    "black" should {
      val blackCell = Cell(0,0,Color.black,Some(Piece(Color.black, new NormalPiece, Kicked.notKicked)))
      "have piece" in {
        blackCell.piece.isDefined should be(true)
      }
    }
  }
}
