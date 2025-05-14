import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());

        ProcesadorPedido procesador = injector.getInstance(ProcesadorPedido.class);

        Pedido pedido = new Pedido("Monitor 27 pulgadas", 7, 120.0);
        procesador.procesar("clienteXYZ", pedido);
    }
}