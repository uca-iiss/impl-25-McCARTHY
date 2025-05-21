# Ejemplo de Errores en Scala

Este documento describe:

- Cómo hemos implementado un ejemplo de errores en Scala.
- Batería de pruebas y automatización con SBT.
- Cómo se construye una imagen personalizada de Jenkins con Java y se crean los contenedores.
- Ejecución de pruebas automatizadas usando un `Jenkinsfile`.

---

## Análisis de programación orientada a aspectos en Java

En **Scala**, el tratamiento de errores puede abordarse de forma expresiva y funcional mediante estructuras como Try, Option, Either, o mediante excepciones tradicionales.

En nuestro ejemplo, hemos simulado un sistema de dispositivos inteligentes que puede fallar ante determinadas condiciones (por ejemplo, parámetros inválidos o estados no permitidos). Para capturar y manejar estos errores, utilizamos principalmente la clase Try, que permite envolver ejecuciones que podrían lanzar excepciones, y trabajar con los resultados de forma segura.

---

## Explicación del código en Java con AspectJ

El proyecto define varias clases como 'Luz', 'Termostato', y 'SmartLock', y funciones que modelan sus comportamientos. Los posibles errores como acceso no autorizado, valores fuera de rango o fallos de ejecución se gestionan con 'Try' y 'match', de forma que no haya necesidad de capturar excepciones con bloques 'try-catch' tradicionales.
---

### Componentes 

### 'Servicio de Reparación'

```scala
object ReparacionService {
    def procesarOrden(orden: Orden): Either[String, Resultado] = {
      for {
        dispositivo <- orden.dispositivo.toRight("Error: No hay dispositivo en la orden")
        diagnostico <- dispositivo.diagnostico.toRight("Error: No se ha realizado diagnostico")
        _ <- if (diagnostico.irreparable) Left("Error: Dispositivo irreparable") else Right(())
      } yield Resultado(s"Reparacion iniciada para ${dispositivo.marca} ${dispositivo.modelo}")
    }
  }
```

Clase que representa una luz inteligente con métodos para encender y apagar.

### 'Domain'

```scala
case class Cliente(nombre: String)
case class Diagnostico(problema: String, irreparable: Boolean)
case class Dispositivo(marca: String, modelo: String, diagnostico: Option[Diagnostico])
case class Orden(cliente: Cliente, dispositivo: Option[Dispositivo])
case class Resultado(mensaje: String)
```

Dispositivo que controla la temperatura, con método para ajustar la temperatura dentro de un rango seguro.

---

## Ventajas del enfoque funcional en el manejo de errores en Scala

1. **Seguridad en tiempo de compilación**: Al usar tipos como Try, Option o Either, se fuerza al programador a considerar explícitamente los posibles errores, lo que reduce errores de tiempo de ejecución.

2. **Código más limpio y declarativo**: En lugar de usar bloques try-catch, el uso de Try permite componer operaciones de forma fluida y más legible..

3. **Encadenamiento de operaciones**: LEs posible encadenar múltiples operaciones con map, flatMap y recover, facilitando el manejo de errores en pipelines de transformación.

4. **Separación de lógica y errores**: El flujo principal de la lógica del programa no se ve interrumpido por detalles de manejo de errores, que se tratan de manera centralizada o localizada.

---

## Estructura de manejo de aspectos en el proyecto

- Las operaciones potencialmente fallidas están encapsuladas en 'Try' para evitar fallos en tiempo de ejecución.
- Se validan los estados antes de realizar operaciones críticas, devolviendo 'Failure' en caso de error.
- El flujo principal del programa puede continuar operando sobre los resultados de forma segura usando 'match', 'getOrElse', o 'recover'.

--- 

Ahora pasaremos a ver el programa de pruebas que hemos utilizado en nuestra clase Main.Scala que ejecuta distintos escenarios de prueba manuales usando varias instancias de pedido.

---

## Casos de prueba

### SetUp

Antes de las pruebas, se crean varias instancias necesarias para simular diferentes escenarios

```Scala
  val cliente = Cliente("Alejandro Salvador") // Creamos un cliente
  val diagnosticoOk = Diagnostico("Pantalla rota", irreparable = false)  // Un diagnóstico reparable
  val diagnosticoMalo = Diagnostico("Placa base quemada", irreparable = true)  // Un diagnóstico irreparable
  val dispositivoConDiagnostico = Dispositivo("Samsung", "Galaxy S21", Some(diagnosticoOk))  // Tiene un diagnóstico reparable
  val dispositivoIrreparable = Dispositivo("LG", "K40", Some(diagnosticoMalo))  // Tiene un diagnóstico irreparable
  val dispositivoSinDiagnostico = Dispositivo("Motorola", "Moto G", None)  // No tiene diagnóstico (None)

  val ordenValida = Orden(cliente, Some(dispositivoConDiagnostico))  // Incluye un diagnóstico reparable
  val ordenSinDiagnostico = Orden(cliente, Some(dispositivoSinDiagnostico))  // Incluye un dispositio pero sin diagnóstico
  val ordenIrreparable = Orden(cliente, Some(dispositivoIrreparable))  // Incluye un dispositivo con diagnóstico irreparable
  val ordenSinDispositivo = Orden(cliente, None)  // No incluye dispositivo (None)
```

