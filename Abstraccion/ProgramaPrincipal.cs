using System;
using System.Collections.Generic;

class ProgramaPrincipal
{
    static void Main()
    {
        List<IMetodoPago> metodos = new List<IMetodoPago>
        {
            new PagoConTarjeta(),
            new PagoConPayPal(),
            new PagoPorTransferencia()
        };

        foreach (var metodo in metodos)
        {
            metodo.ProcesarPago(100.00m);
        }
    }
}
