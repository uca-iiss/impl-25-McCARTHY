package empleados

class Asistente(nombre: String, salarioBase: Double, horasExtras: Int, val oficina: String)
  extends Administrativo(nombre, salarioBase, horasExtras) {

  override def calcularSalario(): Double = salarioBase + pagoHorasExtras()

  override def descripcion(): String =
    super.descripcion() + s", oficina: $oficina"
}
