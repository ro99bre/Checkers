package de.htwg.se.checkers.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PieceSpec extends AnyWordSpec with Matchers{

  "A Piece" when {
    "black" should {
      val blackPiece = Piece(Color.black, Queen.isQueen, Kicked.notKicked)
      "have color black" in {
        blackPiece.color should be(Color.black)
      }
      "be queen" in {
        blackPiece.queen should be(Queen.isQueen)
      }
      "be kicked" in {
        blackPiece.kicked should be(Kicked.notKicked)
      }
    }
  }
}
