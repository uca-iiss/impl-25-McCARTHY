public class CuentaAhorros : CuentaBancaria
{
    private decimal tasaInteres = 0.03m;

    public CuentaAhorros(string titular, decimal saldoInicial) : base(titular, saldoInicial) {}

    public override void Depositar(decimal cantidad)
    {
        if (cantidad > 0)
        {
            Saldo += cantidad;
            Console.WriteLine($"Depositados {cantidad:C} en cuenta de ahorros.");
        }
    }

    public override void Retirar(decimal cantidad)
    {
        if (cantidad > 0 && cantidad <= Saldo)
        {
            Saldo -= cantidad;
            Console.WriteLine($"Retirados {cantidad:C} de cuenta de ahorros.");
        }
        else
        {
            Console.WriteLine("Fondos insuficientes para retiro.");
        }
    }

    public void AplicarInteres()
    {
        decimal interes = Saldo * tasaInteres;
        Saldo += interes;
        Console.WriteLine($"Interés aplicado: {interes:C}");
    }

    public override void MostrarInfo()
    {
        Console.WriteLine($"Cuenta de Ahorros - Titular: {Titular}, Saldo: {Saldo:C}");
    }
}