# Anotaciones en C#

## Introducción
Bienvenido al repositorio sobre Anotaciones en C#. Aquí encontrarás información detallada sobre cómo utilizar las anotaciones en C#.

## Estructura de Directorio
- `/README.md`: Archivo actual.
- `/main.cs`: Archivo C# donde se encuentra el codigo de las clases sobre anotaciones.
- `/main.csproj`: Archivo de configuración esencial en cualquier proyecto de C# basado en .NET. Define cómo debe compilarse y ejecutarse el archivo main.cs, así como sus dependencias, el framework objetivo y otros parámetros clave.
- `/test.cs`: Archivo C# donde se encuentra el codigo de los test
- `/test.csproj`: Archivo de configuración esencial en cualquier proyecto de C# basado en .NET. Define cómo debe compilarse y ejecutarse el archivo test.cs, así como sus dependencias, el framework objetivo y otros parámetros clave.

## Conceptos Previos
Los atributos en C# permiten agregar metadatos declarativos a elementos del código (clases, métodos, propiedades, etc.). Son útiles en tiempo de compilación y ejecución para modificar el comportamiento del programa o facilitar tareas como validación, serialización, pruebas, entre otros.

- **Definición y Uso de Atributos**:

Los atributos en C# se definen como clases que heredan del tipo System.Attribute. Estas clases pueden contener propiedades, campos y constructores que permiten configurar su comportamiento y datos asociados. Además, utilizando el atributo AttributeUsage, se puede indicar a qué elementos del código se puede aplicar el atributo (por ejemplo, clases, métodos, propiedades, etc.), y si es posible aplicar múltiples instancias del mismo atributo en un solo elemento. Esta configuración proporciona flexibilidad a la hora de utilizar atributos personalizados de forma controlada y estructurada.

```csharp 
[AttributeUsage(AttributeTargets.Class | AttributeTargets.Method, AllowMultiple = true)]
public class ExampleAttribute : Attribute
{
    public string Description { get; set; }
    public ExampleAttribute(string description)
    {
        Description = description;
    }
}
```

- **Aplicación de Atributos**:

Los atributos se aplican escribiéndolos entre corchetes [ ] justo antes del elemento del código al que se refieren. Esto puede incluir clases, métodos, propiedades, campos, parámetros, ensamblados y más. Al aplicar un atributo, es posible pasarle argumentos definidos en su constructor, así como establecer valores para sus propiedades públicas. Esto hace que los atributos sean altamente configurables y útiles para describir el propósito o comportamiento esperado de los elementos del programa.

```csharp 
[Example("This is a class attribute.")]
public class MyClass
{
    [Example("This is a method attribute.")]
    public void MyMethod() { }
}

```

- **Atributos Intrínsecos y Personalizados**:

C# incluye una variedad de atributos integrados en el .NET Framework que cumplen funciones específicas. Algunos ejemplos comunes incluyen [Obsolete], que marca un elemento como obsoleto y genera una advertencia en tiempo de compilación; [Serializable], que indica que una clase puede ser serializada; y [DllImport], que permite importar funciones de bibliotecas externas. Sin embargo, cuando los atributos estándar no son suficientes, los desarrolladores pueden definir atributos personalizados que se ajusten a necesidades específicas del dominio de la aplicación. Esta capacidad de extensión permite crear soluciones más flexibles y mantenible

- **Reflexión y Atributos**:

La reflexión es una característica de C# que permite inspeccionar la estructura del programa en tiempo de ejecución. Usando reflexión, es posible acceder a los atributos aplicados a una clase, método o cualquier otro miembro, y actuar en consecuencia. Esto es especialmente útil en frameworks, bibliotecas de validación, motores de pruebas automáticas, herramientas de documentación y otros escenarios donde se requiere comportamiento dinámico basado en metadatos.

```csharp
var attributes = typeof(MyClass).GetCustomAttributes(false);
foreach (Attribute attr in attributes)
{
    Console.WriteLine(attr);
}
```

- **Atributos y Serialización**:

Los atributos juegan un papel importante en la serialización de objetos, especialmente en el contexto de APIs y servicios web. En .NET, atributos como [JsonPropertyName] permiten cambiar el nombre con el que se serializa una propiedad, mientras que [JsonIgnore] evita que una propiedad sea incluida en la salida serializada. Esta capacidad de control detallado sobre la salida serializada permite que los objetos se adapten mejor a los requerimientos de comunicación externa sin modificar su estructura interna.

