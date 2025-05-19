// src/main/scala/EstudianteTrait.scala
trait EstudianteTrait {
  val universidad: String
  def saludarComoEstudiante(nombre: String, edad: Int): String =
    s"Hola, soy $nombre, tengo $edad años y estudio en $universidad."
}

