package de.htwg.se.checkers

import com.google.inject.AbstractModule
import de.htwg.se.checkers.control.ControllerComponent.ControllerTrait
import de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl
import de.htwg.se.checkers.model.FileIOComponent.FileIOTrait
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl
import de.htwg.se.checkers.model.GameComponent.GameTrait
import de.htwg.se.checkers.model.FileIOComponent.FileIoJsonImpl

class CheckersModule extends AbstractModule with ScalaModule {

  override def configure() {
    bind[GameTrait].to[GameBaseImpl.Game]
    bind[ControllerTrait].to[controllerBaseImpl.Controller]
    bind[FileIOTrait].to[FileIoJsonImpl.FileIO]
  }
}
