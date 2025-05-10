using System;

public class PagoConTarjeta : IMetodoPago
{
    public void ProcesarPago(decimal cantidad)
    {
        Console.WriteLine($"Pago de {cantidad:C} procesado con tarjeta de crédito.");
    }
}
