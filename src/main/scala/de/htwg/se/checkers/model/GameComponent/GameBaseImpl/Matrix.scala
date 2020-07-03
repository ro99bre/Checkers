package de.htwg.se.checkers.model.GameComponent.GameBaseImpl

case class Matrix[T](rows:Vector[Vector[T]]) {
  def this(filling:T) = this(Vector.tabulate(8, 8){(i,j) => filling})

  def cell(y:Int, x:Int): T = rows(y)(x)

  def replaceCell(y:Int, x:Int, cell:T): Matrix[T] = copy(rows.updated(y, rows(y).updated(x, cell)))
}

/*object Matrix {
  import play.api.libs.json._
  implicit val matrixWrites = Json.writes[Matrix]
  implicit val matrixReads = Json.reads[Matrix]
}*/
