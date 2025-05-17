# Ejemplo de Programación Orientada a Aspectos (AOP) en Java con AspectJ

Este documento describe:

- Cómo hemos implementado un ejemplo de programación orientada a aspectos en Java usando AspectJ.
- Batería de pruebas y automatización con Maven.
- Cómo se construye una imagen personalizada de Jenkins con Java y se crean los contenedores.
- Ejecución de pruebas automatizadas usando un `Jenkinsfile`.

---

## Análisis de programación orientada a aspectos en Java

La **programación orientada a aspectos (AOP)** es un paradigma que permite modularizar aspectos transversales (*cross-cutting concerns*) como logging, seguridad, y control de acceso sin ensuciar la lógica principal de la aplicación. AspectJ es una herramienta popular que facilita esta modularización en Java.

En el ejemplo que veremos, hemos implementado un sistema domótico que controla dispositivos inteligentes (luces, termostato, cerradura). Usamos aspectos para:

- Registrar acciones importantes (logging).
- Restringir operaciones críticas (seguridad).
- Controlar el consumo energético (monitorización de tiempo encendido).

---

## Explicación del código en Java con AspectJ

Este proyecto define varias clases: `Luz`, `Termostato`, `SmartLock`, y aspectos como `LoggingAspect`, `SecurityAspect` y `EnergyControlAspect`. La lógica principal se mantiene limpia, y los aspectos se encargan de insertar funcionalidades transversales sin modificar las clases base.

---

### Componentes 

### 'Luz'

```java
public class Luz implements Device {
    private String name;
    private boolean isOn = false;

    public Luz(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void turnOn() {
        isOn = true;
        System.out.println(name + " encendida.");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(name + " apagada.");
    }

    public boolean isOn() {
        return isOn;
    }
}
```

Clase que representa una luz inteligente con métodos para encender y apagar.

### 'Termostato'

```Java
public class Termostato implements Device {
    private String name;
    private boolean isOn = false;
    private int temperature = 20;

    public Termostato(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void setTemperature(int t) {
        if (t < 5 || t > 35)
            throw new IllegalArgumentException("Temperatura fuera de rango");
        temperature = t;
        System.out.println(name + " temperatura ajustada a " + temperature + "C.");
    }

    public int getTemperature() {
        return temperature;
    }

    public void turnOn() {
        isOn = true;
        System.out.println(name + " encendido.");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(name + " apagado.");
    }

    public boolean isOn() {
        return isOn;
    }
}
```

Dispositivo que controla la temperatura, con método para ajustar la temperatura dentro de un rango seguro.

### 'SmartLock'

```Java
public class SmartLock implements Device {
    private String name;
    private boolean locked = true;

    public SmartLock(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void unlock() throws Exception {
        if (!SecurityAspect.isLoggedIn()) {
            throw new Exception("Acceso denegado. Inicie sesión primero.");
        }
        System.out.println(name + " desbloqueada.");
        locked = false;
    }

    public void lock() {
        System.out.println(name + " bloqueada.");
        locked = true;
    }

    public boolean isUnlocked() {
        return !locked;
    }

    public void turnOn() {} // No-op
    public void turnOff() {} // No-op
}
```

Cerradura inteligente que puede ser bloqueada o desbloqueada. Su método `unlock` está protegido por un aspecto de seguridad.

### Aspectos Implementados

### 'LoggingAspect'

```Java
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

@Aspect
public class LoggingAspect {

    @Pointcut("execution(* Device.turnOn(..)) || execution(* Device.turnOff(..))")
    public void powerControl() {}

    @Before("powerControl()")
    public void logAction(JoinPoint jp) {
        Device device = (Device) jp.getTarget();
        System.out.println("[LOG] Acción: " + jp.getSignature().getName() +
            " sobre dispositivo: " + device.getName());
    }
}
```

Registra automáticamente llamadas a métodos críticos como encender/apagar dispositivos o bloquear/desbloquear la cerradura.

### 'SecurityAspect'

```Java
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.JoinPoint;

@Aspect
public class SecurityAspect {
    private static boolean authenticated = false;

    public static void login() {
        authenticated = true;
        System.out.println("[AUTH] Usuario autenticado.");
    }

    public static void logout() {
        authenticated = false;
        System.out.println("[AUTH] Usuario desconectado.");
    }

    public static boolean isLoggedIn() {
        return authenticated;
    }
}
```

Controla que sólo un usuario autenticado pueda desbloquear la cerradura, lanzando excepciones en caso contrario.

### 'EnergyControlAspect'

```Java
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.JoinPoint;

@Aspect
public class EnergyControlAspect {
    private Map<String, Long> startTimes = new HashMap<>();

    @Pointcut("execution(* Device.turnOn(..)) && target(dev)")
    public void turnedOn(Device dev) {}

    @Pointcut("execution(* Device.turnOff(..)) && target(dev)")
    public void turnedOff(Device dev) {}

    @Before("turnedOn(dev)")
    public void markStart(Device dev) {
        startTimes.put(dev.getName(), System.currentTimeMillis());
    }

    @After("turnedOff(dev)")
    public void checkDuration(Device dev) {
        Long start = startTimes.remove(dev.getName());
        if (start != null) {
            long duration = (System.currentTimeMillis() - start) / 1000;
            if (duration > 10) {
                System.out.println("[ENERGY] " + dev.getName() +
                        " estuvo encendido por " + duration + "s. Considera optimizar.");
            }
        }
    }
}
```
Monitorea el tiempo que cada dispositivo permanece encendido y muestra alertas si se exceden ciertos límites para optimizar el consumo.

