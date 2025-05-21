using System;
using Xunit;

public class CuentaBancariaTests
{
    [Fact]
    public void DepositarEnCuentaAhorros_AumentaSaldo()
    {
        var cuenta = new CuentaAhorros("Ana", 1000);
        cuenta.Depositar(500);
        Assert.Equal(1500, cuenta.Saldo);
    }

    [Fact]
    public void RetirarEnCuentaAhorros_RetiraSaldoCorrectamente()
    {
        var cuenta = new CuentaAhorros("Ana", 1000);
        cuenta.Retirar(400);
        Assert.Equal(600, cuenta.Saldo);
    }

    [Fact]
    public void RetirarEnCuentaAhorros_NoPermiteSaldoNegativo()
    {
        var cuenta = new CuentaAhorros("Ana", 1000);
        cuenta.Retirar(1500); // Más que saldo
        Assert.Equal(1000, cuenta.Saldo); // No cambia el saldo
    }

    [Fact]
    public void AplicarInteres_AumentaSaldo()
    {
        var cuenta = new CuentaAhorros("Ana", 1000);
        cuenta.AplicarInteres();
        Assert.Equal(1030, cuenta.Saldo);
    }

    [Fact]
    public void RetirarEnCuentaCorriente_PermiteDescubierto()
    {
        var cuenta = new CuentaCorriente("Luis", 500);
        cuenta.Retirar(900);
        Assert.Equal(-400, cuenta.Saldo);
    }

    [Fact]
    public void RetirarEnCuentaCorriente_NoPermiteExcederDescubierto()
    {
        var cuenta = new CuentaCorriente("Luis", 500);
        cuenta.Retirar(1100); // Excede límite de descubierto 500 + saldo 500 = 1000
        Assert.Equal(500, cuenta.Saldo); // No cambia el saldo
    }
}
