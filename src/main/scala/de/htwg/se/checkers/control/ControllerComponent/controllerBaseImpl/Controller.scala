package de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl
import de.htwg.se.checkers.util.Command
import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.checkers.CheckersModule
import de.htwg.se.checkers.control.ControllerComponent.ControllerTrait
import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl.{Color, Game}
import de.htwg.se.checkers.model.GameComponent.GameTrait
import de.htwg.se.checkers.util.UndoManager

class Controller @Inject() (var game:GameTrait) extends ControllerTrait {

  private val undoManager = new UndoManager
  val injector = Guice.createInjector(new CheckersModule)
  val fileIo = injector.instance[FileIOTrait]

  override def createGame():Unit = {
    game = injector.instance[GameTrait]
    notifyObservers()
  }

  override def move(sx:Int, sy:Int, dx:Int, dy:Int): Unit = {
    undoManager.doStep(new MoveCommand(sx,sy,dx,dy,this))
    notifyObservers()
  }

  override def undo() : Unit = {
    undoManager.undoStep()
    notifyObservers()
  }

  override def redo() : Unit = {
    undoManager.redoStep()
    notifyObservers()
  }

  override def save(fileName: String) : Unit = {
    fileIo.save(game, fileName)
    notifyObservers()
  }

  override def load(fileName: String): Unit = {
    game = fileIo.load(fileName)
    notifyObservers()
  }

  override def getGame(): GameTrait = game

  override def gameToString:String = game.toString
}