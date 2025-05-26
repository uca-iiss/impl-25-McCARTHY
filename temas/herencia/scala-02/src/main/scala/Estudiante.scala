class Estudiante(nombre: String, edad: Int, val universidad: String)
  extends Persona(nombre, edad) with Academico {
  
  def departamento: String = "Estudios Generales"

  def saludar(): String = 
    s"Hola, soy $nombre, tengo $edad años y estudio en $universidad."
}

