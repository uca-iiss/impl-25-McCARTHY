object Main {
  def main(args: Array[String]): Unit = {
    val estudiante = new Estudiante("Lucía", 21, "Universidad de Cádiz")
    val profesor = new Profesor("Dr. Gómez", 45, "Informática")
    val investigador = new Investigador("Sara", 38, "Inteligencia Artificial")

    println("=== Demostración de Herencia en Scala ===\n")

    println("[Estudiante]")
    println(estudiante.saludar())
    println(estudiante.infoAcademica())

    println("\n[Profesor]")
    println(profesor.saludar())
    println(profesor.infoAcademica())
    println(profesor.evaluar())

    println("\n[Investigador]")
    println(investigador.saludar())
    println(investigador.saludar(formal = true))

    println("\n=== Fin de la demostración ===")
  }
}

