import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testPedidoSinDescuento() {
        CalculadorDescuentos desc = new CalculadorDescuentos();
        Pedido pedido = new Pedido("Ratón inalámbrico", 2, 25.0);
        assertEquals(50.0, desc.aplicarDescuento(pedido), 0.001);
    }

    @Test
    public void testPedidoConDescuento() {
        CalculadorDescuentos desc = new CalculadorDescuentos();
        Pedido pedido = new Pedido("Monitor 27", 7, 120.0);
        assertEquals(7 * 120.0 * 0.9, desc.aplicarDescuento(pedido), 0.001);
    }

    @Test
    public void testVerificacionStock() {
        VerificadorStock stock = new VerificadorStock();
        Pedido pedido = new Pedido("Cualquier cosa", 1, 5.0);
        assertTrue(stock.hayStock(pedido)); // siempre devuelve true
    }

    @Test
    public void testCobroCorrecto() {
        ProcesadorPago pago = new ProcesadorPago();
        pago.cobrar("cliente", 200.0);
        assertEquals(200.0, pago.getUltimoCobro(), 0.001);
    }

    @Test
    public void testProcesamientoCompleto() {
        VerificadorStock stock = new VerificadorStock();
        CalculadorDescuentos desc = new CalculadorDescuentos();
        ProcesadorPago pago = new ProcesadorPago();
        ProcesadorPedido procesador = new ProcesadorPedido(stock, desc, pago);

        Pedido pedido = new Pedido("Producto", 10, 10.0); // debería aplicar descuento
        procesador.procesar("cliente", pedido);

        double esperado = 10 * 10.0 * 0.9;
        assertEquals(esperado, pago.getUltimoCobro(), 0.001);
    }
}