```csharp
public class Product
{
    [JsonPropertyName("id")]
    public int Id { get; set; }

    [JsonIgnore]
    public string InternalData { get; set; }
}
```

## Código de Ejemplo
A continuación, se muestran los ejemplos de cómo se pueden utilizar las anotaciones:

[**main.cs**](./anotaciones_csharp/main.cs)

```cs
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
```

### Atributo Personalizado: `RangoSalarioAttribute`:

Este atributo personalizado se utiliza para definir un rango válido de valores para propiedades numéricas (en este caso, salarios). Su aplicación está restringida exclusivamente a propiedades.

```C#
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
```

- Restricción de Uso: Mediante [AttributeUsage], se indica que el atributo puede aplicarse solo a propiedades (Property) y no se permite usarlo múltiples veces en la misma propiedad (AllowMultiple = false).

- Propiedades de Solo Lectura: Min y Max definen los límites inferior y superior del rango. Estas propiedades se inicializan a través del constructor y no pueden modificarse después, lo que garantiza la integridad de la validación.

### Validación en la clase: `Trabajador`:

El método `Validar` es esencial para verificar si los valores de las propiedades cumplen con las restricciones definidas por el atributo RangoSalarioAttribute. Se basa en la reflexión para obtener las propiedades de la instancia y validar su contenido.

```C#
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

```

- Permite verificar automáticamente que los valores cumplan las reglas de negocio declaradas en la clase (Salario entre 1000 y 10000).

- En caso de que el valor esté fuera del rango, se lanza una excepción que incluye el nombre de la propiedad y el rango permitido.

- Asegura que el estado de la instancia sea válido antes de proceder con otras operaciones.

### Personalización de la Serialización JSON en la Clase `Empresa`:

La clase Empresa demuestra el uso de atributos de System.Text.Json para modificar la forma en que las propiedades se representan en el JSON resultante.

```C#
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
```

- Cambio de nombre de propiedades en el JSON: Por ejemplo, Nombre se convierte en "nombre_empresa" cuando se serializa.

- Interoperabilidad: Estos cambios permiten que el JSON generado sea compatible con APIs externas que exigen formatos específicos

- Control completo del output JSON: Se puede asegurar consistencia entre las estructuras internas del sistema y los formatos requeridos por consumidores externos.

### Metodo `Main`:

El método Main sirve como un punto de entrada que demuestra cómo se aplican en tiempo de ejecución las validaciones y serializaciones implementadas anteriormente.

```C#
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

```

## Código de tests
Ahora, se muestra unos tests para probar el correcto del ejemplo:
[**test.cs**](./test_csharp/test.cs)

```C#
using NUnit.Framework;
using System;
using System.Text.Json;
using System.Collections.Generic;


namespace EmpresaTests
{
    [TestFixture]
    public class TrabajadorTests
    {
        [Test]
        public void ValidarSalario_Valido_NoLanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Pedro", Salario = 3000m };
            Assert.DoesNotThrow(() => trabajador.Validar());
        }

        [Test]
        public void ValidarSalario_Invalido_LanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Laura", Salario = 200m };
            var ex = Assert.Throws<ArgumentOutOfRangeException>(() => trabajador.Validar());
            StringAssert.Contains("Salario", ex.Message);
        }

        [Test]
        public void SerializarEmpresa_ContieneDatosCorrectos()
        {
            var empresa = new Empresa
            {
                Nombre = "Innovatech",
                Ubicacion = "Barcelona",
                Empleados = new List<Trabajador>
                {
                    new Trabajador { Nombre = "Carlos", Salario = 4000m },
                    new Trabajador { Nombre = "Elena", Salario = 5200m }
                }
            };

            string json = JsonSerializer.Serialize(empresa);

            StringAssert.Contains("Innovatech", json);
            StringAssert.Contains("Barcelona", json);
            StringAssert.Contains("Carlos", json);
            StringAssert.Contains("Elena", json);
        }

        [Test]
        public void Salario_MinimoPermitido_EsValido()
        {
            var trabajador = new Trabajador { Nombre = "Pepe", Salario = 1000m };
            Assert.DoesNotThrow(() => trabajador.Validar());
        }

        [Test]
        public void Salario_MaximoPermitido_EsValido()
        {
            var trabajador = new Trabajador { Nombre = "Lucía", Salario = 10000m };
            Assert.DoesNotThrow(() => trabajador.Validar());
        }

        [Test]
        public void Salario_JustoPorDebajoDelMinimo_LanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Mario", Salario = 999.99m };
            Assert.Throws<ArgumentOutOfRangeException>(() => trabajador.Validar());
        }

        [Test]
        public void Salario_JustoPorEncimaDelMaximo_LanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Ana", Salario = 10000.01m };
            Assert.Throws<ArgumentOutOfRangeException>(() => trabajador.Validar());
        }

        [Test]
        public void SerializacionEmpresa_PropiedadesJsonRenombradasCorrectamente()
        {
            var empresa = new Empresa
            {
                Nombre = "DataCorp",
                Ubicacion = "Valencia",
                Empleados = new List<Trabajador>()
            };

            string json = JsonSerializer.Serialize(empresa);
            StringAssert.Contains("\"nombre_empresa\"", json);
            StringAssert.Contains("\"ubicacion\"", json);
            StringAssert.Contains("\"trabajadores\"", json);
        }
    }
}
```

