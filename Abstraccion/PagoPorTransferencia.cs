using System;

public class PagoPorTransferencia : IMetodoPago
{
    public void ProcesarPago(decimal cantidad)
    {
        Console.WriteLine($"Pago de {cantidad:C} procesado mediante transferencia bancaria.");
    }
}
