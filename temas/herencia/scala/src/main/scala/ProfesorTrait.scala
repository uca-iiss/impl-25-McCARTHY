// src/main/scala/ProfesorTrait.scala
trait ProfesorTrait {
  val departamento: String
  def saludarComoProfesor(nombre: String, edad: Int): String =
    s"Hola, soy el profesor $nombre, tengo $edad años y enseño en $departamento."
}

