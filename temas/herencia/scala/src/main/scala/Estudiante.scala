// src/main/scala/Estudiante.scala
class Estudiante(nombre: String, edad: Int, val universidad: String)
  extends Persona(nombre, edad) with EstudianteTrait {

  def saludar(): String = saludarComoEstudiante(nombre, edad)
}

