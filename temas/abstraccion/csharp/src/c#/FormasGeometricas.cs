using System;

public interface IFormaGeometrica
{
    double CalcularArea();
    double CalcularPerimetro();
}

public class Circulo : IFormaGeometrica
{
    public double Radio { get; }

    public Circulo(double radio)
    {
        if (radio <= 0) throw new ArgumentException("Radio debe ser positivo");
        Radio = radio;
    }

    public double CalcularArea() => Math.PI * Radio * Radio;
    public double CalcularPerimetro() => 2 * Math.PI * Radio;
}

public class Rectangulo : IFormaGeometrica
{
    public double Base { get; }
    public double Altura { get; }

    public Rectangulo(double @base, double altura)
    {
        if (@base <= 0 || altura <= 0) throw new ArgumentException("Base y altura deben ser positivas");
        Base = @base;
        Altura = altura;
    }

    public double CalcularArea() => Base * Altura;
    public double CalcularPerimetro() => 2 * (Base + Altura);
}