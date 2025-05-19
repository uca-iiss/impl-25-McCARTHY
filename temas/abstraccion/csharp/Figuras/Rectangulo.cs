using System;

namespace Figuras
{
    /// Implementación de un Rectángulo que cumple con IFigura.
    public class Rectangulo : IFigura
    {
        // Propiedad inmutable (solo se asigna en el constructor)
        public string Nombre { get; init; } = "Rectángulo";  

        // Propiedades con setters privados (encapsulamiento)
        public double Ancho { get; private set; }  
        public double Alto { get; private set; }  

        // Constructor
        public Rectangulo(double ancho, double alto)
        {
            if (ancho <= 0 || alto <= 0)
                throw new ArgumentException("Las dimensiones deben ser mayores que cero.");
            
            Ancho = ancho;
            Alto = alto;
        }

        // Implementación de CalcularArea() para la clase Rectángulo
        public double CalcularArea() => Ancho * Alto;  

        // Implementación de ObtenerDescripcion() para la clase Rectángulo
        public string ObtenerDescripcion(){
            var formatoDecimal = CalcularArea().ToString("0.00", System.Globalization.CultureInfo.InvariantCulture);
            return $"Soy un {Nombre} de {Ancho} x {Alto} y área {formatoDecimal}.";
        }
    }
}