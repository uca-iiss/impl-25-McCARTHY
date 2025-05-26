package notificaciones;

import com.google.inject.Inject;

public class ServicioMensajes {
    private final Notificador notificador;

    @Inject
    public ServicioMensajes(Notificador notificador) {
        this.notificador = notificador;
    }

    public void notificar(String mensaje) {
        notificador.enviar(mensaje);
    }
}
 