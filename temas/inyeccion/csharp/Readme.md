# Inyecion en C#

## Introducción
Bienvenido al repositorio sobre Inyeccion en C#. Este repositorio demuestra la implementación de la inyección de dependencias en C#, una técnica esencial en el desarrollo de software que mejora la modularidad y la testabilidad del código.

## EStructura de Directorio

- `/README.md`: Archivo actual.
- `/main.cs`: Archivo C# donde se encuentra el codigo de las clases sobre inyeccion.
- `/main.csproj`: archivo de configuración esencial en cualquier proyecto de C# basado en .NET. Define cómo debe compilarse y ejecutarse el archivo main.cs, así como sus dependencias, el framework objetivo y otros parámetros clave.
- `/test_csharp.cs`: Archivo C# donde se encuentra el codigo de los test
- `/test.csproj`: archivo de configuración esencial en cualquier proyecto de C# basado en .NET. Define cómo debe compilarse y ejecutarse el archivo test.cs, así como sus dependencias, el framework objetivo y otros parámetros clave.


## Conceptos Básicos
La inyección de dependencias es un patrón de diseño que permite inyectar objetos dependientes en una clase en lugar de que la clase los cree directamente. Esto promueve el principio de inversión de dependencias, facilitando pruebas, mantenimiento y extensión.

### Tipo Comunes
- ```Constructor Injection:``` Las dependencias se pasan a través del constructor.
- ```Property Injection:``` Las dependencias se establecen mediante propiedades públicas.
- ```Method Injection:``` Se suministran como argumentos de método.

### Contenedor de Inyección de Dependencias
En el C# es común usar un contenedor de inyección de dependencias, el cual se encarga de instanciar y administrar las dependencias. Este contenedor se configura al arrancar la aplicación, definiendo cómo crear cada tipo de objeto y cómo resolver sus dependencias.

### Registro de Dependencias
El registro de dependencias se realiza en el contenedor de DI mediante la definición de interfaces y sus implementaciones concretas. Esto incluye decidir el ciclo de vida de estos objetos, como transientes, de alcance o singletones:
- ```Transientes:``` Una nueva instancia cada vez que se solicita.
- ```Alcance:``` Una instancia única por solicitud.
- ```Singletones:``` Una instancia única para toda la aplicación.

### Resolución de Dependencias
Una vez que todo está configurado, el contenedor puede resolver automáticamente cualquier objeto que se necesite, creando y encadenando las dependencias requeridas de forma recursiva.

### Ventajas de la Inyección de Dependencias
- ```Menor acoplamiento:``` Las clases son independientes entre sí, lo que permite cambios más fáciles.

- ```Mejor capacidad de prueba:``` Facilita las pruebas unitarias al permitir reemplazar las dependencias reales por simulaciones (mocks).

- ```Configuración centralizada:``` Las dependencias se definen en un solo lugar, lo cual mejora la organización del código.

- ```Mayor flexibilidad:``` Cambiar implementaciones es sencillo sin modificar las clases consumidoras.

### Frameworks de DI en C#
-```  Microsoft.Extensions.DependencyInjection:```  La implementación más común utilizada en aplicaciones .NET, especialmente en ASP.NET Core.
- ``` Autofac:```  Un poderoso contenedor de inyección de dependencias que ofrece funcionalidades avanzadas.
- ``` Ninject:```  Un contenedor que se enfoca en ser fácil de usar y configurar. Cada uno de estos frameworks de DI proporciona diferentes capacidades y formas de configurar y manejar la inyección de dependencias, permitiendo a los desarrolladores elegir la herramienta que mejor se adapte a sus necesidades específicas.

La correcta implementación de la inyección de dependencias en C# puede llevar a una arquitectura de software significativamente más robusta, facilitando la gestión de dependencias y aumentando la escalabilidad y la mantenibilidad del código.

## Código de Ejemplo
Este proyecto proporciona un enfoque práctico y funcional para implementar la inyección de dependencias en C# utilizando el contenedor de servicios `Microsoft.Extensions.DependencyInjection`. A través del código de ejemplo, exploramos cómo configurar y utilizar un sistema de inyección de dependencias para mejorar la escalabilidad, la prueba y el mantenimiento de las aplicaciones .NET.

