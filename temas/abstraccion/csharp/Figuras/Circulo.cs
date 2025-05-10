using System;

namespace Figuras
{
    /// Implementación de un Círculo que cumple con IFigura.
    public class Circulo : IFigura
    {
        // Propiedad inmutable (solo se asigna en el constructor)
        public string Nombre { get; init; } = "Círculo";  

        // Propiedad privada con get público (encapsulamiento)
        public double Radio { get; private set; }  

        // Constructor
        public Circulo(double radio)
        {
            if (radio <= 0)
                throw new ArgumentException("El radio debe ser mayor que cero.");
            
            Radio = radio;
        }

        // Implementación de CalcularArea() para la clase Círculo
        public double CalcularArea() => Math.PI * Radio * Radio;  

        // Implementación de ObtenerDescripcion() para la clase Círculo
        public string ObtenerDescripcion(){
            var formatoDecimal = CalcularArea().ToString("0.00", System.Globalization.CultureInfo.InvariantCulture);
            return $"Soy un {Nombre} con radio {Radio} y área {formatoDecimal}.";
        }

    }
}