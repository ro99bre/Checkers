package de.htwg.se.checkers.util

trait Command {
  def doStep() : Unit
  def undoStep() : Unit
  def redoStep() : Unit
}
