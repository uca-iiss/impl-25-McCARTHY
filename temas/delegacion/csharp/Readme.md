# Delegación en C#

## Introducción

Bienvenido a este repositorio donde explicaremos y mostramos el concepto de Delgacion en C#, mediante un ejemplo sencillo con un codigo limpio y accesible para cualquiera
 
## Estructura de Directorio

- `/README.md`: Archivo actual.
- `/PrinterDelegationExample.csproj`: archivo de configuración esencial en cualquier proyecto de C# basado en .NET. Define cómo debe compilarse y ejecutarse el archivo test.cs, así como sus dependencias, el framework objetivo y otros parámetros clave.
- `/Program.cs`: Archivo C# donde se encuentra el codigo de las clases sobre delegación.
- `/test.cs`: Archivo C# donde se encuentra el codigo de los test
- `/test.csproj`: archivo de configuración esencial en cualquier proyecto de C# basado en .NET. Define cómo debe compilarse y ejecutarse el archivo test.cs, así como sus dependencias, el framework objetivo y otros parámetros clave.

## Conceptos Previos

Antes de comenzar con el codigo donde se muestra el concepto de delegación, vamos a explicar algunos conceptos claves necesarios para entender su funcionamiento correctamente: 

- **Delegación**: la delegación consiste en que un objeto no implementa directamente una funcionalidad, sino que delegará la ejecución de esa funcionalidad a otro objeto permitiendo asi separar responsabilidades, mejorar la flexibilidad y reutilizar código. 

- **Composicion**: la composición se encarga de construir objetos a partir de otros objetos permitiendo un acoplamiento debil y pudiendo cambiar el comportamiento en tiempo de ejecución. Un ejemplo en nuestro código es: `manager.SetPrinter(pdfPrinter.Print)`.

- **Delegación Polimórfica**: todos los objetos tienen una interfaz comun, pero su implementación cambia, esto lo haremos con una interfaz como contrato. En nuestro caso `IPrinter` define un contrato común: cualquier impresora debe tener un método `Print(string)`, asi podemos cambiar entre impresoras sin modificar `PrinterManager`

- **Delegates en C#**: un `delegate`es un tipo seguro de puntero a método, es como una función que puedes pasar como variable. Permite que una clase no conozca nada sobre la implementación del objeto que ejecutará la impresión. En nuestro caso tenemos esto: `public delegate void PrintDelegate (string content)`. 

## Código de Ejemplo
A continuacion tenemos el codigo principal de abstracción en C#:

[**Program.cs**](./PrinterDelegationExample/csharp/Program.cs)
```csharp
using System;

public interface IPrinter
{
    void Print(string content);
}

public class PdfPrinter : IPrinter
{
    public void Print(string content)
    {
        Console.WriteLine("Printing PDF: " + content);
    }
}

public class WordPrinter : IPrinter
{
    public void Print(string content)
    {
        Console.WriteLine("Printing Word Document: " + content);
    }
}

public class ImagePrinter : IPrinter
{
    public void Print(string content)
    {
        Console.WriteLine("Printing Image: " + content);
    }
}

public class PrinterManager
{
    public delegate void PrintDelegate(string content);
    
    private PrintDelegate _printDelegate;

    public void SetPrinter(PrintDelegate printerMethod)
    {
        _printDelegate = printerMethod;
    }

    public void PrintDocument(string content)
    {
        if (_printDelegate != null)
        {
            _printDelegate(content);  // Aquí ocurre la delegación
        }
        else
        {
            Console.WriteLine("No printer configured.");
        }
    }
}

public class Program
{
    public static void Main(string[] args)
    {
        var manager = new PrinterManager();

        IPrinter pdfPrinter = new PdfPrinter();
        IPrinter wordPrinter = new WordPrinter();
        IPrinter imagePrinter = new ImagePrinter();

        // Delegar a PDF printer
        manager.SetPrinter(pdfPrinter.Print);
        manager.PrintDocument("Informe de ventas");

        // Delegar a Word printer
        manager.SetPrinter(wordPrinter.Print);
        manager.PrintDocument("Carta de presentación");

        //Delegamar a Imager printer 
        manager.SetPrinter(imagePrinter.Print);
        manager.PrintDocument("Diagrama arquitectónico");
    }
}
```


## Código de tests
Ahora, se muestra unos tests para probar el correcto uso de las funciones delegación en C#:

