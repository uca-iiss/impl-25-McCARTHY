package empleados

class Desarrollador(nombre: String, salarioBase: Double, experiencia: Int, val lenguaje: String)
  extends Tecnico(nombre, salarioBase, experiencia) {

  override def calcularSalario(): Double = {
    val lenguajeBonus = if (lenguaje == "Scala") 0.2 else 0.1
    salarioBase * (1 + lenguajeBonus) + bonoExperiencia()
  }

  override def descripcion(): String =
    super.descripcion() + s", Desarrollador en $lenguaje"
}
