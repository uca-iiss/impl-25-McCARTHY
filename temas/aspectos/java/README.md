# Ejemplo de Inyección de Dependencias en Java

Este documento describe:

- Cómo hemos implementado un ejemplo de inyección de dependencias en Java.
- Batería de pruebas y automatización con Maven.
- Cómo se construye una imagen personalizada de Jenkins con Java y se crean los contenedores.
- Ejecución de pruebas automatizadas usando un `Jenkinsfile`.

---

# Análisis de inyección de dependencias en Java

La **inyección de dependencias (DI)** es un patrón de diseño en el que un objeto no crea sus dependencias directamente, sino que se las proporcionan desde fuera. Esto mejora el desacoplamiento entre clases, facilita el mantenimiento, la extensión del código y la realización de pruebas unitarias.

En Java, este patrón se puede implementar de muchas maneras: manualmente, con frameworks como Spring o Guice. En este ejemplo utilizamos **Google Guice** por su simplicidad y ligereza.

Aplicando el patrón DI al ejemplo que veremos más adelante, la clase principal (`ProcesadorPedido`) no instancia directamente sus colaboradores (`VerificadorStock`, `CalculadorDescuentos`, `ProcesadorPago`), sino que Guice los inyecta automáticamente.

---

# Explicación del código en Java con Guice

Este proyecto define varias clases: `Pedido`, `ProcesadorPedido`, `VerificadorStock`, `CalculadorDescuentos`, y `ProcesadorPago`. El flujo simula el procesamiento de pedidos en un sistema de comercio electrónico. Las dependencias se resuelven mediante **inyección por constructor**, lo cual es una práctica recomendada.

## Componentes

### `Pedido`

```java
public class Pedido {
    private String producto;
    private int cantidad;
    private double precioUnitario;

    public Pedido(String producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getTotalSinDescuento() {
        return cantidad * precioUnitario;
    }
}
```

Contiene los datos de un pedido: producto, cantidad y precio por unidad. Aparte de los metodos observadores, tiene un método para calcular el total sin descuento.

### 'VerificarStock'

```Java
public class VerificadorStock {
    public boolean hayStock(Pedido pedido) {
        System.out.println("Verificando stock para: " + pedido.getProducto());
        return true; // Simulación
    }
}
```

Comprueba si hay stock suficiente para procesar el pedido.

### 'CalculadorDescuentos'

```Java
public class CalculadorDescuentos {
    public double aplicarDescuento(Pedido pedido) {
        double descuento = pedido.getCantidad() > 5 ? 0.1 : 0.0;
        double total = pedido.getTotalSinDescuento() * (1 - descuento);
        System.out.println("Total con descuento: $" + total);
        return total;
    }
}
```

Aplica una política simple de descuentos según la cantidad del pedido.

### 'ProcesadorPago'

```Java
public class ProcesadorPago {
    public void cobrar(String cliente, double total) {
        System.out.println("Cobrando a " + cliente + ": $" + total);
    }
}
```

Encargado de cobrar el importe al cliente.

### 'ProcesadorPedido'

```Java
import com.google.inject.Inject;

public class ProcesadorPedido {

    private final VerificadorStock stock;
    private final CalculadorDescuentos descuentos;
    private final ProcesadorPago pago;

    @Inject
    public ProcesadorPedido(VerificadorStock stock, CalculadorDescuentos descuentos, ProcesadorPago pago) {
        this.stock = stock;
        this.descuentos = descuentos;
        this.pago = pago;
    }

    public void procesar(String cliente, Pedido pedido) {
        System.out.println("Procesando pedido de: " + cliente);

        if (!stock.hayStock(pedido)) {
            System.out.println("No hay stock disponible.");
            return;
        }

        double total = descuentos.aplicarDescuento(pedido);
        pago.cobrar(cliente, total);

        System.out.println("Pedido completado.");
    }
}
```

Esta clase central recibe sus dependencias inyectadas por Guice, y coordina el flujo completo de procesamiento de un pedido.

## Ventajas del patrón de Inyección

1. **Desacoplamiento**: La clase 'ProcesadorPedido' no crea sus propias instancias de 'VerificadorStock', 'CalculadorDescuentos' ni 'ProcesadorPago', Guice se encarga de inyectarlas, lo que permite cambiar fácilmente una implementación sin modificar 'ProcesadorPedido'.
2. **RSeparación de responsabilidades**: Cada clase tiene una responsabilidad bien definida, gracias a DI, estas clases no saben nada unas de otras, excepto lo necesario.
3. **Flexibilidad y extensibilidad**: Si cambiar la lógica de descuentos, añadir logging a los pagos, solo necesitariamos crear nuevas implementaciones y cambiar el binding en el 'AppModule'.
4. **Código limpio y mantenible**: 'ProcesadorPedido' no contiene lógica innecesaria de construcción de objetos, solo depende de interfaces públicas o clases concretas, y se enfoca en la lógica de cómo se procesa un pedido.

---