[**test.cs**](./PrinterDelegationExample/test/test.cs)
```scala
using NUnit.Framework;
using System;
using System.IO;

namespace PrinterTests
{
    [TestFixture]
    public class PrinterManagerTests
    {
        private StringWriter output;
        private PrinterManager manager;

        [SetUp]
        public void Setup()
        {
            output = new StringWriter();
            Console.SetOut(output);
            manager = new PrinterManager();
        }

        [TearDown]
        public void TearDown()
        {
            output.Dispose();
        }

        [Test]
        public void TestPdfPrinter()
        {
            IPrinter printer = new PdfPrinter();
            manager.SetPrinter(printer.Print);
            manager.PrintDocument("Reporte anual");

            Assert.IsTrue(output.ToString().Contains("Printing PDF: Reporte anual"));
        }

        [Test]
        public void TestWordPrinter()
        {
            IPrinter printer = new WordPrinter();
            manager.SetPrinter(printer.Print);
            manager.PrintDocument("Carta laboral");

            Assert.IsTrue(output.ToString().Contains("Printing Word Document: Carta laboral"));
        }

        [Test]
        public void TestImagePrinter()
        {
            IPrinter printer = new ImagePrinter();
            manager.SetPrinter(printer.Print);
            manager.PrintDocument("Logo corporativo");

            Assert.IsTrue(output.ToString().Contains("Printing Image: Logo corporativo"));
        }

        [Test]
        public void TestNoPrinterConfigured()
        {
            manager.PrintDocument("Sin impresora configurada");

            Assert.IsTrue(output.ToString().Contains("No printer configured."));
        }

        [Test]
        public void TestChangePrinterAtRuntime()
        {
            IPrinter pdfPrinter = new PdfPrinter();
            IPrinter wordPrinter = new WordPrinter();

            manager.SetPrinter(pdfPrinter.Print);
            manager.PrintDocument("Archivo PDF");

            manager.SetPrinter(wordPrinter.Print);
            manager.PrintDocument("Archivo Word");

            string result = output.ToString();
            Assert.IsTrue(result.Contains("Printing PDF: Archivo PDF"));
            Assert.IsTrue(result.Contains("Printing Word Document: Archivo Word"));
        }

        [Test]
        public void TestEmptyContentPrint()
        {
            IPrinter printer = new PdfPrinter();
            manager.SetPrinter(printer.Print);
            manager.PrintDocument("");

            Assert.IsTrue(output.ToString().Contains("Printing PDF: "));
        }
    }
}
```

## Ejecución Test
Para ejecutar el código y pasar los test de dicho código, realiza los siguientes pasos detallados que incluyen la creación de un Jenkinsfile, creación pipeline y ejecución del pipeline

### 1. Creación Jenkinsfile
A continuación, hemos creado el Jenkinsfile necesario para realizar el pipeline, este se encuentra en la carpeta con el resto de código

```Jenkinsfile
pipeline {
    agent {
        docker {
            image 'mcr.microsoft.com/dotnet/sdk:7.0' // Como usas net7.0 en tests
            args '-u root:root'
        }
    }

    environment {
        DOTNET_CLI_TELEMETRY_OPTOUT = '1'
        DOTNET_SKIP_FIRST_TIME_EXPERIENCE = '1'
        PATH = "${env.HOME}/.dotnet/tools:${env.PATH}"
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
                sh 'dotnet restore temas/delegacion/csharp/PrinterDelegationExample/csharp/PrinterDelegationExample.csproj'
                sh 'dotnet restore temas/delegacion/csharp/PrinterDelegationExample/test/test.csproj'
            }
        }

        stage('Compilar') {
            steps {
                sh 'dotnet build temas/delegacion/csharp/PrinterDelegationExample/csharp/PrinterDelegationExample.csproj --configuration Release'
                sh 'dotnet build temas/delegacion/csharp/PrinterDelegationExample/test/test.csproj --configuration Release'
            }
        }

        stage('Ejecutar Tests') {
            steps {
                sh 'dotnet test temas/delegacion/csharp/PrinterDelegationExample/test/test.csproj --logger trx;LogFileName=test_results.trx'
            }
        }

        stage('Convertir TRX a JUnit XML') {
            steps {
                sh 'dotnet tool install -g trx2junit || true'
                sh '/root/.dotnet/tools/trx2junit temas/delegacion/csharp/PrinterDelegationExample/test/TestResults/*.trx'
            }
        }

        stage('Publicar resultados de test') {
            steps {
                junit 'temas/delegacion/csharp/PrinterDelegationExample/test/TestResults/*.xml'
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
 - En branch ponemos `*/feature-delegacion`.
 - Por último, en Script Path añadimos `temas/delegacion/csharp/Jenkinsfile`

Y con esta configuración hemos creado el pipeline necesario para la ejecución de los test

### 3. Ejecutar los Tests
Una vez creado el pipeline, ejecutamos dando a `Construir ahora` y el propion Jenkins pasará los test automaticamente.