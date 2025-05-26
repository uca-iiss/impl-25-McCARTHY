using System;

public abstract class CuentaBancaria
{
    public string Titular { get; }
    public decimal Saldo { get; protected set; }

    public CuentaBancaria(string titular, decimal saldoInicial)
    {
        Titular = titular;
        Saldo = saldoInicial;
    }

    public abstract void Depositar(decimal cantidad);
    public abstract void Retirar(decimal cantidad);

    public virtual void MostrarInfo()
    {
        Console.WriteLine($"Titular: {Titular}, Saldo: {Saldo:C}");
    }
}