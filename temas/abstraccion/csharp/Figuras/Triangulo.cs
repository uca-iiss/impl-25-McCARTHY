using System;

namespace Figuras
{
    /// Implementación de un Triángulo que cumple con IFigura.
    public class Triangulo : IFigura
    {
        // Propiedad inmutable
        public string Nombre { get; init; } = "Triángulo";  

        // Propiedades privadas con get público
        public double Base { get; private set; }  
        public double Altura { get; private set; }  

        // Constructor
        public Triangulo(double baseTri, double altura)
        {
            if (baseTri <= 0 || altura <= 0)
                throw new ArgumentException("Base y altura deben ser mayores que cero.");
            
            Base = baseTri;
            Altura = altura;
        }

        // Implementación de CalcularArea() de clase Triángulo
        public double CalcularArea() => (Base * Altura) / 2;  

        // Implementación de ObtenerDescripcion() de clase Triángulo
        public string ObtenerDescripcion() {
            var formatoDecimal = CalcularArea().ToString("0.00", System.Globalization.CultureInfo.InvariantCulture);
            return $"Soy un {Nombre} de base {Base}, altura {Altura} y área {formatoDecimal}.";
        }
    }
}