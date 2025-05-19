// src/main/scala/Main.scala
object Main extends App {
  val estudiante = new Estudiante("Lucía", 22, "UCA")
  val profesor = new Profesor("Carlos", 50, "Informática")

  println(estudiante.saludar())
  println(profesor.saludar())
}