[**main.cs**](./inyeccion_csharp/main.cs)

```csharp
using Microsoft.Extensions.DependencyInjection;
using System;

public interface IWeatherService
{
    double GetTemperature(string city);
}

public interface IAlertService
{
    void SendAlert(string message);
}

public interface IWeatherLogger
{
    void Log(string message);
}

public interface ICityWeatherMonitor
{
    void MonitorCity(string city);
}

public class FakeWeatherService : IWeatherService
{
    public double GetTemperature(string city)
    {
        return city.ToLower().Contains("desert") ? 42.0 : 21.5;
    }
}

public class EmailAlertService : IAlertService
{
    public void SendAlert(string message)
    {
        Console.WriteLine($"[EMAIL ALERT] {message}");
    }
}

public class PushAlertService : IAlertService
{
    public void SendAlert(string message)
    {
        Console.WriteLine($"[PUSH ALERT] {message}");
    }
}
 
 public class ConsoleWeatherLogger : IWeatherLogger
{
    public void Log(string message)
    {
        Console.WriteLine($"[LOG] {message}");
    }
}

public class CityWeatherMonitor : ICityWeatherMonitor
{
    private readonly IWeatherService _weatherService;
    private readonly IAlertService _alertService;
    private readonly IWeatherLogger _logger;

    public CityWeatherMonitor(IWeatherService weatherService, IAlertService alertService, IWeatherLogger logger)
    {
        _weatherService = weatherService;
        _alertService = alertService;
        _logger = logger;
    }

    public void MonitorCity(string city)
    {
        try
        {
            _logger.Log($"Consultando temperatura para {city}...");
            double temp = _weatherService.GetTemperature(city);
            _logger.Log($"Temperatura en {city}: {temp}°C");

            if (temp > 35)
            {
                _alertService.SendAlert($"¡Alerta de calor en {city}! Temperatura: {temp}°C");
            }

            _logger.Log($"Monitoreo completado para {city}.");
        }
        catch (Exception ex)
        {
            _logger.Log($"Error al monitorear {city}: {ex.Message}");
            throw;
        }
    }
}


public class Program
{
    public static void Main(string[] args)
    {
        var services = new ServiceCollection();
        ConfigureServices(services, args);

        var provider = services.BuildServiceProvider();
        var monitor = provider.GetService<ICityWeatherMonitor>();
        monitor?.MonitorCity("Desert City");
    }

    private static void ConfigureServices(IServiceCollection services, string[] args)
    {
        services.AddSingleton<IWeatherService, FakeWeatherService>();
        services.AddSingleton<IWeatherLogger, ConsoleWeatherLogger>();

        if (args.Length > 0 && args[0].ToLower() == "push")
        {
            services.AddTransient<IAlertService, PushAlertService>();
        }
        else
        {
            services.AddTransient<IAlertService, EmailAlertService>();
        }

        services.AddTransient<ICityWeatherMonitor, CityWeatherMonitor>();
    }
}
```
### Metodo: `ConfigureServices`:
Este método centraliza la configuración de servicios y registra todas las dependencias necesarias para la aplicación.

```C#
    private static void ConfigureServices(IServiceCollection services, string[] args)
    {
        services.AddSingleton<IWeatherService, FakeWeatherService>();
        services.AddSingleton<IWeatherLogger, ConsoleWeatherLogger>();

        if (args.Length > 0 && args[0].ToLower() == "push")
        {
            services.AddTransient<IAlertService, PushAlertService>();
        }
        else
        {
            services.AddTransient<IAlertService, EmailAlertService>();
        }

        services.AddTransient<ICityWeatherMonitor, CityWeatherMonitor>();
    }
```

-  Este método permite cambiar la implementación del servicio de alertas sin modificar el código de monitoreo, simplemente con un argumento en tiempo de ejecución.

### Metodo: `MonitorCity`:
Este método muestra cómo utilizar las dependencias inyectadas para monitorear condiciones climáticas y enviar alertas cuando se detectan temperaturas altas.

