import org.junit.Test
import org.junit.Assert.*

class AplicacionTest {
    @Test
    fun testIniciar() {
        val logger = ConsoleLogger()
        val app = Aplicacion("TestApp", logger)
        app.iniciar()
        assertTrue(true) // Prueba mínima: no lanza excepciones
    }

    @Test
    fun testFalloCritico() {
        val logger = ConsoleLogger()
        val app = Aplicacion("TestApp", logger)
        app.falloCritico()
        assertTrue(true) // Prueba mínima
    }
}
