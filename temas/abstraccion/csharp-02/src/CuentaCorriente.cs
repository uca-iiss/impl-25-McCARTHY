public class CuentaCorriente : CuentaBancaria
{
    private decimal limiteDescubierto = 500;

    public CuentaCorriente(string titular, decimal saldoInicial) : base(titular, saldoInicial) {}

    public override void Depositar(decimal cantidad)
    {
        if (cantidad > 0)
        {
            Saldo += cantidad;
            Console.WriteLine($"Depositados {cantidad:C} en cuenta corriente.");
        }
    }

    public override void Retirar(decimal cantidad)
    {
        if (cantidad > 0 && Saldo + limiteDescubierto >= cantidad)
        {
            Saldo -= cantidad;
            Console.WriteLine($"Retirados {cantidad:C} de cuenta corriente.");
        }
        else
        {
            Console.WriteLine("Límite de descubierto excedido.");
        }
    }

    public override void MostrarInfo()
    {
        Console.WriteLine($"Cuenta Corriente - Titular: {Titular}, Saldo: {Saldo:C}, Límite descubierto: {limiteDescubierto:C}");
    }
}