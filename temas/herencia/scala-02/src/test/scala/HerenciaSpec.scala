import org.scalatest.funsuite.AnyFunSuite

class HerenciaSpec extends AnyFunSuite {

  test("Estudiante debería imprimir su info académica") {
    val estudiante = new Estudiante("Lucía", 21, "Universidad de Cádiz")
    val info = estudiante.infoAcademica()
    println(s"[DEBUG] Info académica del estudiante: $info")
    assert(info.contains("Perteneciente al departamento de Estudios Generales"))
  }

  test("Profesor debería implementar Evaluador y Academico") {
    val profesor = new Profesor("Dr. Gómez", 45, "Informática")
    val info = profesor.infoAcademica()
    val evaluacion = profesor.evaluar()
    println(s"[DEBUG] Info académica del profesor: $info")
    println(s"[DEBUG] Evaluación del profesor: $evaluacion")
    assert(info.contains("Informática"))
    assert(evaluacion == "Realizando evaluación de estudiantes...")
  }

  test("Investigador debería imprimir su especialidad") {
    val investigadora = new Investigador("Sara", 38, "Inteligencia Artificial")
    val saludoFormal = investigadora.saludar(formal = true)
    println(s"[DEBUG] Saludo formal del investigador: $saludoFormal")
    assert(saludoFormal.contains("especialista en Inteligencia Artificial"))
  }

}

