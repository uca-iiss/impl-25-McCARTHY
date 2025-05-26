package notificaciones;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        ServicioMensajes servicio = injector.getInstance(ServicioMensajes.class);
        servicio.notificar("Tu pedido ha sido procesado.");
    }
}
