using System;

class Program
{
    static void Main()
    {
        IFormaGeometrica[] formas = {
            new Circulo(5),
            new Rectangulo(4, 6)
        };

        foreach (var forma in formas)
        {
            Console.WriteLine($"Área: {forma.CalcularArea():F2}");
            Console.WriteLine($"Perímetro: {forma.CalcularPerimetro():F2}");
            Console.WriteLine();
        }
    }
}