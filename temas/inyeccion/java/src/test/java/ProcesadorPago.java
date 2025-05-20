public class ProcesadorPago {
    private double ultimoCobro = 0.0;

    public void cobrar(String cliente, double total) {
        this.ultimoCobro = total;
        System.out.println("Cobrando a " + cliente + ": $" + total);
    }

    public double getUltimoCobro() {
        return ultimoCobro;
    }
}