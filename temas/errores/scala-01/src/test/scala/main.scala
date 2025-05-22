import org.scalatest.funsuite.AnyFunSuite

class main extends AnyFunSuite {

  val cliente = Cliente("Alejandro Salvador")
  val diagnosticoOk = Diagnostico("Pantalla rota", irreparable = false)
  val diagnosticoMalo = Diagnostico("Placa base quemada", irreparable = true)
  val dispositivoConDiagnostico = Dispositivo("Samsung", "Galaxy S21", Some(diagnosticoOk))
  val dispositivoIrreparable = Dispositivo("LG", "K40", Some(diagnosticoMalo))
  val dispositivoSinDiagnostico = Dispositivo("Motorola", "Moto G", None)

  val ordenValida = Orden(cliente, Some(dispositivoConDiagnostico))
  val ordenSinDiagnostico = Orden(cliente, Some(dispositivoSinDiagnostico))
  val ordenIrreparable = Orden(cliente, Some(dispositivoIrreparable))
  val ordenSinDispositivo = Orden(cliente, None)

  test("Procesar orden valida debe ser exito") {
    val resultado = ReparacionService.procesarOrden(ordenValida)
    assert(resultado.isRight)
  }

  test("Procesar orden sin diagnostico debe ser error") {
    val resultado = ReparacionService.procesarOrden(ordenSinDiagnostico)
    assert(resultado.isLeft)
  }

  test("Procesar orden irreparable debe ser error") {
    val resultado = ReparacionService.procesarOrden(ordenIrreparable)
    assert(resultado.isLeft)
  }

  test("Procesar orden sin dispositivo debe ser error") {
    val resultado = ReparacionService.procesarOrden(ordenSinDispositivo)
    assert(resultado.isLeft)
  }

}