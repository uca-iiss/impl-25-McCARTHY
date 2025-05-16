using System;

// Encapsulamiento: la clase es responsable de manejar su propio estado
public class CuentaBancaria
{
    // Campo privado (ocultación de implementación)
    private decimal saldo;

    // Propiedad de solo lectura para acceso uniforme e inmutabilidad
    public decimal Saldo
    {
        get => saldo; // Proporciona acceso al saldo público
        // Proporciona acceso de forma privada al saldo para modificarlo
        private set => saldo = value >= 0 ? value : throw new ArgumentException("El saldo no puede ser negativo");
    }

    // Propiedad inmutable, solo lectura
    public string NumeroCuenta { get; }

    // Constructor
    public CuentaBancaria(string numeroCuenta, decimal saldoInicial)
    {
        // NumeroCuenta es una propiedad inmutable, se asigna en el constructor
        // y no puede ser modificada después
        NumeroCuenta = numeroCuenta ?? throw new ArgumentNullException(nameof(numeroCuenta));
        Saldo = saldoInicial;
    }

    // Método público (abstracción)
    public void Depositar(decimal cantidad)
    {
        if (cantidad <= 0)
            throw new ArgumentException("La cantidad debe ser positiva");

        Saldo += cantidad;
        Console.WriteLine($"Se han depositado {cantidad:C} en la cuenta {NumeroCuenta}. Saldo actual: {Saldo:C}");
    }

    // Método público (abstracción)
    public void Retirar(decimal cantidad)
    {
        if (cantidad <= 0)
            throw new ArgumentException("La cantidad debe ser positiva");
        if (cantidad > Saldo)
            throw new InvalidOperationException("Saldo insuficiente");

        Saldo -= cantidad;
        Console.WriteLine($"Se han retirado {cantidad:C} de la cuenta {NumeroCuenta}. Saldo actual: {Saldo:C}");
    }
}
