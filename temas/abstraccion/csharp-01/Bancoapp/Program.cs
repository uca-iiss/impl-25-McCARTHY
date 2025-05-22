using System;

public class Programa
{
    public static void Main()
    {
        // Crear una nueva cuenta bancaria
        var cuenta = new CuentaBancaria("1234567890", 500m);

        // Intentar depositar y retirar fondos
        cuenta.Depositar(200m);
        cuenta.Retirar(100m);

        // Intentar acceder a las propiedades inmutables
        Console.WriteLine($"Número de cuenta: {cuenta.NumeroCuenta}");
        Console.WriteLine($"Saldo final: {cuenta.Saldo}");

        // Intentar modificar directamente el saldo (esto fallará)
        // cuenta.Saldo = 1000m; // Error: No se puede asignar a 'Saldo' porque es de solo lectura
    }
}
