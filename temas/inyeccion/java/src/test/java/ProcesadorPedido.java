import com.google.inject.Inject;

public class ProcesadorPedido {

    private final VerificadorStock stock;
    private final CalculadorDescuentos descuentos;
    private final ProcesadorPago pago;

    @Inject
    public ProcesadorPedido(VerificadorStock stock, CalculadorDescuentos descuentos, ProcesadorPago pago) {
        this.stock = stock;
        this.descuentos = descuentos;
        this.pago = pago;
    }

    public boolean procesar(String cliente, Pedido pedido) {
        System.out.println("Procesando pedido de: " + cliente);

        if (!stock.hayStock(pedido)) {
            System.out.println("No hay stock disponible.");
            return false;
        }

        double total = descuentos.aplicarDescuento(pedido);
        pago.cobrar(cliente, total);

        System.out.println("Pedido completado.");
        return true;
    }
}