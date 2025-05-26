package empleados

import org.scalatest.funsuite.AnyFunSuite

class EmpleadoTest extends AnyFunSuite {

  test("El salario del Arquitecto incluye experiencia, lenguaje y proyectos") {
    val arq = new ArquitectoSoftware("Mario", 4000, 6, "Scala", 3)
    assert(arq.calcularSalario() == (4000 * 1.2 + 500 + 300))
  }

  test("Un tester con 4 años no tiene bono") {
    val tester = new Tester("Carla", 2500, 4, "Manual")
    assert(tester.calcularSalario() == 2500)
  }

  test("Un tester con 5 años tiene bono") {
    val tester = new Tester("Luis", 2500, 5, "Automatizada")
    assert(tester.calcularSalario() == 3000)
  }

  test("Un gerente tiene salario base + bono") {
    val gerente = new Gerente("Nora", 6000, 1200)
    assert(gerente.calcularSalario() == 7200)
  }

  test("Asistente con horas extras") {
    val asistente = new Asistente("Pamela", 2200, 10, "RRHH")
    assert(asistente.calcularSalario() == 2200 + 150)
  }

  test("Contador con informes") {
    val contador = new Contador("Carlos", 3000, 5, 4)
    assert(contador.calcularSalario() == 3000 + 75 + 100)
  }
}
