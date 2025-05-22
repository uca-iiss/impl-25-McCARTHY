package empleados

class Contador(nombre: String, salarioBase: Double, horasExtras: Int, val informesAnuales: Int)
  extends Administrativo(nombre, salarioBase, horasExtras) {

  override def calcularSalario(): Double = salarioBase + pagoHorasExtras() + informesAnuales * 25

  override def descripcion(): String =
    super.descripcion() + s", informes anuales: $informesAnuales"
}
