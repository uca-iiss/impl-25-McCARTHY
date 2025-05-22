package empleados

abstract class Administrativo(nombre: String, salarioBase: Double, val horasExtras: Int)
  extends Empleado(nombre, salarioBase) {

  protected def pagoHorasExtras(): Double = horasExtras * 15.0

  override def descripcion(): String = s"Administrativo: $nombre, horas extras: $horasExtras"
}
