public class BancoMain {
    public static void Main()
    {
        CuentaBancaria cuenta1 = new CuentaAhorros("Ana", 1000);
        CuentaBancaria cuenta2 = new CuentaCorriente("Luis", 500);

        cuenta1.MostrarInfo();
        cuenta1.Depositar(200);
        ((CuentaAhorros)cuenta1).AplicarInteres();
        cuenta1.Retirar(500);
        cuenta1.MostrarInfo();

        Console.WriteLine();

        cuenta2.MostrarInfo();
        cuenta2.Depositar(300);
        cuenta2.Retirar(1000); 
        cuenta2.MostrarInfo();
    }
}