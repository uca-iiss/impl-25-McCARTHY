using Xunit;
using System;

namespace BancoApp.Tests
{
    public class CuentaBancariaTests
    {
        [Fact]
        public void Constructor_DeberiaInicializarSaldoYNumeroCuenta()
        {
            // Arrange
            string numeroCuenta = "1234567890";
            decimal saldoInicial = 500m;

            // Act
            var cuenta = new CuentaBancaria(numeroCuenta, saldoInicial);

            // Assert
            Assert.Equal(numeroCuenta, cuenta.NumeroCuenta);
            Assert.Equal(saldoInicial, cuenta.Saldo);
        }

        [Fact]
        public void Depositar_DeberiaAumentarSaldo()
        {
            // Arrange
            var cuenta = new CuentaBancaria("1234567890", 500m);
            decimal montoDeposito = 200m;
            decimal saldoEsperado = 700m;

            // Act
            cuenta.Depositar(montoDeposito);

            // Assert
            Assert.Equal(saldoEsperado, cuenta.Saldo);
        }

        [Fact]
        public void Retirar_DeberiaReducirSaldo()
        {
            // Arrange
            var cuenta = new CuentaBancaria("1234567890", 500m);
            decimal montoRetiro = 200m;
            decimal saldoEsperado = 300m;

            // Act
            cuenta.Retirar(montoRetiro);

            // Assert
            Assert.Equal(saldoEsperado, cuenta.Saldo);
        }

        [Fact]
        public void Retirar_SaldoInsuficiente_DeberiaLanzarExcepcion()
        {
            // Arrange
            var cuenta = new CuentaBancaria("1234567890", 100m);
            decimal montoRetiro = 200m;

            // Act & Assert
            Assert.Throws<InvalidOperationException>(() => cuenta.Retirar(montoRetiro));
        }

        [Fact]
        public void Depositar_CantidadNegativa_DeberiaLanzarExcepcion()
        {
            // Arrange
            var cuenta = new CuentaBancaria("1234567890", 500m);
            decimal montoDeposito = -100m;

            // Act & Assert
            Assert.Throws<ArgumentException>(() => cuenta.Depositar(montoDeposito));
        }

        [Fact]
        public void CrearCuenta_SaldoNegativo_DeberiaLanzarExcepcion()
        {
            // Act & Assert
            Assert.Throws<ArgumentException>(() => new CuentaBancaria("1234567890", -500m));
        }
    }
}
