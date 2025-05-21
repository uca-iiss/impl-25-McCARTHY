public class CalculadorDescuentos {
    public double aplicarDescuento(Pedido pedido) {
        double descuento = pedido.getCantidad() > 5 ? 0.1 : 0.0;
        double total = pedido.getTotalSinDescuento() * (1 - descuento);
        System.out.println("Total con descuento: $" + total);
        return total;
    }
}