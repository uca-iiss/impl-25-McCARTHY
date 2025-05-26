package notificaciones;

import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Notificador.class).to(EmailNotificador.class);
        // para usar SMS: .to(SmsNotificador.class);
    }
}
