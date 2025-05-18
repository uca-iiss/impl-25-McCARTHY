import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HerenciaSpec extends AnyFlatSpec with Matchers {

  "Un Estudiante" should "saludar correctamente" in {
    val est = new Estudiante("Ana", 21, "UCA")
    est.saludar() should be ("Hola, soy Ana, tengo 21 años y estudio en UCA.")
  }
}

