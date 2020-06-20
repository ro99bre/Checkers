package de.htwg.se.checkers.aview

import de.htwg.se.checkers.CheckersTextUI
import de.htwg.se.checkers.control.Controller
import de.htwg.se.checkers.model.{Color, Queen}
import de.htwg.se.checkers.util.Observer
import javafx.scene.shape.{Circle, RectangleBuilder}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Pos
import scalafx.geometry.Pos.Center
import scalafx.scene.control.Button
import scalafx.scene.image.ImageView
import scalafx.scene.{Node, Scene}
import scalafx.scene.layout.{BorderPane, GridPane, StackPane, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.{FontWeight, Text}

import scala.io.Source

class GUI(controller: Controller) extends JFXApp with Observer {

  controller.add(this)

  val w : Double = 1280//1920
  val h : Double = 720//1080
  var sx : Int = 8
  var sy : Int = 8
  var coordinatesSet : Boolean = false

  stage = new PrimaryStage {
    title = "Checkers"
    width = w
    height = h
  }

  initialize()
  //drawBoard()

  //@TODO: turn board?, following buttons/pages
  //Initial Page Buttons:
  //Play
  //Rules
  //About
  //Exit

  //Button on every page (left side)
  //Return/Back button

  def initialize(): Unit = {
    stage.scene = new Scene {
      val pane1 : BorderPane = new BorderPane {
        style = "-fx-background-color: White"
        val title1: Node = new Text {
          text = "Checkers"
          style = "-fx-font-size: 38pt"
        }
        top = new BorderPane {
          prefHeight = 200
          center = title1
        }
        val playButton : Button = new Button("Play") {
          val buttonStyle : String = "-fx-font-size: " + stage.getHeight/29 + "pt;" +
            "-fx-padding:5;" +
            "-fx-background-color: white;"
          style <== when(hover) choose buttonStyle + "-fx-border-color: black;" otherwise buttonStyle
          prefHeight = 85
          prefWidth = (stage.getWidth/4)-10
          onAction = _ => drawBoard()
        }
        center = new VBox {
          alignment = Pos.Center
          children = List(playButton, rulesButton(85), createExitButton(85))
        }
      }
      root = pane1
    }
  }

  def drawBoard(): Unit = {
    val recHW = (stage.getHeight-50)/8
    stage.scene = new Scene {
      val boardPane = new GridPane()
      for (yr <- 0 until 8;
           xr <- 0 until 8) {

        var square = new StackPane()
        square.setPrefSize(recHW,recHW)

        if (controller.game.board.cells.cell(yr,xr).piece.isDefined) {
          (controller.game.board.cells.cell(yr,xr).piece.get.color, controller.game.board.cells.cell(yr,xr).piece.get.queen) match {
            case (Color.black, Queen.notQueen) => square.getChildren.addAll(createCell(recHW,xr,yr), createBlackPiece(), createSquareButton(recHW,xr,yr))
            case (Color.white, Queen.notQueen) => square.getChildren.addAll(createCell(recHW,xr,yr), createWhitePiece(), createSquareButton(recHW,xr,yr))
            case (Color.black, Queen.isQueen) => square.getChildren.addAll(createCell(recHW,xr,yr), createBlackPiece(), createQueen(), createSquareButton(recHW,xr,yr))
            case (Color.white, Queen.isQueen) => square.getChildren.addAll(createCell(recHW,xr,yr), createWhitePiece(), createQueen(), createSquareButton(recHW,xr,yr))
          }
        } else square.getChildren.addAll(createCell(recHW,xr,yr), createSquareButton(recHW,xr,yr))

        boardPane.add(square,xr,7 - yr)
      }
      boardPane.style = "-fx-background-color: White"
      val playerWinnerPane = new StackPane()
      playerWinnerPane.getChildren.add(textBGRectangle(recHW))
      val kickedBlackPane = new StackPane()
      kickedBlackPane.getChildren.addAll(textBGRectangle(recHW),kickedPiecesBlack())
      val kickedWhitePane = new StackPane()
      kickedWhitePane.getChildren.addAll(textBGRectangle(recHW),kickedPiecesWhite())
      if (controller.game.winnerColor.isEmpty) playerWinnerPane.getChildren.add(nextPlayer())
      else playerWinnerPane.getChildren.add(nextPlayer())
      boardPane.add(playerWinnerPane,8,0)
      boardPane.add(kickedBlackPane,8,1)
      boardPane.add(kickedWhitePane,8,2)
      boardPane.add(undoButton(recHW),8,3)
      boardPane.add(redoButton(recHW),8,4)
      boardPane.add(newRoundButton(recHW),8,5)
      boardPane.add(backButton(recHW),8,6)
      boardPane.add(createExitButton(recHW),8,7)
      boardPane.alignment = Center
      root = boardPane
    }
  }

  def showRules(): Unit = {
    val hw = stage.getHeight-50
    stage.scene = new Scene{
      val rect : Rectangle = new Rectangle{
        width = hw-(2*(hw/8))
        height = hw-(2*(hw/8))
        fill = White
      }
      val stackPaneRules = new StackPane()
      val gridPane = new GridPane()
      //stackPaneRules.getChildren.addAll(rect, new Text(Source.fromFile("checkersRules.txt").mkString) {style = "-fx-font-size: " + stage.getHeight/50 + "pt;"})
      stackPaneRules.getChildren.addAll(rect,rulesFromTXT())
      gridPane.add(stackPaneRules,0,0)
      gridPane.add(backButton(hw/8),1,1)
      gridPane.add(createExitButton(hw/8),1,2)
      gridPane.alignment = Center
      gridPane.style = "-fx-background-color: White"
      root = gridPane
    }
  }

  def rulesFromTXT(): Node = {
    val rules: Node = new Text {
      text = Source.fromFile("checkersRules.txt").mkString
      style = "-fx-font-size: " + stage.getHeight/50 + "pt;" //+ "-fx-padding:50px;"
    }
    //close source???
    rules
  }

  def createSquareButton(recHW:Double, xr:Integer, yr:Integer): Button = {
    val squareButton = new Button {
      var squareButtonStyle = "-fx-background-color: transparent;"
      prefHeight = recHW
      prefWidth = recHW
      onAction = _ => click(xr,yr)
      if (coordinatesSet && sx == xr && sy == yr) squareButtonStyle += "-fx-border-color: blue;" + "-fx-border-width: 4"
      style <== when(hover) choose "-fx-background-color: blue;" otherwise squareButtonStyle
    }
    squareButton
  }

  def createCell(recHW:Double, xr:Integer, yr:Integer): Rectangle = {
    var cell : Rectangle = new Rectangle {
      width = recHW
      height = recHW
      x = xr.toDouble
      y = yr.toDouble
      if (controller.game.board.cells.cell(yr,xr).color == Color.white) fill = White
      else fill = Black
    }
    cell
  }

  def createBlackPiece(): Circle = {
    val blackPiece = new Circle {
      setRadius((stage.getHeight-100)/16)
      fillProperty().set(Red)
    }
    blackPiece
  }

  def createWhitePiece(): Circle = {
    val whitePiece = new Circle {
      setRadius((stage.getHeight-100)/16)
      fillProperty().set(White)
    }
    whitePiece
  }

  def createQueen(): Node = {
    val queen: Node = new Text {
      text = "K"
      style = "-fx-font-size: " + stage.getHeight/29 + "pt"
    }
    queen
  }

  def nextPlayer(): Node = {
    val nextPlayer: Node = new Text {//text in rectangle stackpane -> board doesn't move
      controller.game.lmc match {
        case Color.black => text = "Next Player: White"
        case Color.white => text = "Next Player: Red"
      }
      style = "-fx-font-size: " + stage.getHeight/29 + "pt;"
    }
    nextPlayer
  }

  def textBGRectangle(recHW:Double) : Rectangle = {
    val rect : Rectangle = new Rectangle{
      width = (stage.getWidth/4)-10
      height = recHW
      fill = White
    }
    rect
  }

  def winner(): Node = {
    val winner: Node = new Text {
      controller.game.winnerColor.get match {
        case Color.black => text = "Winner: Red"
        case Color.white => text = "Winner: White"
      }
      style = "-fx-font-size: " + stage.getHeight/29 + "pt;"
    }
    winner
  }

  def kickedPiecesBlack() : Node = {
    val kickedBlack: Node = new Text {
      text = "Dead Black Pieces: " + controller.game.countKickedPieces()._1
      style = "-fx-font-size: " + stage.getHeight/29 + "pt;"
    }
    kickedBlack
  }

  def kickedPiecesWhite() : Node = {
    val kickedWhite: Node = new Text {
      text = "Dead White Pieces: " + controller.game.countKickedPieces()._2
      style = "-fx-font-size: " + stage.getHeight/29 + "pt;"
    }
    kickedWhite
  }

  def undoButton(recHW:Double): Button = {
    val undoButton = new Button("Undo Move") {
      var undoButtonStyle = "-fx-background-color: white;" +
        "-fx-font-size: " + stage.getHeight/29 + "pt;" +
        "-fx-padding:5;"
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => controller.undo()
      style <== when(hover) choose undoButtonStyle + "-fx-border-color: black;" otherwise undoButtonStyle
    }
    undoButton
  }

  def redoButton(recHW:Double): Button = {
    val redoButton = new Button("Redo Move") {
      var redoButtonStyle = "-fx-background-color: white;" +
        "-fx-font-size: " + stage.getHeight/29 + "pt;" +
        "-fx-padding:5;"
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => controller.redo()
      style <== when(hover) choose redoButtonStyle + "-fx-border-color: black;" otherwise redoButtonStyle
    }
    redoButton
  }

  def newRoundButton(recHW:Double): Button = {
    val newRoundButton = new Button("New Round") {
      var newRoundButtonStyle = "-fx-background-color: white;" +
        "-fx-font-size: " + stage.getHeight/29 + "pt;" +
        "-fx-padding:5;"
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => controller.createGame()
      style <== when(hover) choose newRoundButtonStyle + "-fx-border-color: black;" otherwise newRoundButtonStyle
    }
    newRoundButton
  }

  def backButton(recHW:Double): Button = {
    val backButton = new Button("Go Back") {
      var backButtonStyle = "-fx-background-color: white;" +
        "-fx-font-size: " + stage.getHeight/29 + "pt;" +
        "-fx-padding:5;"
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => initialize()
      style <== when(hover) choose backButtonStyle + "-fx-border-color: black;" otherwise backButtonStyle
    }
    backButton
  }

  def rulesButton(recHW:Double): Button = {
    val rulesButton = new Button("Show Rules") {
      var rulesButtonStyle = "-fx-background-color: white;" +
        "-fx-font-size: " + stage.getHeight/29 + "pt;" +
        "-fx-padding:5;"
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => showRules()
      style <== when(hover) choose rulesButtonStyle + "-fx-border-color: black;" otherwise rulesButtonStyle
    }
    rulesButton
  }

  def createExitButton(recHW:Double): Button = {
    val exitButton = new Button("Exit") {
      var exitButtonStyle = "-fx-background-color: white;" +
        "-fx-font-size: " + stage.getHeight/29 + "pt;" +
        "-fx-padding:5;"
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => exit()
      style <== when(hover) choose exitButtonStyle + "-fx-border-color: black;" otherwise exitButtonStyle
    }
    exitButton
  }

  def click(x:Integer, y:Integer): Unit = {
    if (!coordinatesSet) {
      sx = x
      sy = y
      coordinatesSet = true
      drawBoard()
    } else {
      controller.move(sx,sy,x,y)
      coordinatesSet = false
      drawBoard()
    }
  }

  def exit(): Unit = {
    System.exit(0)
  }

  override def update(): Unit = drawBoard()
}