```C#
     public void MonitorCity(string city)
    {
        try
        {
            _logger.Log($"Consultando temperatura para {city}...");
            double temp = _weatherService.GetTemperature(city);
            _logger.Log($"Temperatura en {city}: {temp}°C");

            if (temp > 35)
            {
                _alertService.SendAlert($"¡Alerta de calor en {city}! Temperatura: {temp}°C");
            }

            _logger.Log($"Monitoreo completado para {city}.");
        }
        catch (Exception ex)
        {
            _logger.Log($"Error al monitorear {city}: {ex.Message}");
            throw;
        }
    }
```
#### Interfaces Definidas
##### 1. IWeatherService: 
Define un contrato para obtener temperaturas.

- Método: `double GetTemperature(string city);`

##### 2. IAlertService:
Contrato para servicios de alerta.

- Método: `void SendAlert(string message);`

##### 3. IWeatherLogger:
Interfaz para registrar eventos.

- Método: `void Log(string message);`

##### 4. ICityWeatherMonitor:
Define el comportamiento de un monitor climático por ciudad.

- Método: `void MonitorCity(string city);`

#### Implementaciones de las Interfaces
##### FakeWeatherService (IWeatherService)
- Simula el acceso a datos meteorológicos.
```csharp
public class FakeWeatherService : IWeatherService
{
    public double GetTemperature(string city)
    {
        return city.ToLower().Contains("desert") ? 42.0 : 21.5;
    }
}
```

##### EmailAlertService / 📱 PushAlertService (IAlertService):
- Envían alertas simuladas por distintos canales.

```csharp
public class EmailAlertService : IAlertService
{
    public void SendAlert(string message)
    {
        Console.WriteLine($"[EMAIL ALERT] {message}");
    }
}

public class PushAlertService : IAlertService
{
    public void SendAlert(string message)
    {
        Console.WriteLine($"[PUSH ALERT] {message}");
    }
}
```

##### ConsoleWeatherLogger (IWeatherLogger):
- Proporciona un mecanismo para registrar mensajes en la consola, facilitando el seguimiento de eventos y errores.

```csharp
 public class ConsoleWeatherLogger : IWeatherLogger
{
    public void Log(string message)
    {
        Console.WriteLine($"[LOG] {message}");
    }
}
```
##### Contribuciones al Proceso de Inyección de Dependencias
Las interfaces y sus implementaciones en este proyecto juegan un papel clave en la estructura de inyección de dependencias, permitiendo una separación clara de responsabilidades y una mayor flexibilidad en el desarrollo. Al definir comportamientos mediante interfaces como IWeatherService, IAlertService, IWeatherLogger e ICityWeatherMonitor, se desacopla la lógica de negocio de las implementaciones específicas, facilitando el reemplazo o modificación de estas sin afectar el resto del sistema.

Por ejemplo, si se desea cambiar el medio por el cual se envían las alertas —de correo electrónico a notificaciones push—, solo es necesario cambiar la implementación registrada de IAlertService en el contenedor, sin necesidad de alterar la lógica de monitoreo en CityWeatherMonitor. De forma similar, si más adelante se desea usar un servicio meteorológico real en lugar de FakeWeatherService, se puede integrar sin afectar el resto de la aplicación.

Este patrón es gestionado a través de IServiceCollection, donde se registran las dependencias que luego son resueltas automáticamente por el contenedor al momento de construir las instancias. En este proyecto, el método ConfigureServices permite decidir dinámicamente qué implementación de IAlertService utilizar, dependiendo de los argumentos de entrada (args), lo que demuestra cómo la inyección de dependencias puede adaptarse al contexto de ejecución sin cambios en el código fuente principal.

Este enfoque no solo promueve un código más limpio y organizado, sino que también mejora significativamente la capacidad de realizar pruebas unitarias, ya que las dependencias reales pueden ser fácilmente reemplazadas por versiones mock o fake durante las pruebas.

### Metodo Main
Actuando como el punto de entrada del programa, este método configura el contenedor de servicios y resuelve las dependencias necesarias para ejecutar la lógica de procesamiento de pedidos. 

```csharp
public static void Main(string[] args)
    {
        var services = new ServiceCollection();
        ConfigureServices(services, args);

        var provider = services.BuildServiceProvider();
        var monitor = provider.GetService<ICityWeatherMonitor>();
        monitor?.MonitorCity("Desert City");
    }
```

## Código de Test
Ahora, se muestra unos tests para probar el correcto del ejemplo:

