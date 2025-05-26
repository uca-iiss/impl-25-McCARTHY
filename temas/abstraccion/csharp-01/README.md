# Ejemplo de uso de Abstracción con C#

Este documento describe:

- Cómo hemos implementado un ejemplo de abstracción utilizando C#.
- Batería de pruebas y automatización con Make.
- Cómo se construye una imagen personalizada de Jenkins con c# y se crean los contenedores.
- Ejecución de pruebas automatizadas usando un `Jenkinsfile`.

---

## Análisis de abstracción con C#

La **abstracción** proceso de simplificar la complejidad de un sistema ocultando detalles innecesarios y mostrando solo los aspectos relevantes para el usuario.

En el ejemplo que veremos, hemos implementado un sistema de banco que controla el ingreso y retiro de dinero. Utilizamos abstracción para:

- Ocultar miembros privados al usuario.
- Restringir la modificación de ciertos miembros privados (como el número de cuenta).
- Encapsular el programa en una clase.

---

## Explicación del código en C#

Este proyecto define una clase llamada `CuentaBancaria`, con miembros que actúan como getters o setters, dependiendo del acceso. Se oculta la información que el usuario no necesita saber a través de estos.

---

### Componentes 

### Miembros

```c#
    // Campo privado (ocultación de
    // implementación)
    private decimal saldo;

    // Propiedad inmutable,
    // solo lectura
    public string NumeroCuenta { get; }
```

Utilizamos la ocultación de la información haciendo privado el saldo y permitimos acceder al número de cuenta con solo lectura.

### Métodos

### 'Saldo'

```c#
// Propiedad de solo lectura para acceso uniforme e inmutabilidad
    public decimal Saldo
    {
        // Proporciona acceso
        // al saldo público
        get => saldo;
        // Proporciona acceso de forma
        // privada al saldo para
        // modificarlo
        private set => saldo = value
        >= 0 ? value : throw new 
        ArgumentException("El saldo no 
        puede ser negativo");
    }
```

Método que controla el acceso al saldo de forma privada.

### Constructor

```C#
// Constructor
    public CuentaBancaria(string numeroCuenta, decimal saldoInicial)
    {
        // NumeroCuenta es una propiedad 
        // inmutable, se asigna en el
        // constructor
        // y no puede ser modificada después
        NumeroCuenta = numeroCuenta ?? throw 
        new ArgumentNullException(nameof(numeroCuenta));
        Saldo = saldoInicial;
    }
```

Constructor que inicializa el número de cuenta, sin permitir que se modifique más tarde.

### 'Depositar'


```C#
// Método público (abstracción)
    public void Depositar(decimal cantidad)
    {
        if (cantidad <= 0)
            throw new ArgumentException("La cantidad debe ser positiva");

        Saldo += cantidad;
        Console.WriteLine($"Se han 
        depositado {cantidad:C} en la
        cuenta {NumeroCuenta}. Saldo 
        actual: {Saldo:C}");
    }
```

Método que deposita la cantidad sin que el usuario sepa como está implementado.

### 'Retirar'

```C#
// Método público (abstracción)
    public void Retirar(decimal cantidad)
    {
        if (cantidad <= 0)
            throw new ArgumentException("La cantidad debe ser positiva");
        if (cantidad > Saldo)
            throw new InvalidOperationException("Saldo insuficiente");

        Saldo -= cantidad;
        Console.WriteLine($"Se han 
        retirado {cantidad:C} de la 
        cuenta {NumeroCuenta}. Saldo 
        actual: {Saldo:C}");
    }
```

Parecida a la anterior pero para retirar saldo.

## Ventajas de la abstracción

1. **Reducción de la complejidad:** Permite simplificar problemas complejos al enfocarse en los aspectos más importantes, ocultando los detalles de implementación innecesarios.

2. **Reutilización de código:** Facilita la creación de componentes modulares y reutilizables, mejorando la eficiencia en el desarrollo.

3. **Mantenimiento más sencillo:** Hace que el código sea más fácil de leer, entender y mantener, ya que se centra en lo esencial.

---

Ahora pasaremos a ver el programa de pruebas implementada en un proyecto para pruebas llamado Bancoapp.Tests, utilizando Xunit.

---

## Casos de prueba

### 1. 'Inicialización constructor'

