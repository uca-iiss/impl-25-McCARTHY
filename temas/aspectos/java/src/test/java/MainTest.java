import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private Luz sala;
    private Termostato termostato;
    private SmartLock puerta;

    @BeforeEach
    public void setUp() {
        sala = new Luz("Luz del salon");
        termostato = new Termostato("Termostato");
        puerta = new SmartLock("Puerta principal");
    }

    @Test
    public void testLuzTurnOnOff() {
        sala.turnOn();
        assertTrue(sala.isOn(), "La luz debería estar encendida");

        sala.turnOff();
        assertFalse(sala.isOn(), "La luz debería estar apagada");
    }

    @Test
    public void testTermostatoOnOffAndTemperature() {
        termostato.turnOn();
        termostato.setTemperature(25);
        assertEquals(25, termostato.getTemperature(), "La temperatura debería ser 25");

        termostato.turnOff();
        assertFalse(termostato.isOn(), "El termostato debería estar apagado");
    }

    @Test
    public void testSmartLockWithoutLoginFails() {
        Exception exception = assertThrows(Exception.class, () -> {
            puerta.unlock();
        });
        assertEquals("Acceso denegado. Inicie sesión primero.", exception.getMessage());
    }

    @Test
    public void testSmartLockWithLogin() throws Exception {
        SecurityAspect.login();
        puerta.unlock();  // No debe lanzar excepción
        assertTrue(puerta.isUnlocked(), "La cerradura debería estar desbloqueada");
        SecurityAspect.logout();
    }
}