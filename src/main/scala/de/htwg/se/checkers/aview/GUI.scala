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

  //initialize()
  drawBoard()

  //@TODO: turn board?, following buttons/pages
  //Initial Page Buttons:
  //Play
  //Rules
  //About
  //Exit

  //Button on every page (left side)
  //Return/Back button

  //Buttons/Texts on Game-Page
  //Winner: No Winner yet
  //Next Player:
  //kickedPiecesCounter
  //New Round/Game button
  //Exit Button

  def initialize(): Unit = {
    stage.scene = new Scene {
      val pane1 = new BorderPane {
        val title1: Node = new Text {
          text = "Checkers"
          style = "-fx-font-size: 38pt"
        }
        top = new BorderPane {
          prefHeight = 200
          center = title1
        }
        val playButton = new Button {
          text = "Play"
          val playButtonStyle = "-fx-font-size: 25pt" +
            "-fx-background-radius: 5em;" +
            "-fx-padding:5;" +
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;"
          style <== when(hover) choose playButtonStyle + "-fx-border-color: black;" otherwise playButtonStyle
          prefHeight = 85
          prefWidth = 200
          onAction = _ => drawBoard()
        }
        center = new VBox {
          alignment = Pos.Center
          children = playButton
        }
      }
      root = pane1
    }
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

  def drawBoard(): Unit = {
    val recHW = (stage.getHeight-50)/8
    stage.scene = new Scene {
      val boardPane = new GridPane()
      for (yr <- 0 until 8;//(yr <- 7 to (0,-1);
           xr <- 0 until 8) {

        var square = new StackPane()
        square.setPrefSize(recHW,recHW)

        var squareButton = new Button {
          var squareButtonStyle = "-fx-background-color: transparent;"
          prefHeight = recHW
          prefWidth = recHW
          onAction = _ => click(xr,yr)
          if (coordinatesSet && sx == xr && sy == yr) squareButtonStyle += "-fx-border-color: blue;" + "-fx-border-width: 4"
          style <== when(hover) choose "-fx-background-color: blue;" otherwise squareButtonStyle
        }

        var cell : Rectangle = new Rectangle {
          width = recHW
          height = recHW
          x = xr
          y = yr
          if (controller.game.board.cells.cell(yr,xr).color == Color.white) fill = White
          else fill = Black//Red
          }

        if (controller.game.board.cells.cell(yr,xr).piece.isDefined) {
          (controller.game.board.cells.cell(yr,xr).piece.get.color, controller.game.board.cells.cell(yr,xr).piece.get.queen) match {
            case (Color.black, Queen.notQueen) => square.getChildren.addAll(cell, createBlackPiece(), squareButton)
            case (Color.white, Queen.notQueen) => square.getChildren.addAll(cell, createWhitePiece(), squareButton)
            case (Color.black, Queen.isQueen) => square.getChildren.addAll(cell, createBlackPiece(), createQueen(), squareButton)
            case (Color.white, Queen.isQueen) => square.getChildren.addAll(cell, createWhitePiece(), createQueen(), squareButton)
            case (_,_) => square.getChildren.addAll(cell, squareButton)
          }
        } else square.getChildren.addAll(cell, squareButton)

        boardPane.add(square,xr,yr)
        boardPane.alignment = Center
      }
      root = boardPane
    }
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
    CheckersTextUI.main(Array[String]("exit"))
  }

  override def update(): Unit = drawBoard()
}
