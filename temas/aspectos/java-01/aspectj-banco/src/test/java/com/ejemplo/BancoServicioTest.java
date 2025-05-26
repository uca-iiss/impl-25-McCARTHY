package com.ejemplo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BancoServicioTest {

    private BancoServicio banco;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        banco = new BancoServicio();
        usuario = new Usuario("Ana");
    }

    @Test
    void testTransferencia() {
        banco.transferir(usuario.getNombre(), "Carlos", 200.0);
    }

    @Test
    void testRetiro() {
        banco.retirar(usuario.getNombre(), 50.0);
    }

    @Test
    void testConsultaSaldo() {
        banco.consultarSaldo(usuario.getNombre());
    }
}
