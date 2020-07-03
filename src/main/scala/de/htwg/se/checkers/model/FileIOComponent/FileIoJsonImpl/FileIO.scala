package de.htwg.se.checkers.model.FileIOComponent.FileIoJsonImpl

import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import de.htwg.se.checkers.model.GameComponent.GameTrait
import com.google.inject.{Guice, Inject}
import de.htwg.se.checkers.CheckersModule
import net.codingwell.scalaguice.InjectorExtensions._

class FileIO extends FileIOTrait {

  override def load: GameTrait = {
    val injector = Guice.createInjector(new CheckersModule)
    var game : GameTrait = injector.instance[GameTrait]

    game
  }

  override def save(game: GameTrait): Unit = {

  }
}