```C#
[Fact]
public void Constructor_DeberiaInicializarSaldoYNumeroCuenta()
{
    // Arrange
    string numeroCuenta = "1234567890";
    decimal saldoInicial = 500m;

    // Act
    var cuenta = new CuentaBancaria(numeroCuenta, saldoInicial);

    // Assert
    Assert.Equal(numeroCuenta, cuenta.NumeroCuenta);
    Assert.Equal(saldoInicial, cuenta.Saldo);
}
```

- El constructor inicializa saldo y número
- Comprobación que el constructor lo inicializa correctamente

---

### 2. 'Depositar saldo'

```c#
[Fact]
public void Depositar_DeberiaAumentarSaldo()
{
    // Arrange
    var cuenta = new CuentaBancaria("1234567890", 500m);
    decimal montoDeposito = 200m;
    decimal saldoEsperado = 700m;

    // Act
    cuenta.Depositar(montoDeposito);

    // Assert
    Assert.Equal(saldoEsperado, cuenta.Saldo);
}

```

- Se deposita una cantidad de 200.
- Comprobar la cantidad de 700 en la cuenta.

---

### 3. 'Retirar saldo'

```c#
[Fact]
public void Retirar_DeberiaReducirSaldo()
{
    // Arrange
    var cuenta = new 
    CuentaBancaria("1234567890", 500m);
    decimal montoRetiro = 200m;
    decimal saldoEsperado = 300m;

    // Act
    cuenta.Retirar(montoRetiro);

    // Assert
    Assert.Equal(saldoEsperado, 
    cuenta.Saldo);
}
```

- Se intenta reducir el saldo.
- Verificar que el saldo ha sido reducido a 300.

---

### 4. 'Depositar cantidad negativa'

```C#
[Fact]
public void Depositar_CantidadNegativa_DeberiaLanzarExcepcion()
{
    // Arrange
    var cuenta = new CuentaBancaria("1234567890", 
    500m);
    decimal montoDeposito = -100m;

    // Act & Assert
    Assert.Throws<ArgumentException>(()
    => cuenta.Depositar(montoDeposito));
}
```
- Se realiza un intento de depósito negativo.
- Confirmar que expulsa una excepción.

---

### 5. 'Número de cuenta no debería ser accesible para modificación'
``` c#
[Fact]
public void NumeroCuenta_NoDebeTenerSetterPublico()
{
    // Arrange
    var propiedad = typeof(CuentaBancaria).GetProperty
    ("NumeroCuenta");

    // Act
    var tieneSetter = propiedad?.SetMethod != null && propiedad.SetMethod.IsPublic;

    // Assert
    Assert.False(tieneSetter, "La propiedad NumeroCuenta no debe tener un setter público.");
}
```

- Se intenta modificar un atributo de solo lectura.
- Comprobar que no tiene acceso. 

---

### 6. 'Saldo no de debería ser accesible para modificación'

``` C#
[Fact]
public void Saldo_NoDebeTenerSetterPublico()
{
    // Arrange
    var propiedad = typeof(CuentaBancaria).GetProperty("Saldo");

    // Act
    var tieneSetter = propiedad?.SetMethod != null && propiedad.SetMethod.IsPublic;

    // Assert
    Assert.False(tieneSetter, "La propiedad Saldo no debe tener un setter público.");
}
```

- Parecido al anterior, pero para el atributo saldo.

---

Este programa sirve como un banco de pruebas básico para:

- Confirmar el correcto uso de la abstracción.
- Verificar comportamientos específicos de nuestro programa.

---

## Make

Para la realización de pruebas automáticas en C#, hemos utilizado Make para compilar, testear y ejecutar.
Por ello, hemos tenido que primero de todo crear un archivo Makefile que se encargará de ejecutar los comandos para cada fase.

```make
# Definir el nombre del proyecto y la carpeta de pruebas
PROJECT_NAME = Bancoapp
TEST_PROJECT = Bancoapp.Tests
BUILD_DIR = bin/Debug/net8.0

# Comandos
.PHONY: all build clean test

all: clean build test

build:
	dotnet restore $(PROJECT_NAME)
	dotnet build $(PROJECT_NAME)

test:
	dotnet test $(TEST_PROJECT) --no-build --verbosity normal

clean:
	dotnet clean $(PROJECT_NAME)

```

Con ello, podremos ejecutar dentro de la raiz del directorio donde se encuentra nuestro Makefile:

