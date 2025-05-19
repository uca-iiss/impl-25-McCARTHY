// src/main/scala/Profesor.scala
class Profesor(nombre: String, edad: Int, val departamento: String)
  extends Persona(nombre, edad) with ProfesorTrait {

  def saludar(): String = saludarComoProfesor(nombre, edad)
}

