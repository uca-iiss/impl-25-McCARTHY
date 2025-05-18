abstract class Persona(val nombre: String, val edad: Int) {
  def saludar(): String
}

class Estudiante(nombre: String, edad: Int, val universidad: String)
  extends Persona(nombre, edad) {

  override def saludar(): String = {
    s"Hola, soy $nombre, tengo $edad años y estudio en $universidad."
  }
}

object Main extends App {
  val estudiante = new Estudiante("Javier", 22, "UCA")
  println(estudiante.saludar())
}

