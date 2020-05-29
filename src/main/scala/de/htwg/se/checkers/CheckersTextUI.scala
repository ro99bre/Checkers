package de.htwg.se.checkers

import de.htwg.se.checkers.aview.TextUI

object CheckersTextUI {

  def main(args: Array[String]): Unit = {

    val tui = new TextUI()
    var input: String = ""

    println("Started Checkers in TUI Mode")
    println(tui.game.toString)

    do {
      print("Next move: ")
      input = scala.io.StdIn.readLine()

      println(tui.tuiProcessor(input))
    } while (input != "exit")
  }
}
