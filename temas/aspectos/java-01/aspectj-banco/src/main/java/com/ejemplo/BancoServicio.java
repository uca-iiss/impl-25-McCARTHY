package com.ejemplo;

public class BancoServicio {

    public void transferir(String origen, String destino, double monto) {
        System.out.printf("Transferencia de %.2f€ de %s a %s realizada.%n", monto, origen, destino);
    }

    public void retirar(String cuenta, double monto) {
        System.out.printf("Retiro de %.2f€ de la cuenta %s realizado.%n", monto, cuenta);
    }

    public void consultarSaldo(String cuenta) {
        System.out.printf("Saldo consultado de la cuenta %s.%n", cuenta);
    }
}
