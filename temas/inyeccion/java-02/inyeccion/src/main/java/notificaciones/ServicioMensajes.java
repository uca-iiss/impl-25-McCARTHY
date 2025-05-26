package notificaciones; // Define el paquete donde se encuentra esta clase

import com.google.inject.Inject; // Importa la anotación @Inject de Google Guice para inyección de dependencias

// Clase que representa un servicio que envía mensajes usando un notificador
public class ServicioMensajes {
    
    // Dependencia del servicio: una instancia de Notificador
    private final Notificador notificador;

    // Constructor con inyección de dependencias
    @Inject // Indica a Guice que debe usar este constructor para inyectar la dependencia
    public ServicioMensajes(Notificador notificador) {
        this.notificador = notificador; // Asigna la instancia inyectada al campo privado
    }

    // Método público que permite enviar un mensaje utilizando el notificador
    public void notificar(String mensaje) {
        notificador.enviar(mensaje); // Delega el envío del mensaje al objeto Notificador
    }
}
