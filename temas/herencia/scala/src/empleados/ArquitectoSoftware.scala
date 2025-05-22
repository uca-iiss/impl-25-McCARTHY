package empleados

class ArquitectoSoftware(nombre: String, salarioBase: Double, experiencia: Int, lenguaje: String, val proyectos: Int)
  extends Desarrollador(nombre, salarioBase, experiencia, lenguaje) {

  override def calcularSalario(): Double = {
    super.calcularSalario() + proyectos * 100
  }

  override def descripcion(): String =
    super.descripcion() + s", Arquitecto con $proyectos proyectos"
}
