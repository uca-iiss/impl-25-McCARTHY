package empleados

abstract class Empleado(val nombre: String, protected val salarioBase: Double) {
  def calcularSalario(): Double
  def descripcion(): String = s"Empleado: $nombre"
}