- make build            # Se encargará de compilar el proyecto
- make test             # Se encargará de realizar los tests
- make clean            # Limpia recursos del proyecto
- make all              # Hace todo lo anterior

---

Ahora pasaremos con la instalación de los contenedores de Jenkins con C#


## Dockerfile: Jenkins con C#

```dockerfile
FROM jenkins/jenkins:lts

# Cambiar a usuario root para instalar dependencias del sistema
USER root

# Instalar dependencias necesarias para Ruby y compilación de gemas
RUN apt-get update && \
    apt-get install -y \ 
        wget \
        build-essential \
        git &&\
    wget https://packages.microsoft.com/config/debian/11/packages-microsoft-prod.deb -O packages-microsoft-prod.deb && \
    dpkg -i packages-microsoft-prod.deb && \
    rm packages-microsoft-prod.deb && \
    apt-get update && \
    apt-get install -y dotnet-sdk-8.0 dotnet-runtime-8.0 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Comprobar versiones instaladas
RUN dotnet --version && make --version && git --version

# Volver al usuario Jenkins
USER jenkins
```

- Parte de la imagen oficial 'jenkins/jenkins:lts'.
- Instala build-essential (que contiene Make) y herramientas de desarrollo necesarias.
- Verificamos la instalación de dotnet, make y git en nuestor sistema.
- Vuelve al usuario 'jenkins' para mayor seguridad al ejecutar procesos.

---

## Creación del contenedor Jenkins personalizado

### 1. Construir la imagen

```bash
docker build -t my-jenkins-image .
```

### 2. Crear y ejecutar el contenedor mediante Terraform

Mediante un main.tf el cual incluirá todo lo necesario para poder proceder con la instalación de los contenedores de Jenkins, podremos tener los contenedores funcionales y operativos colocando los comandos de **Terraform Init** y **Terraform Apply**

terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {
  host = "npipe:////./pipe/docker_engine"
}

# Crear una red Docker para Jenkins y DinD
resource "docker_network" "ci_network" {
  name = "ci_network"
}

# Imagen Docker-in-Docker
resource "docker_image" "dind" {
  name = "docker:dind"
}

# Contenedor Docker-in-Docker
resource "docker_container" "dind" {
  image = docker_image.dind.image_id
  name  = "dind"
  privileged = true
  restart    = "always"

  env = [
    "DOCKER_TLS_CERTDIR="
  ]

  networks_advanced {
    name = docker_network.ci_network.name
  }

  ports {
    internal = 2375
    external = 2375
  }
}

# Contenedor Jenkins usando la imagen personalizada creada previamente
resource "docker_container" "jenkins" {
  image   = "my-jenkins-image"  # Usamos la imagen creada manualmente
  name    = "jenkins"
  restart = "always"

  ports {
    internal = 8080
    external = 8080
  }

  networks_advanced {
    name = docker_network.ci_network.name
  }

  # Esto permite que Jenkins use Docker del host
  volumes {
    host_path      = "/var/run/docker.sock"
    container_path = "/var/run/docker.sock"
  }
}

Una vez ejecutado, ahora ya tendremos los contenedores de Jenkins funcionales, ahora procederemos a explicar el JenkinsFile escogido para el lanzamiento de la aplicación mediante un pipeline.


## Jenkinsfile para CI/CD en C# y Make

```groovy
pipeline {
    agent any

    stages {
        stage('Preparar entorno') {
            steps {
                echo 'Preparando entorno C#...'
            }
        }

        stage('Compilar') {
            steps {
                echo 'Compilando proyecto...'
                dir('temas/abstraccion/csharp-01') {
                    sh 'make build'
                }
            }
        }

        stage('Ejecutar pruebas') {
            steps {
                echo 'Ejecutando pruebas...'
                dir('temas/abstraccion/csharp-01/Bancoapp.Tests') {
                    sh 'dotnet test'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado.'
        }
        failure {
            echo 'La ejecución falló.'
        }
        success {
            echo 'La ejecución fue exitosa.'
        }
    }
}

```

'Compilar' Se encarga de ejecutar make build. 
'Ejecutar pruebas' Se encarga de realizar los tests con dotnet. 
'post'  Define acciones según el resultado: éxito o fallo. 

Este setup permite:

- Ejecutar Jenkins en Docker con dotnet y make preinstalado.
- Correr pruebas automatizadas de C# usando `Make`.
- Integrar pruebas a pipelines CI/CD de forma simple y portable.

---