Ahora pasaremos a ver el programa de pruebas que hemos utilizado en nuestra clase Main.java que ejecuta distintos escenarios de prueba manuales usando varias instancias de pedido.

## Estructura del archivo

```Java
Injector injector = Guice.createInjector(new AppModule());
ProcesadorPedido procesador = injector.getInstance(ProcesadorPedido.class);
```

- Se crea un contenedor Guice con la configuración definida en AppModule
- A través del contenedor, se obtiene una instancia de 'ProcesarPedido' con todas sus dependencias inyectadas automáticamente

---

## Casos de prueba

### 1. 'Pedido sin descuento'

```Java
Pedido pedido1 = new Pedido("Ratón inalámbrico", 2, 25.0);
procesador.procesar("clienteA", pedido1);
```

- Cantidad baja
- No se aplica descuento
- Verifica que el sistema funcione con valores simples

---

### 2. 'Pedido con descuento'

```Java
Pedido pedido2 = new Pedido("Monitor 27 pulgadas", 7, 120.0);
procesador.procesar("clienteB", pedido2);
```

- Cantidad > 5 -> se aplica un 10% de descuento
- Verifica que la lógica de descuentos funcione correctamente

---

### 3. 'Pedido caro sin descuento'

```Java
Pedido pedido3 = new Pedido("Portátil Gaming", 1, 1500.0);
procesador.procesar("clienteC", pedido3);
```

- Alto valor unitario pero cantidad = 1
- No hay descuento
- Evalúa cómo el sistema maneja pedidos de alto coste

---

### 4. 'Pedido en el límite del descuento'

```Java
Pedido pedido4 = new Pedido("Teclado mecánico", 5, 70.0);
procesador.procesar("clienteD", pedido4);
```

- Se espera que no se aplique el descuento
- Prueba la lógica del límite condicional

---

### 5. 'Pedido grande'

```Java
Pedido pedido5 = new Pedido("Lote de pendrives", 100, 3.5);
procesador.procesar("clienteE", pedido5);
```

- Pedido masivo
- Se aplica descuento
- Evalúa rendimiento y precisión de cálculo en grandes cantidades

Este programa sirve como un banco de pruebas básico para:

- Confirmar el correcto ensamblado de dependencias mediante DI.
- Verificar comportamientos específicos de negocio.
- Demostrar que el sistema es modular y extensible.

---

## POM y Maven

Para la realización de pruebas automáticas en Java, hemos utilizado Maven para compilar, testear y ejecutar.
Por ello, hemos tenido que primero de todo crear un archivo pom.xml que se encargará de implantar las dependencias suficientes para poder compilar las librerias necesarias para su correcto funcionamiento.

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>org.ejemplo</groupId>
    <artifactId>procesamiento-pedidos</artifactId>
    <version>1.0</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Google Guice -->
        <dependency>
            <groupId>com.google.inject</groupId>
            <artifactId>guice</artifactId>
            <version>5.1.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Plugin para ejecutar la clase Main -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>Main</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
---

Con ello, podremos ejecutar dentro de la raiz del directorio donde se encuentra nuestro pom.xml:

- mvn clean compile     # Se encargará de compilar pom.xml 
- mvn exec:java         # Buscará dentro de nuestro directorio la ruta para ejecutar nuestro main.java (src/main/java/*.java)

---

Ahora pasaremos con la instalación de los contenedores de Jenkins con Java


## Dockerfile: Jenkins con Java

```dockerfile
FROM jenkins/jenkins:lts

# Cambiar a usuario root para instalar dependencias del sistema
USER root

# Instalar Maven y limpiar caché
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Verificar instalación
RUN java -version && mvn -version

# Volver al usuario Jenkins
USER jenkins
```

- Parte de la imagen oficial 'jenkins/jenkins:lts'.
- Instala Maven y herramientas de desarrollo necesarias.
- Verificamos la instalación de Java y mvn en nuestor sistema.
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


## Jenkinsfile para CI/CD en Java y Maven

```groovy
pipeline {
    agent any

    stages {
        stage('Compilar') {
            steps {
                dir('temas/inyeccion/java') {
                    echo 'Compilando el proyecto...'
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Ejecutar Main.java') {
            steps {
                dir('temas/inyeccion/java') {
                    echo 'Ejecutando programa principal...'
                    sh 'mvn exec:java'
                }
            }
        }
    }

    post {
        success {
            echo 'Compilación y ejecución completadas con éxito.'
        }
        failure {
            echo 'Hubo un error en el pipeline.'
        }
    }
}
```

'Compilaar' Se encarga primero de buscar nuestro 'pom.xml' y compilarlo. 
'Ejecutar Main.java' Entra a la ruta del proyecto ('temas/delegacion/java') y ejecuta 'Maven' para ejecutar las pruebas. 
'post'  Define acciones según el resultado: éxito o fallo. 

Este setup permite:

- Ejecutar Jenkins en Docker con Java yMaven preinstalado.
- Correr pruebas automatizadas de Java usando `Maven`.
- Integrar pruebas a pipelines CI/CD de forma simple y portable.

---

