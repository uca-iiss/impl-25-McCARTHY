package notificaciones; // Define el paquete al que pertenece esta clase

import com.google.inject.Guice;   // Importa la clase Guice para crear inyectores
import com.google.inject.Injector; // Importa la clase Injector, que se usa para obtener instancias con dependencias inyectadas

// Clase principal que ejecuta la aplicación
public class Main {
    
    public static void main(String[] args) {
        // Crea el inyector de dependencias usando el módulo de configuración AppModule
        Injector injector = Guice.createInjector(new AppModule());
        
        // Solicita una instancia de ServicioMensajes al inyector. Guice se encargará de inyectar sus dependencias automáticamente.
        ServicioMensajes servicio = injector.getInstance(ServicioMensajes.class);
        
        // Utiliza el servicio para enviar una notificación
        servicio.notificar("Tu pedido ha sido procesado.");
    }
}
