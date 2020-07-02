package de.htwg.se.checkers.aview

import de.htwg.se.checkers.control.controllerComponent.ControllerTrait
import de.htwg.se.checkers.control.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Color, Queen}
import de.htwg.se.checkers.util.Observer
import javafx.scene.shape.Circle
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Pos
import scalafx.geometry.Pos.{Center, TopCenter}
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, GridPane, StackPane, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text
import scalafx.scene.{Node, Scene}

import scala.io.{BufferedSource, Source}

class GUI(controller: ControllerTrait) extends JFXApp with Observer {

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
          children = List(playButton, rulesButton(85), aboutButton(85), createExitButton(85))
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

        //if (controller.game.board.cells.cell(yr,xr).piece.isDefined) {
        if (controller.getGame().getBoard().cells.cell(yr,xr).piece.isDefined) {
          (controller.getGame().getBoard().cells.cell(yr,xr).piece.get.color, controller.getGame().getBoard().cells.cell(yr,xr).piece.get.queen) match {
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
      if (controller.getGame().getWinnerColor().isEmpty) playerWinnerPane.getChildren.add(nextPlayer())
      else playerWinnerPane.getChildren.add(winner())
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
      val stackPaneRules = new StackPane()
      val gridPane = new GridPane()
      stackPaneRules.getChildren.addAll(rulesAboutBGRectangle(hw),textFromTXT("checkersRules.txt"))
      stackPaneRules.alignment = TopCenter
      gridPane.add(stackPaneRules,0,0)
      gridPane.add(backButton(hw/8),1,1)
      gridPane.add(createExitButton(hw/8),1,2)
      gridPane.alignment = Center
      gridPane.style = "-fx-background-color: White"
      root = gridPane
    }
  }

  def about(): Unit = {
    val hw = stage.getHeight-50
    stage.scene = new Scene{
      val stackPaneAbout = new StackPane()
      val gridPane = new GridPane()
      stackPaneAbout.getChildren.addAll(rulesAboutBGRectangle(hw),textFromTXT("about.txt"))
      stackPaneAbout.alignment = TopCenter
      gridPane.add(stackPaneAbout,0,0)
      gridPane.add(backButton(hw/8),1,1)
      gridPane.add(createExitButton(hw/8),1,2)
      gridPane.alignment = Center
      gridPane.style = "-fx-background-color: White"
      root = gridPane
    }
  }

  def textFromTXT(sourceStr:String): Node = {
    val rules: Node = new Text {
      val source : BufferedSource = Source.fromFile(sourceStr)
      text = source.mkString
      source.close
      style = "-fx-font-size: " + stage.getHeight/55 + "pt;"
    }
    rules
  }

  def rulesAboutBGRectangle(hw:Double): Rectangle = {
    val rect : Rectangle = new Rectangle{
      width = hw
      height = hw-(2*(hw/8))
      fill = White
    }
    rect
  }

  def createSquareButton(recHW:Double, xr:Integer, yr:Integer): Button = {
    val squareButton : Button = new Button {
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
      if (controller.getGame().getBoard().cells.cell(yr,xr).color == Color.white) fill = White
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
    val nextPlayer: Node = new Text {
      controller.getLastMoveColor() match {
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
      controller.getWinnerColor() match {
        case Color.black => text = "Winner: Red"
        case Color.white => text = "Winner: White"
      }
      style = "-fx-font-size: " + stage.getHeight/29 + "pt;"
    }
    winner
  }

  def kickedPiecesBlack() : Node = {
    val kickedBlack: Node = new Text {
      text = "Dead Red Pieces: " + controller.getGame().countKickedPieces()._1
      style = "-fx-font-size: " + stage.getHeight/29 + "pt;"
    }
    kickedBlack
  }

  def kickedPiecesWhite() : Node = {
    val kickedWhite: Node = new Text {
      text = "Dead White Pieces: " + controller.getGame().countKickedPieces()._2
      style = "-fx-font-size: " + stage.getHeight/29 + "pt;"
    }
    kickedWhite
  }

  def undoButton(recHW:Double): Button = {
    val undoButton = new Button("Undo Move") {
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => controller.undo()
      style <== when(hover) choose standardButtonStyle() + "-fx-border-color: black;" otherwise standardButtonStyle()
    }
    undoButton
  }

  def redoButton(recHW:Double): Button = {
    val redoButton = new Button("Redo Move") {
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => controller.redo()
      style <== when(hover) choose standardButtonStyle() + "-fx-border-color: black;" otherwise standardButtonStyle()
    }
    redoButton
  }

  def newRoundButton(recHW:Double): Button = {
    val newRoundButton = new Button("New Round") {
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => controller.createGame()
      style <== when(hover) choose standardButtonStyle() + "-fx-border-color: black;" otherwise standardButtonStyle()
    }
    newRoundButton
  }

  def backButton(recHW:Double): Button = {
    val backButton = new Button("Go Back") {
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => initialize()
      style <== when(hover) choose standardButtonStyle() + "-fx-border-color: black;" otherwise standardButtonStyle()
    }
    backButton
  }

  def rulesButton(recHW:Double): Button = {
    val rulesButton = new Button("Show Rules") {
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => showRules()
      style <== when(hover) choose standardButtonStyle() + "-fx-border-color: black;" otherwise standardButtonStyle()
    }
    rulesButton
  }

  def aboutButton(recHW:Double): Button = {
    val aboutButton = new Button("About") {
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => about()
      style <== when(hover) choose standardButtonStyle() + "-fx-border-color: black;" otherwise standardButtonStyle()
    }
    aboutButton
  }

  def createExitButton(recHW:Double): Button = {
    val exitButton = new Button("Exit") {
      prefHeight = recHW
      prefWidth = (stage.getWidth/4)-10
      onAction = _ => exit()
      style <== when(hover) choose standardButtonStyle() + "-fx-border-color: black;" otherwise standardButtonStyle()
    }
    exitButton
  }

  def standardButtonStyle(): String = {
    val buttonStyle = "-fx-background-color: white;" +
      "-fx-font-size: " + stage.getHeight/29 + "pt;" +
      "-fx-padding:5;"
    buttonStyle
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
