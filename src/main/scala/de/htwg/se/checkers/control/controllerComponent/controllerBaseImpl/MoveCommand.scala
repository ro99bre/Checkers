package de.htwg.se.checkers.control.controllerComponent.controllerBaseImpl

import de.htwg.se.checkers.util.Command

class MoveCommand(sx:Int, sy:Int, dx:Int, dy:Int, controller:Controller) extends Command {

  override def doStep() : Unit = controller.game = controller.game.movePiece(controller.game.cell(sy,sx), controller.game.cell(dy,dx))

  override def undoStep() : Unit = controller.game = controller.game.undoMove(controller.game.cell(dy,dx), controller.game.cell(sy,sx))

  override def redoStep() : Unit = controller.game = controller.game.movePiece(controller.game.cell(sy,sx), controller.game.cell(dy,dx))
}
