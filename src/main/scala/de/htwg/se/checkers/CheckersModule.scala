package de.htwg.se.checkers

import com.google.inject.AbstractModule
import de.htwg.se.checkers.control.ControllerComponent.ControllerTrait
import de.htwg.se.checkers.control.ControllerComponent.controllerBaseImpl
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.checkers.model.GameComponent.GameBaseImpl
import de.htwg.se.checkers.model.GameComponent.GameTrait

class CheckersModule extends AbstractModule with ScalaModule {

  override def configure() {
    bind[GameTrait].to[GameBaseImpl.Game]
    bind[ControllerTrait].to[controllerBaseImpl.Controller]
  }
}
