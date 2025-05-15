using Xunit;
using System;
using System.Reflection;

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
        public void Depositar_CantidadNegativa_DeberiaLanzarExcepcion()
        {
            // Arrange
            var cuenta = new CuentaBancaria("1234567890", 500m);
            decimal montoDeposito = -100m;

            // Act & Assert
            Assert.Throws<ArgumentException>(() => cuenta.Depositar(montoDeposito));
        }

        [Fact]
        public void NumeroCuenta_NoDebeTenerSetterPublico()
        {
            // Arrange
            var propiedad = typeof(CuentaBancaria).GetProperty("NumeroCuenta");

            // Act
            var tieneSetter = propiedad?.SetMethod != null && propiedad.SetMethod.IsPublic;

            // Assert
            Assert.False(tieneSetter, "La propiedad NumeroCuenta no debe tener un setter público.");
        }

        [Fact]
        public void Saldo_NoDebeTenerSetterPublico()
        {
            // Arrange
            var propiedad = typeof(CuentaBancaria).GetProperty("Saldo");

            // Act
            var tieneSetter = propiedad?.SetMethod != null && propiedad.SetMethod.IsPublic;

            // Assert
            Assert.False(tieneSetter, "La propiedad Saldo no debe tener un setter público.");
        }
    }
}
