import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(VerificadorStock.class).toInstance(new VerificadorStock());
        bind(CalculadorDescuentos.class).toInstance(new CalculadorDescuentos());
        bind(ProcesadorPago.class).toInstance(new ProcesadorPago());
    }
}