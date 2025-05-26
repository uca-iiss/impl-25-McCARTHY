package notificaciones; // Define el paquete al que pertenece esta clase

import com.google.inject.AbstractModule; // Importa la clase base para definir un módulo de configuración de Guice

// Clase que configura las asociaciones entre interfaces y sus implementaciones concretas
// Se utiliza por Guice para saber qué clases inyectar en tiempo de ejecución
public class AppModule extends AbstractModule {

    // Método que se sobrescribe para definir las vinculaciones (bindings)
    @Override
    protected void configure() {
        // Indica que cuando se necesite una instancia de Notificador, se debe usar EmailNotificador
        bind(Notificador.class).to(EmailNotificador.class);

        // Para cambiar a otra implementación, como SmsNotificador, se podría usar:
        // bind(Notificador.class).to(SmsNotificador.class);
    }
}