[**test_csharp.cs**](./test_csharp/test_csharp.cs)
```csharp
using Moq;
using NUnit.Framework;
using System;

namespace Tests_Clima
{
    public class WeatherMonitorTests
    {
        private Mock<IWeatherService> mockWeatherService;
        private Mock<IAlertService> mockAlertService;
        private Mock<IWeatherLogger> mockLogger;
        private CityWeatherMonitor monitor;

        [SetUp]
        public void Setup()
        {
            mockWeatherService = new Mock<IWeatherService>();
            mockAlertService = new Mock<IAlertService>();
            mockLogger = new Mock<IWeatherLogger>();

            monitor = new CityWeatherMonitor(mockWeatherService.Object, mockAlertService.Object, mockLogger.Object);

            mockLogger.Setup(l => l.Log(It.IsAny<string>()))
                      .Callback<string>(msg => Console.WriteLine($"[MockLog] {msg}"));
        }

        [Test]
        public void MonitorCity_CallsGetTemperature()
        {
            string city = "TestCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(25.0);

            monitor.MonitorCity(city);

            mockWeatherService.Verify(w => w.GetTemperature(city), Times.Once);
        }

        [Test]
        public void MonitorCity_LogsAllMessages()
        {
            string city = "LogCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(20.0);

            monitor.MonitorCity(city);

            mockLogger.Verify(l => l.Log(It.Is<string>(s => s.Contains("Consultando temperatura"))), Times.Once);
            mockLogger.Verify(l => l.Log(It.Is<string>(s => s.Contains("Monitoreo completado"))), Times.Once);
        }

        [Test]
        public void MonitorCity_SendsHeatAlert_WhenTemperatureHigh()
        {
            string city = "HotCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(40.0);

            monitor.MonitorCity(city);

            mockAlertService.Verify(a => a.SendAlert(It.Is<string>(s => s.Contains("¡Alerta de calor"))), Times.Once);
        }

        [Test]
        public void MonitorCity_WhenAlertServiceFails_LogsError()
        {
            string city = "FailCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(50.0);
            mockAlertService.Setup(a => a.SendAlert(It.IsAny<string>())).Throws(new Exception("Error en alerta"));

            Assert.Throws<Exception>(() => monitor.MonitorCity(city));

            mockLogger.Verify(l => l.Log(It.Is<string>(s => s.Contains("Error al monitorear"))), Times.Once);
        }
    }
}

```

### Setup Inicial
Este método se ejecuta antes de cada test. Se crean objetos simulados (mocks) para cada dependencia que requiere CityWeatherMonitor. Estos mocks permiten definir comportamientos predecibles y verificar interacciones sin necesidad de usar las implementaciones reales. El Callback en el logger sirve para ver los mensajes simulados de registro durante la ejecución de los tests.

```csharp
public void Setup()
        {
            mockWeatherService = new Mock<IWeatherService>();
            mockAlertService = new Mock<IAlertService>();
            mockLogger = new Mock<IWeatherLogger>();

            monitor = new CityWeatherMonitor(mockWeatherService.Object, mockAlertService.Object, mockLogger.Object);

            mockLogger.Setup(l => l.Log(It.IsAny<string>()))
                      .Callback<string>(msg => Console.WriteLine($"[MockLog] {msg}"));
        }
```

### Test: ```MonitorCity_CallsGetTemperature```
EL objetivo es asegurar que el monitor efectivamente llama al servicio climático para obtener la temperatura de la ciudad. Verifica la dependencia fundamental entre el monitor y el servicio climático.

```csharp
[Test]
        public void MonitorCity_CallsGetTemperature()
        {
            string city = "TestCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(25.0);

            monitor.MonitorCity(city);

            mockWeatherService.Verify(w => w.GetTemperature(city), Times.Once);
        }
```

### Test: ```MonitorCity_CallsGetTemperature```
EL objetivo es asegurar que el monitor efectivamente llama al servicio climático para obtener la temperatura de la ciudad. Verifica la dependencia fundamental entre el monitor y el servicio climático.

```csharp
[Test]
        public void MonitorCity_CallsGetTemperature()
        {
            string city = "TestCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(25.0);

            monitor.MonitorCity(city);

            mockWeatherService.Verify(w => w.GetTemperature(city), Times.Once);
        }
```