### ValidarSalario_Valido_NoLanzaExcepcion:

```C#
[Test]
        public void ValidarSalario_Valido_NoLanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Pedro", Salario = 3000m };
            Assert.DoesNotThrow(() => trabajador.Validar());
        }
```
- Verifica que un salario dentro del rango especificado (1000 a 10000) no genera errores.

- Que la ejecución de trabajador.Validar() con un salario de 3000m no lanza ninguna excepción.

### ValidarSalario_Invalido_LanzaExcepcion:

```C#
[Test]
        public void ValidarSalario_Invalido_LanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Laura", Salario = 200m };
            var ex = Assert.Throws<ArgumentOutOfRangeException>(() => trabajador.Validar());
            StringAssert.Contains("Salario", ex.Message);
        }
```
- Valida que un salario fuera del rango (por debajo de 1000) lanza una ArgumentOutOfRangeException.

- Un salario de 200m lanza excepción. También se verifica que el mensaje de error contiene el nombre de la propiedad.

### SerializarEmpresa_ContieneDatosCorrectos:

```C#
[Test]
        public void SerializarEmpresa_ContieneDatosCorrectos()
        {
            var empresa = new Empresa
            {
                Nombre = "Innovatech",
                Ubicacion = "Barcelona",
                Empleados = new List<Trabajador>
                {
                    new Trabajador { Nombre = "Carlos", Salario = 4000m },
                    new Trabajador { Nombre = "Elena", Salario = 5200m }
                }
            };

            string json = JsonSerializer.Serialize(empresa);

            StringAssert.Contains("Innovatech", json);
            StringAssert.Contains("Barcelona", json);
            StringAssert.Contains("Carlos", json);
            StringAssert.Contains("Elena", json);
        }
```
- Verifica que al serializar una empresa, el JSON generado contiene correctamente los datos de texto reales: nombre de empresa, ubicación y trabajadores.

- Que los valores "Innovatech", "Barcelona", "Carlos" y "Elena" aparecen correctamente en el string JSON generado por JsonSerializer.

### Salario_MinimoPermitido_EsValido:

```C#
        [Test]
        public void Salario_MinimoPermitido_EsValido()
        {
            var trabajador = new Trabajador { Nombre = "Pepe", Salario = 1000m };
            Assert.DoesNotThrow(() => trabajador.Validar());
        }
```
- Confirma que el límite inferior exacto del salario (1000m) es considerado válido.

- Validar() no lanza excepción cuando el salario es exactamente 1000m.


### Salario_MaximoPermitido_EsValido:

```C#
        [Test]
        public void Salario_MaximoPermitido_EsValido()
        {
            var trabajador = new Trabajador { Nombre = "Lucía", Salario = 10000m };
            Assert.DoesNotThrow(() => trabajador.Validar());
        }
```
- Confirma que el límite superior exacto (10000m) es válido.

- No se lanza ninguna excepción cuando el salario es exactamente 10000m.

### Salario_JustoPorDebajoDelMinimo_LanzaExcepcion:

```C#
        [Test]
        public void Salario_JustoPorDebajoDelMinimo_LanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Mario", Salario = 999.99m };
            Assert.Throws<ArgumentOutOfRangeException>(() => trabajador.Validar());
        }
```
- Valida que un salario ligeramente por debajo del mínimo permitido (999.99m) genera error.

- Se lanza una ArgumentOutOfRangeException al validar un salario fuera del límite inferior.

