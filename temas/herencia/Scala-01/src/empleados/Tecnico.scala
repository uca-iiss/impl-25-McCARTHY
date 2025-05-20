package empleados

abstract class Tecnico(nombre: String, salarioBase: Double, val experiencia: Int)
  extends Empleado(nombre, salarioBase) {

  protected def bonoExperiencia(): Double = {
    if (experiencia >= 5) 500 else 0
  }

  override def descripcion(): String = {
    super.descripcion() + s", experiencia: $experiencia años"
  }
}
