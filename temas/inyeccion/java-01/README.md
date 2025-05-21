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
    private boolean stockDisponible = true;

    public void setStockDisponible(boolean disponible) {
        this.stockDisponible = disponible;
    }

    public boolean hayStock(Pedido pedido) {
        System.out.println("Verificando stock para: " + pedido.getProducto());
        return stockDisponible;
    }
}
```

Comprueba si el stock es suficiente para procesar el pedido.

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
    private double ultimoCobro = 0.0;

    public void cobrar(String cliente, double total) {
        this.ultimoCobro = total;
        System.out.println("Cobrando a " + cliente + ": $" + total);
    }

    public double getUltimoCobro() {
        return ultimoCobro;
    }
}
```

Encargado de cobrar el importe al cliente.
Obtenemos el último cobro del cliente para hacer la verificación de pruebas.

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

    public boolean procesar(String cliente, Pedido pedido) {
        System.out.println("Procesando pedido de: " + cliente);

        if (!stock.hayStock(pedido)) {
            System.out.println("No hay stock disponible.");
            return false;
        }

        double total = descuentos.aplicarDescuento(pedido);
        pago.cobrar(cliente, total);

        System.out.println("Pedido completado.");
        return true;
    }
}
```

Esta clase central recibe sus dependencias inyectadas por Guice, y coordina el flujo completo de procesamiento de un pedido.
Devuelve true cuando el pedido es satisfecho.

## Ventajas del patrón de Inyección

1. **Desacoplamiento**: La clase 'ProcesadorPedido' no crea sus propias instancias de 'VerificadorStock', 'CalculadorDescuentos' ni 'ProcesadorPago', Guice se encarga de inyectarlas, lo que permite cambiar fácilmente una implementación sin modificar 'ProcesadorPedido'.
2. **RSeparación de responsabilidades**: Cada clase tiene una responsabilidad bien definida, gracias a DI, estas clases no saben nada unas de otras, excepto lo necesario.
3. **Flexibilidad y extensibilidad**: Si cambiar la lógica de descuentos, añadir logging a los pagos, solo necesitariamos crear nuevas implementaciones y cambiar el binding en el 'AppModule'.
4. **Código limpio y mantenible**: 'ProcesadorPedido' no contiene lógica innecesaria de construcción de objetos, solo depende de interfaces públicas o clases concretas, y se enfoca en la lógica de cómo se procesa un pedido.

---

Ahora pasaremos a ver el programa de pruebas que hemos utilizado en nuestra clase Main.java que ejecuta distintos escenarios de prueba manuales usando varias instancias de pedido.

## Estructura de AppModule

El archivo `AppModule` es una clase que extiende `AbstractModule` de Google Guice y se utiliza para definir cómo se deben crear e inyectar las dependencias en la aplicación. Su función principal es centralizar la configuración de los objetos que serán gestionados por el contenedor de inyección de dependencias.

### Explicación del código

```java
import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(VerificadorStock.class).toInstance(new VerificadorStock());
        bind(CalculadorDescuentos.class).toInstance(new CalculadorDescuentos());
        bind(ProcesadorPago.class).toInstance(new ProcesadorPago());
    }
}
```

- `AppModule` hereda de `AbstractModule`, lo que permite sobreescribir el método `configure()`.
- Dentro de `configure()`, se definen los *bindings* usando el método `bind()`. Esto le indica a Guice cómo debe proporcionar instancias de cada clase cuando sean requeridas.
- `toInstance(new VerificadorStock())` significa que siempre se usará la misma instancia de `VerificadorStock` cuando se solicite esa dependencia. Lo mismo ocurre con `CalculadorDescuentos` y `ProcesadorPago`.
- Así, cuando otra clase (por ejemplo, `ProcesadorPedido`) declare que necesita alguna de estas dependencias, Guice se encargará de inyectar la instancia correspondiente automáticamente.

Esta estructura permite desacoplar la creación de objetos del resto de la lógica de la aplicación, facilitando el mantenimiento, la extensión y la realización de pruebas, ya que puedes cambiar fácilmente las implementaciones o el ciclo de vida de los objetos modificando solo este módulo.

## Casos de prueba

### 1. 'Pedido sin descuento'

```Java
    @Test
    public void testPedidoSinDescuento() {
        CalculadorDescuentos desc = new CalculadorDescuentos();
        Pedido pedido = new Pedido("Ratón inalámbrico", 2, 25.0);
        assertEquals(50.0, desc.aplicarDescuento(pedido), 0.001);
    }
```

 - Verifica que el cálculo del total de un pedido sin descuento sea correcto. Crea un pedido pequeño y comprueba que el total calculado coincide con el esperado.
---

### 2. 'Pedido con descuento'

```Java
    @Test
    public void testPedidoConDescuento() {
        CalculadorDescuentos desc = new CalculadorDescuentos();
        Pedido pedido = new Pedido("Monitor 27", 7, 120.0);
        assertEquals(7 * 120.0 * 0.9, desc.aplicarDescuento(pedido), 0.001);
    }
```

- Comprueba que se aplique correctamente un descuento cuando el pedido supera cierta cantidad. Se espera que el total refleje el descuento aplicado.

---

### 3. 'Verificación de Stock'

```Java
    @Test
    public void testVerificacionStock() {
        VerificadorStock stock = new VerificadorStock();
        Pedido pedido = new Pedido("Cualquier cosa", 1, 5.0);
        assertTrue(stock.hayStock(pedido)); // siempre devuelve true
    }
```

- Testea que el verificador de stock siempre indique que hay stock disponible (según la implementación actual), devolviendo `true` para cualquier pedido.

---

### 4. 'Cobro correcto de un pedido'

```Java
    @Test
    public void testCobroCorrecto() {
        ProcesadorPago pago = new ProcesadorPago();
        pago.cobrar("cliente", 200.0);
        assertEquals(200.0, pago.getUltimoCobro(), 0.001);
    }
```

-  Valida que el procesador de pagos registre correctamente el último cobro realizado. Después de cobrar una cantidad, se comprueba que el valor almacenado sea el correcto.

---

### 5. 'Procesamiento del pedido'

```Java
    @Test
    public void testProcesamientoCompleto() {
        VerificadorStock stock = new VerificadorStock();
        CalculadorDescuentos desc = new CalculadorDescuentos();
        ProcesadorPago pago = new ProcesadorPago();
        ProcesadorPedido procesador = new ProcesadorPedido(stock, desc, pago);

        Pedido pedido = new Pedido("Producto", 10, 10.0); // debería aplicar descuento
        procesador.procesar("cliente", pedido);

        double esperado = 10 * 10.0 * 0.9;
        assertEquals(esperado, pago.getUltimoCobro(), 0.001);
    }
```

- Evalúa el flujo completo de procesamiento de un pedido: verificación de stock, aplicación de descuento y cobro. Al finalizar, verifica que el monto cobrado corresponda al total con descuento.

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
        <!-- JUnit 5 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.9.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>Main</mainClass>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0</version>
            </plugin>
        </plugins>
    </build>
</project>
---

Con ello, podremos ejecutar dentro de la raiz del directorio donde se encuentra nuestro pom.xml:

- mvn clean compile     # Se encargará de compilar pom.xml 
- mvn test        # Se encargará de probar todas las baterías de prueba que estén dentro del directorio (src/test/java/MainTest.java)
- mvn exec:java   # Si quisieramos que simplemente ejecute el programa, tendriamos que cambiar el nombre de nuestra carpeta "test" a "java" y "MainTest.java" a "Main.java" para que Maven reconozca que estamos haciendo una ejecución de nuestro Main.java.

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

