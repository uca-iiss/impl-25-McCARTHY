using System;
using Xunit;

public class FormasGeometricasTests
{
    [Fact]
    public void Circulo_Calculos_Correctos()
    {
        var circulo = new Circulo(5);
        Assert.Equal(78.54, circulo.CalcularArea(), 2);
        Assert.Equal(31.42, circulo.CalcularPerimetro(), 2);
    }

    [Fact]
    public void Rectangulo_Calculos_Correctos()
    {
        var rect = new Rectangulo(4, 6);
        Assert.Equal(24, rect.CalcularArea());
        Assert.Equal(20, rect.CalcularPerimetro());
    }

    [Fact]
    public void Validaciones_Funcionan()
    {
        Assert.Throws<ArgumentException>(() => new Circulo(-1));
        Assert.Throws<ArgumentException>(() => new Rectangulo(0, 5));
    }
}