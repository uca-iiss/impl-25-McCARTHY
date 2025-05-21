public class VerificadorStock {
    private boolean stockDisponible = true;

    public void setStockDisponible(boolean disponible) {
        this.stockDisponible = disponible;
    }

    public boolean hayStock(Pedido pedido) {
        System.out.println("Verificando stock para: " + pedido.getProducto());
        return stockDisponible;
    }
}