- Metodo anotado con @BeforeEach, lo que significa que se ejecuta automáticamente antes de cada prueba.
- Crear nuevas instancias de los objetos que vamos a probar, asegurando que cada prueba del test comience limpia e independiente

### 1. 'EProcesar orden valida'

```Scala
  test("Procesar orden valida debe ser exito") {
    val resultado = ReparacionService.procesarOrden(ordenValida)
    assert(resultado.isRight)
  }
```

- Verifica que una orden con todos los datos correctos y diagnóstico reparable se procesa exitosamente.
- Se espera un 'Right' -> La operación fue exitosa.

---

### 2. 'Procesar orden sin diagnostico'

```Scala
  test("Procesar orden sin diagnostico debe ser error") {
    val resultado = ReparacionService.procesarOrden(ordenSinDiagnostico)
    assert(resultado.isLeft)
  }
```

- Verifica que una orden con dispositivo pero sin diagnóstico sea rechazada.
- Se espera un 'Left' -> Indica un error al procesar.

---

### 3. 'Procesar orden irreparable'

```Scala
  test("Procesar orden irreparable debe ser error") {
    val resultado = ReparacionService.procesarOrden(ordenIrreparable)
    assert(resultado.isLeft)
  }
```

- Verifica que una orden con diagnóstico marcado como irreparable sea rechazada.
- Se espera un 'Left' -> Indica un error al procesar

---

### 4. 'Procesar orden sin dispositivo'

```Scala
  test("Procesar orden sin dispositivo debe ser error") {
    val resultado = ReparacionService.procesarOrden(ordenSinDispositivo)
    assert(resultado.isLeft)
  }
```

- Verifica que una orden sin dispositivo no sea aceptada.
- Se espera un 'Left' -> Indica un error al procesar

---

Este programa sirve como un banco de pruebas básico para:

- Solo procesa órdenes completas y válidas.
- Rechaza correctamente los casos con información incompleta o inválida.
- No depende del uso de 'Null', utilizando 'Option' y 'Either' para el manejo seguro de datos y errores.

---

## Build.sbt y SBT

Utilizamos SBT (Scala Build Tool) para compilar, testear y ejecutar nuestros programas Scala.

name := "scala"

version := "0.1"

scalaVersion := "2.13.13"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.17" % Test
)

---

- sbt compile: Compila el proyecto

- sbt test: Ejecuta todos los tests definidos con ScalaTest

- sbt run: Ejecutariamos Main.Scala (si lo definieramos como objeto def main()).

---

Ahora pasaremos con la instalación de los contenedores de Jenkins con Java


## Dockerfile: Jenkins con Scala

```dockerfile
FROM jenkins/jenkins:lts

USER root

ENV DEBIAN_FRONTEND=noninteractive

# Instalar Java y herramientas necesarias
RUN apt-get update && apt-get install -y \
    default-jdk \
    curl \
    zip \
    unzip

USER jenkins

# Define explícitamente HOME y usa SHELL bash
ENV HOME=/var/jenkins_home
SHELL ["/bin/bash", "-c"]

# Instalar SDKMAN, sbt y scala
RUN curl -s "https://get.sdkman.io" | bash && \
    source "$HOME/.sdkman/bin/sdkman-init.sh" && \
    sdk install sbt && \
    sdk install scala

# Agregar sbt y scala al PATH
ENV PATH="$HOME/.sdkman/candidates/sbt/current/bin:$HOME/.sdkman/candidates/scala/current/bin:$PATH"

# Verificar versiones
RUN java -version && sbt --version && scala -version
```

- Parte de la imagen oficial 'jenkins/jenkins:lts'.
- Instala default-jdk y diferentes dependencias para poder instalar Scala.
- Cambiamos al usuario Jenkins para que reconozca todos los archivos instalados en nuestro contenedor.
- Instalamos SDKMAN, SBT y Scala y los agregamos al PATH.
- Verificamos su instalación lanzando las versiones.

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


## Jenkinsfile para CI/CD en Java y Maven

```groovy
pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
		dir('temas/errores/scala') {
                    echo 'Compilando el proyecto Scala...'
                    sh 'sbt compile'
		}
            }
        }

        stage('Test') {
            steps {
		dir('temas/errores/scala') {
                    echo 'Ejecutando pruebas automatizadas...'
                    sh 'sbt test'
		}
            }
        }
    }

    post {
        success {
            echo 'Pipeline completado con éxito'
        }
        failure {
            echo 'Algo falló en la ejecución del pipeline'
        }
    }
}
}
```

'Compilar' Se encarga de primero encontrar nuestro build.sbt y compilarlo. 
'Test' Entra a la ruta del proyecto ('temas/errores/scala') y ejecuta 'sbt test' para ejecutar las pruebas de nuestro Main.scala. 
'post'  Define acciones según el resultado: éxito o fallo. 

Este setup permite:

- Ejecutar Jenkins en Docker con Java y Scala preinstalado.
- Correr pruebas automatizadas de Java usando 'SBT'.
- Integrar pruebas a pipelines CI/CD de forma simple y portable.

---