### Salario_JustoPorEncimaDelMaximo_LanzaExcepcion:

```C#
        [Test]
        public void Salario_JustoPorEncimaDelMaximo_LanzaExcepcion()
        {
            var trabajador = new Trabajador { Nombre = "Ana", Salario = 10000.01m };
            Assert.Throws<ArgumentOutOfRangeException>(() => trabajador.Validar());
        }
```
- Valida que un salario ligeramente superior al máximo (10000.01m) genera error.

- Se lanza una ArgumentOutOfRangeException al ejecutar Validar() con este valor.

### SerializacionEmpresa_PropiedadesJsonRenombradasCorrectamente:

```C#
        [Test]
        public void SerializacionEmpresa_PropiedadesJsonRenombradasCorrectamente()
        {
            var empresa = new Empresa
            {
                Nombre = "DataCorp",
                Ubicacion = "Valencia",
                Empleados = new List<Trabajador>()
            };

            string json = JsonSerializer.Serialize(empresa);
            StringAssert.Contains("\"nombre_empresa\"", json);
            StringAssert.Contains("\"ubicacion\"", json);
            StringAssert.Contains("\"trabajadores\"", json);
        }
```
- Verifica que las propiedades decoradas con [JsonPropertyName] son correctamente renombradas en el JSON.

- Prueba que el JSON contiene los nombres personalizados "nombre_empresa", "ubicacion" y "trabajadores". Esto confirma que las anotaciones de serialización funcionan como se espera.

## Ejecución Test
Para ejecutar el código y pasar los test de dicho código, realiza los siguientes pasos detallados que incluyen la instalacion de python, la creación de un Jenkinsfile, creación pipeline y ejecución del pipeline

### 1. Creación Jenkinsfile
A continuación, hemos creado el Jenkinsfile necesario para realizar el pipeline, este se encuentra en la carpeta con el resto de código

```Jenkinsfile
pipeline {
    agent {
        docker {
            image 'mcr.microsoft.com/dotnet/sdk:6.0'
            args '-u root:root'
        }
    }

    environment {
        DOTNET_CLI_TELEMETRY_OPTOUT = '1'
        DOTNET_SKIP_FIRST_TIME_EXPERIENCE = '1'
        PATH = "${env.HOME}/.dotnet/tools:${env.PATH}" // Asegura que las herramientas globales estén accesibles
    }

    options {
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Clonando el repositorio...'
                checkout scm
            }
        }

        stage('Restaurar dependencias') {
            steps {
                sh 'dotnet restore anotaciones/anotaciones_csharp/main.csproj'
                sh 'dotnet restore anotaciones/test_csharp/test.csproj'
            }
        }

        stage('Compilar') {
            steps {
                sh 'dotnet build anotaciones/anotaciones_csharp/main.csproj --configuration Release'
                sh 'dotnet build anotaciones/test_csharp/test.csproj --configuration Release'
            }
        }

        stage('Ejecutar Tests') {
            steps {
                sh 'dotnet test anotaciones/test_csharp/test.csproj --logger trx;LogFileName=test_results.trx'
            }
        }

        stage('Convertir TRX a JUnit XML') {
            steps {
                // Instala trx2junit si no está disponible
                sh 'dotnet tool install -g trx2junit || true'

                sh '/root/.dotnet/tools/trx2junit anotaciones/test_csharp/TestResults/*.trx'

            }
        }

        stage('Publicar resultados de test') {
            steps {
                junit 'anotaciones/test_csharp/TestResults/*.xml'
            }
        }
    }

    post {
        success {
            echo 'Pipeline finalizada con éxito.'
        }

        failure {
            echo 'La compilación o pruebas fallaron.'
        }
    }
}
```

### 2. Crear Pipeline
Una vez realizados los pasos anteriores, abrimos Jenkins y creamos un nuevo Pipeline. Para ello: 

 - Lo definimos como `Pipeline script from SCM` y como SCM seleccionamos `Git`.
 - Ponemos la siguiente URL: `https://github.com/uca-iiss/WIRTH-impl-25`.
 - En branch ponemos `*/feature-anotaciones`.
 - Por último, en Script Path añadimos `temas/anotaciones/csharp/Jenkinsfile`

Y con esta configuración hemos creado el pipeline necesario para la ejecución de los test

### 3. Ejecutar los Tests
Una vez creado el pipeline, ejecutamos dando a `Construir ahora` y el propio Jenkins pasará los test automaticamente.