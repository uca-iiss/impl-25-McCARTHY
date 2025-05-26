package notificaciones;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicioMensajesTest {

    @Test
    void testNotificacionEmail() {
        Notificador notificador = new EmailNotificador();
        ServicioMensajes servicio = new ServicioMensajes(notificador);

        assertDoesNotThrow(() -> servicio.notificar("Mensaje de prueba"));
    }
}
