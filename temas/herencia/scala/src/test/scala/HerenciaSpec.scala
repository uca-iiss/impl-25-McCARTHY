import org.scalatest.funsuite.AnyFunSuite

class HerenciaSpec extends AnyFunSuite {

  test("Estudiante debería imprimir su info académica") {
    val estudiante = new Estudiante("Lucía", 21, "Universidad de Cádiz")
    assert(estudiante.infoAcademica().contains("Universidad de Cádiz"))
  }

  test("Profesor debería implementar Evaluador y Academico") {
    val profesor = new Profesor("Dr. Gómez", 45, "Informática")
    assert(profesor.infoAcademica().contains("Informática"))
    assert(profesor.evaluar() == "Realizando evaluación de estudiantes...")
  }

  test("Investigador debería imprimir su especialidad") {
    val investigadora = new Investigador("Sara", 38, "Inteligencia Artificial")
    assert(investigadora.presentarse().contains("especialista en Inteligencia Artificial"))
  }
}

