using System;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Collections.Generic;


// Atributo personalizado para validar el salario
[AttributeUsage(AttributeTargets.Property, AllowMultiple = false)]
public class RangoSalarioAttribute : Attribute
{
    public decimal Min { get; }
    public decimal Max { get; }

    public RangoSalarioAttribute(double min, double max)
    {
        Min = (decimal)min;
        Max = (decimal)max;
    }
}

// Clase Trabajador usando el atributo personalizado
public class Trabajador
{
    [RangoSalario(1000, 10000)] // Salario entre 1000 y 10000
    public decimal Salario { get; set; }

    public string Nombre { get; set; } = string.Empty;

    public void Validar()
    {
        var propiedades = GetType().GetProperties();
        foreach (var prop in propiedades)
        {
            var attr = Attribute.GetCustomAttribute(prop, typeof(RangoSalarioAttribute)) as RangoSalarioAttribute;
            if (attr != null)
            {
                decimal valor = (decimal)prop.GetValue(this)!;
                if (valor < attr.Min || valor > attr.Max)
                {
                    throw new ArgumentOutOfRangeException(prop.Name, $"El salario debe estar entre {attr.Min} y {attr.Max}.");
                }
            }
        }
    }
}

// Clase Empresa con control de serialización JSON
public class Empresa
{
    [JsonPropertyName("nombre_empresa")]
    public string Nombre { get; set; } = string.Empty;

    [JsonPropertyName("ubicacion")]
    public string Ubicacion { get; set; } = string.Empty;

    [JsonPropertyName("trabajadores")]
    public List<Trabajador> Empleados { get; set; } = new List<Trabajador>();
}

// Programa principal
class Program
{
    static void Main(string[] args)
    {
        Console.WriteLine("Ejemplo 1: Validación de Salario Correcta");
        var trabajadorValido = new Trabajador { Nombre = "Ana", Salario = 3500m };
        try
        {
            trabajadorValido.Validar();
            Console.WriteLine("Validación exitosa para Ana.");
        }
        catch (ArgumentOutOfRangeException ex)
        {
            Console.WriteLine($"Error: {ex.Message}");
        }

        Console.WriteLine("\nEjemplo 2: Validación de Salario Incorrecta");
        var trabajadorInvalido = new Trabajador { Nombre = "Carlos", Salario = 500m };
        try
        {
            trabajadorInvalido.Validar();
            Console.WriteLine("Validación exitosa para Carlos.");
        }
        catch (ArgumentOutOfRangeException ex)
        {
            Console.WriteLine($"Error: {ex.Message}");
        }

        Console.WriteLine("\nEjemplo 3: Serialización JSON de Empresa");
        var empresa = new Empresa
        {
            Nombre = "Tech Solutions",
            Ubicacion = "Madrid",
            Empleados = new List<Trabajador>
            {
                new Trabajador { Nombre = "Ana", Salario = 3500m },
                new Trabajador { Nombre = "Luis", Salario = 4200m }
            }
        };

        string json = JsonSerializer.Serialize(empresa, new JsonSerializerOptions { WriteIndented = true });
        Console.WriteLine(json);
    }
}
