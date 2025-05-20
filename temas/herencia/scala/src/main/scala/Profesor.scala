class Profesor(nombre: String, edad: Int, val departamento: String)
  extends Persona(nombre, edad) with Academico with Evaluador {
  
  def saludar(): String = 
    s"Hola, soy el profesor $nombre del departamento de $departamento."
}

