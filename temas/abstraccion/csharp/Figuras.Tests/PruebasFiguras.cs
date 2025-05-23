using System;
using Xunit;
using System.IO;

namespace Figuras.Tests
{
    public class PruebasFiguras
    {
        // Test para comprobar el cálculo del área en Círculo
        [Fact]
        public void TestCirculoCalcularArea()
        {
            var circulo = new Circulo(5.0);
            double areaEsperada = Math.PI * 5.0 * 5.0;
            Assert.Equal(areaEsperada, circulo.CalcularArea(), 2);  // Precisión de 2 decimales
        }

        // Test para comprobar la descripción en Círculo
        [Fact]
        public void TestCirculoObtenerDescripcion()
        {
            var circulo = new Circulo(5.0);
            var output = new StringWriter();
            Console.SetOut(output);

            Console.WriteLine(circulo.ObtenerDescripcion());

            Assert.Contains("Círculo", output.ToString());
            Assert.Contains("radio 5", output.ToString());
            Assert.Contains("área 78.54", output.ToString());
        }

        // Test para comprobar el cálculo del área en Rectángulo
        [Fact]
        public void TestRectanguloCalcularArea()
        {
            var rectangulo = new Rectangulo(4.0, 6.0);
            Assert.Equal(24.0, rectangulo.CalcularArea());
        }

        // Test para comprobar la descripción en Rectángulo
        [Fact]
        public void TestRectanguloObtenerDescripcion()
        {
            var rectangulo = new Rectangulo(4.0, 6.0);
            var output = new StringWriter();
            Console.SetOut(output);

            Console.WriteLine(rectangulo.ObtenerDescripcion());

            Assert.Contains("Rectángulo", output.ToString());
            Assert.Contains("4 x 6", output.ToString());
            Assert.Contains("área 24.00", output.ToString());
        }

        // Test para comprobar el cálculo del área en Triángulo
        [Fact]
        public void TestTrianguloCalcularArea()
        {
            var triangulo = new Triangulo(3.0, 7.0);
            Assert.Equal(10.5, triangulo.CalcularArea());
        }

        // Test para comprobar la descripción en Triángulo
        [Fact]
        public void TestTrianguloObtenerDescripcion()
        {
            var triangulo = new Triangulo(3.0, 7.0);
            var output = new StringWriter();
            Console.SetOut(output);

            Console.WriteLine(triangulo.ObtenerDescripcion());

            Assert.Contains("Triángulo", output.ToString());
            Assert.Contains("base 3", output.ToString());
            Assert.Contains("altura 7", output.ToString());
            Assert.Contains("área 10.50", output.ToString());
        }
    }
}