---

## Ventajas del paradigma de Programación Orientada a Aspectos (AOP)

1. **Separación de responsabilidades**: Los aspectos permiten encapsular funcionalidades transversales como logging, seguridad o control energético sin mezclar esa lógica con el código principal de los dispositivos.

2. **Modularidad**: Cambios en aspectos como la política de seguridad o las reglas de monitoreo energético se pueden realizar sin modificar las clases base ('Luz', 'Termostato', 'SmartLock').

3. **Reutilización**: Los aspectos pueden aplicarse fácilmente a múltiples clases y métodos con expresiones puntuales ('pointcuts'), facilitando la reutilización de código.

4. **Mantenimiento sencillo**: El código principal permanece limpio y enfocado en su responsabilidad, mientras que los aspectos gestionan los detalles transversales, reduciendo el riesgo de errores y facilitando pruebas.

---

## Estructura de manejo de aspectos en el proyecto

- El aspecto 'LoggingAspect' intercepta y registra llamadas a métodos importantes como 'turnOn()' o 'unlock()'.
- 'SecurityAspect' protege métodos críticos, lanzando excepciones cuando no se cumplen condiciones.
- 'EnergyControlAspect' monitoriza el tiempo de uso de dispositivos para optimización energética.

--- 

Ahora pasaremos a ver el programa de pruebas que hemos utilizado en nuestra clase Main.java que ejecuta distintos escenarios de prueba manuales usando varias instancias de pedido.

---

## Casos de prueba

### SetUp

```Java
    @BeforeEach
    public void setUp() {
        sala = new Luz("Luz del salon");
        termostato = new Termostato("Termostato");
        puerta = new SmartLock("Puerta principal");
    }
```

- Metodo anotado con @BeforeEach, lo que significa que se ejecuta automáticamente antes de cada prueba.
- Crear nuevas instancias de los objetos que vamos a probar, asegurando que cada prueba del test comience limpia e independiente

### 1. 'Encender y apagar una luz'

```Java
    @Test
    public void testLuzTurnOnOff() {
        sala.turnOn();
        assertTrue(sala.isOn(), "La luz debería estar encendida");

        sala.turnOff();
        assertFalse(sala.isOn(), "La luz debería estar apagada");
    }
```

- Se enciende y apaga la luz del salón

---

### 2. 'Ajustar la temperatura con el termostato'

```Java
    @Test
    public void testTermostatoOnOffAndTemperature() {
        termostato.turnOn();
        termostato.setTemperature(25);
        assertEquals(25, termostato.getTemperature(), "La temperatura debería ser 25");

        termostato.turnOff();
        assertFalse(termostato.isOn(), "El termostato debería estar apagado");
    }

```

- Se enciende el termostato, se ajusta la temperatura a 25ºC, y luego se apaga.

---

### 3. 'Intento de desbloqueo de la puerta sin autenticación'

```Java
    @Test
    public void testSmartLockWithoutLoginFails() {
        Exception exception = assertThrows(Exception.class, () -> {
            puerta.unlock();
        });
        assertEquals("Acceso denegado. Inicie sesión primero.", exception.getMessage());
    }
```

- Se intenta desbloquear la cerradura inteligente sin realizar un login previo.
- Verificar que el aspecto de seguridad bloquea el acceso y lanza la excepción adecuada.

---

### 4. 'Autenticarse y desbloquear la puerta correctamente'

```Java
    @Test
    public void testSmartLockWithLogin() throws Exception {
        SecurityAspect.login();
        puerta.unlock();  // No debe lanzar excepción
        assertTrue(puerta.isUnlocked(), "La cerradura debería estar desbloqueada");
        SecurityAspect.logout();
    }
```

- Se realiza un login mediante 'SecurityAspect.login()', se desbloquea la puerta y luego se hace logOut.
- Confirmar que tras autenticarse el acceso está permitido y la puerta se desbloquea sin errores.

---

Este programa sirve como un banco de pruebas básico para:

- Confirmar el correcto uso de aspectos.
- Verificar comportamientos específicos de nuestro programa.

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
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>1.9.21</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.21</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjtools</artifactId>
            <version>1.9.21</version>
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

- mvn clean compile # Se encargará de compilar pom.xml
- mvn test # Se encargará de probar todas las baterías de prueba que estén dentro del directorio (src/test/java/MainTest.java)
- mvn exec:java # Si quisieramos que simplemente ejecute el programa, tendriamos que cambiar el nombre de nuestra carpeta "test" a "java" y "MainTest.java" a "Main.java" para que Maven reconozca que estamos haciendo una ejecución de nuestro Main.java.

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
                dir('temas/aspectos/java') {
                    echo 'Compilando el proyecto...'
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Ejecutar Main.java') {
            steps {
                dir('temas/aspectos/java') {
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

'Compilar' Se encarga primero de buscar nuestro 'pom.xml' y compilarlo. 
'Ejecutar Main.java' Entra a la ruta del proyecto ('temas/delegacion/java') y ejecuta 'Maven' para ejecutar las pruebas. 
'post'  Define acciones según el resultado: éxito o fallo. 

Este setup permite:

- Ejecutar Jenkins en Docker con Java y Maven preinstalado.
- Correr pruebas automatizadas de Java usando `Maven`.
- Integrar pruebas a pipelines CI/CD de forma simple y portable.

---

