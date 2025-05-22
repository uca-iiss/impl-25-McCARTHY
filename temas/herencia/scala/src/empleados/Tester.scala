package empleados

class Tester(nombre: String, salarioBase: Double, experiencia: Int, val tipoPrueba: String)
  extends Tecnico(nombre, salarioBase, experiencia) {

  override def calcularSalario(): Double = salarioBase + bonoExperiencia()

  override def descripcion(): String =
    super.descripcion() + s", Tester de tipo: $tipoPrueba"
}
