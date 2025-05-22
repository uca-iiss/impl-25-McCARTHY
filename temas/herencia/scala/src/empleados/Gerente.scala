package empleados

class Gerente(nombre: String, salarioBase: Double, val bono: Double)
  extends Empleado(nombre, salarioBase) {

  override def calcularSalario(): Double = salarioBase + bono

  override def descripcion(): String = s"Gerente: $nombre, bono: $$bono"
}
