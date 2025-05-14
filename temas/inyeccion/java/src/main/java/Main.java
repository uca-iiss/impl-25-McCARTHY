import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        ProcesadorPedido procesador = injector.getInstance(ProcesadorPedido.class);

        System.out.println("\n=== CASO 1: Pedido sin descuento ===");
        Pedido pedido1 = new Pedido("Ratón inalámbrico", 2, 25.0);
        procesador.procesar("clienteA", pedido1);

        System.out.println("\n=== CASO 2: Pedido con descuento ===");
        Pedido pedido2 = new Pedido("Monitor 27 pulgadas", 7, 120.0);
        procesador.procesar("clienteB", pedido2);

        System.out.println("\n=== CASO 3: Pedido caro sin descuento ===");
        Pedido pedido3 = new Pedido("Portátil Gaming", 1, 1500.0);
        procesador.procesar("clienteC", pedido3);

        System.out.println("\n=== CASO 4: Pedido exacto al límite de descuento ===");
        Pedido pedido4 = new Pedido("Teclado mecánico", 5, 70.0);
        procesador.procesar("clienteD", pedido4);

        System.out.println("\n=== CASO 5: Pedido grande ===");
        Pedido pedido5 = new Pedido("Lote de pendrives", 100, 3.5);
        procesador.procesar("clienteE", pedido5);
    }
}