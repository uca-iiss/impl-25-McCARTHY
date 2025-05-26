package com.ejemplo;

public class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario("Ana");
        BancoServicio banco = new BancoServicio();

        banco.consultarSaldo(usuario.getNombre());
        banco.transferir(usuario.getNombre(), "Carlos", 200.0);
        banco.retirar(usuario.getNombre(), 50.0);
    }
}