### Test: ```MonitorCity_LogsAllMessages```
EL objetivo verificar que el sistema registra correctamente los eventos clave del monitoreo. Asegura trazabilidad y seguimiento, especialmente útil para diagnóstico y auditoría.

```csharp
[Test]
        public void MonitorCity_LogsAllMessages()
        {
            string city = "LogCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(20.0);

            monitor.MonitorCity(city);

            mockLogger.Verify(l => l.Log(It.Is<string>(s => s.Contains("Consultando temperatura"))), Times.Once);
            mockLogger.Verify(l => l.Log(It.Is<string>(s => s.Contains("Monitoreo completado"))), Times.Once);
        }
```

### Test: ```MonitorCity_SendsHeatAlert_WhenTemperatureHigh```
EL objetivo es comprobar que el sistema emite una alerta cuando la temperatura supera los 35°C. Valida el comportamiento de seguridad y respuesta ante condiciones críticas.

```csharp
 [Test]
        public void MonitorCity_SendsHeatAlert_WhenTemperatureHigh()
        {
            string city = "HotCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(40.0);

            monitor.MonitorCity(city);

            mockAlertService.Verify(a => a.SendAlert(It.Is<string>(s => s.Contains("¡Alerta de calor"))), Times.Once);
        }

```

### Test: ```MonitorCity_WhenAlertServiceFails_LogsError```
EL objetivo es validar que si ocurre un error durante el envío de alertas, este se registra correctamente y la excepción es manejada. Mejora la resiliencia del sistema ante fallos inesperados y permite una respuesta informada.

```csharp
 [Test]
        public void MonitorCity_WhenAlertServiceFails_LogsError()
        {
            string city = "FailCity";
            mockWeatherService.Setup(w => w.GetTemperature(city)).Returns(50.0);
            mockAlertService.Setup(a => a.SendAlert(It.IsAny<string>())).Throws(new Exception("Error en alerta"));

            Assert.Throws<Exception>(() => monitor.MonitorCity(city));

            mockLogger.Verify(l => l.Log(It.Is<string>(s => s.Contains("Error al monitorear"))), Times.Once);
        }
```

Los tests aseguran que cada componente del sistema se comporta como se espera cuando se ejecuta de forma aislada. Gracias a la inyección de dependencias, es posible sustituir servicios reales por versiones simuladas, facilitando pruebas rápidas y precisas. Este enfoque fomenta una arquitectura desacoplada, mantenible y preparada para cambios futuros.

## Ejecución Test
Para ejecutar el código y pasar los test de dicho código, realiza los siguientes pasos detallados que incluyen la creación de un Jenkinsfile, creación pipeline y ejecución del pipeline

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
                sh 'dotnet restore temas/inyeccion/csharp/inyeccion_csharp/main.csproj'
                sh 'dotnet restore temas/inyeccion/csharp/test_csharp/test.csproj'
            }
        }

        stage('Compilar') {
            steps {
                sh 'dotnet build temas/inyeccion/csharp/inyeccion_csharp/main.csproj --configuration Release'
                sh 'dotnet build temas/inyeccion/csharp/test_csharp/test.csproj --configuration Release'
            }
        }

        stage('Ejecutar Tests') {
            steps {
                sh 'dotnet test temas/inyeccion/csharp/test_csharp/test.csproj --logger trx;LogFileName=test_results.trx'
            }
        }

        stage('Convertir TRX a JUnit XML') {
            steps {
                // Instala trx2junit si no está disponible
                sh 'dotnet tool install -g trx2junit || true'

                sh '/root/.dotnet/tools/trx2junit temas/inyeccion/csharp/test_csharp/TestResults/*.trx'

            }
        }

        stage('Publicar resultados de test') {
            steps {
                junit 'temas/inyeccion/csharp/test_csharp/TestResults/*.xml'
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
 - En branch ponemos `*/feature-inyeccion`.
 - Por último, en Script Path añadimos `temas/inyeccion/csharp/Jenkinsfile`

Y con esta configuración hemos creado el pipeline necesario para la ejecución de los test

### 3. Ejecutar los Tests
Una vez creado el pipeline, ejecutamos dando a `Construir ahora` y el propio Jenkins pasará los test automaticamente.
