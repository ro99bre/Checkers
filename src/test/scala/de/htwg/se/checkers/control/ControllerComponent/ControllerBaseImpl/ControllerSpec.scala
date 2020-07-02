package de.htwg.se.checkers.control.ControllerComponent.ControllerBaseImpl

import de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl.Controller
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.Game
import de.htwg.se.checkers.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers{

  "A Controller" when {
    "observed by an Observer" should {
      val game = new Game()
      val controller = new Controller(game)
      val observer = new Observer {
        var updated: Boolean = false
        override def update(): Unit = updated = true
      }
      controller.add(observer)
      "notify Observer after creation of game" in {
        controller.createGame()
        observer.updated should be(true)
      }
      "handle undo/redo correctly on empty undo-stack" in {
        controller.undo()
        controller.game should be(game)
        controller.redo()
        controller.game should be(game)
      }
      "notify Observer after moving a piece" in {
        controller.move(0,2,1,3)
        observer.updated should be(true)
        controller.game.cell(2,0).piece.isDefined should be(false)
        controller.game.cell(3,1).piece.isDefined should be(true)
      }
      "notify Observer after undo of move" in {
        controller.undo()
        observer.updated should be(true)
        controller.game.cell(2,0).piece.isDefined should be(true)
        controller.game.cell(3,1).piece.isDefined should be(false)
      }
      "notify Observer after redo of move" in {
        controller.redo()
        observer.updated should be(true)
        controller.game.cell(2,0).piece.isDefined should be(false)
        controller.game.cell(3,1).piece.isDefined should be(true)
      }
    }
  }
}
