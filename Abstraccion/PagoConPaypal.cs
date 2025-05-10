using System;

public class PagoConPayPal : IMetodoPago
{
    public void ProcesarPago(decimal cantidad)
    {
        Console.WriteLine($"Pago de {cantidad:C} procesado a través de PayPal.");
    }
}
