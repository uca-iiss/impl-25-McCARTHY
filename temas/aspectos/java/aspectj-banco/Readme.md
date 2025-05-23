# Aspectos en Java

## Introducción
Bienvenido al repositorio sobre Aspectos en Java. Aquí encontrarás información detallada sobre cómo utilizar los aspectos en java para escribir código más limpio y eficiente gracias a AspectJ de java

## Estructura de Directorio
- `/README.md`: Archivo actual.
- `/aspectJ-banco`: Directorio que representa la raiz de nuestro proyecto.
- `/aspectJ-banco/pom.xml`: Archivo XML donde añadiremos las dependencias de nuestro proyecto
- `/aspectJ-banco/src/main/java/com/ejemplo`: Directorio donde encontraremos los codigos de nuestro ejemplo.
- `/aspectJ-banco/src/main/java/com/ejemplo/AuditoriaAspect.java`: Archivo java donde se encuentra la definción de nuestro aspecto que servirá para la ejecucion de los métodos de la clase BancoServicio
- `/aspectJ-banco/src/main/java/com/ejemplo/BancoServicio.java`: Archivo java donde se encuentra los diferentes métodos que vamos a usar en este ejemplo
- `/aspectJ-banco/src/main/java/com/ejemplo/Main.java`: Archivo java donde se encuentra el codigo que se va a probar mediante los test para comprobar su funcionamiento
- `/aspectJ-banco/src/main/java/com/ejemplo/Usuario.java`: Archivo java que representa un usuario con un nombre 
- `/aspectJ-banco/src/test/java/com/ejemplo/BancoServicioTest.java`: Directorio donde encontraremos los tests de nuestro ejemplo.

## Conceptos Previos

Antes de explorar los ejemplos proporcionados en este repositorio, es esencial entender algunos conceptos clave relacionados con Aspect Oriented Programming (AOP) en Java. AOP nos permite separar las preocupaciones transversales de la lógica del negocio principal. Aquí se detallan varios usos y técnicas que se emplean en el código de ejemplo:

    - Pointcut: Un pointcut define en qué punto del código se aplicará un aspecto, como métodos específicos donde se desea interceptar la ejecución. 

    - Advice: Los advices son acciones tomadas por un aspecto en un punto de ejecución. 
        -Before Advice: Se ejecuta antes de que el método interceptado se ejecute. En nuestro proyecto, beforeAccessUser() se utiliza para loguear antes de acceder a los datos del usuario.
        
        - After Advice: Se ejecuta después de que el método interceptado se complete. afterModifyUser() se utiliza para loguear después de que la información del usuario haya sido modificada, advirtiendo sobre posibles cambios en datos sensibles.
        
        - Around Advice: Envuelve completamente la ejecución del método interceptado, permitiendo controlar el comportamiento antes y después de la llamada al método, e incluso alterar el resultado. aroundUserPropertyChange() maneja cambios en las propiedades del usuario y registra esos eventos.

    - Join Point: Un join point es un punto en la ejecución del programa, como la llamada a un método o el manejo de una excepción, donde un aspecto puede ser aplicado.En nuestro caso tendremos el join point @Around("execution(* com.ejemplo.BancoServicio.*(..))"). 

## Código de Ejemplo
A continuación, se muestran los ejemplos de cómo se pueden utilizar los aspectos:
[**AuditoriaAspect.java**](./src/main/java/com/ejemplo/AuditoriaAspect.java)

```java
package com.ejemplo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class AuditoriaAspect {

    @Around("execution(* com.ejemplo.BancoServicio.*(..))")
    public Object auditarMetodo(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof String) {
            System.out.println("[AUDITORÍA] Usuario: " + args[0]);
        }

        String metodo = joinPoint.getSignature().getName();
        long inicio = System.nanoTime();

        Object resultado = joinPoint.proceed();

        long fin = System.nanoTime();
        System.out.printf("[AUDITORÍA] Método '%s' ejecutado en %d microsegundos.%n",
                metodo, (fin - inicio) / 1000);

        return resultado;
    }
}
```
Explicación de AuditoriaAspect: 

- @Around("execution(* com.ejemplo.BancoServicio.*(..))"): este join point es la ejecucion de cualquier método (*) dentro de la clase BancoServicio con caulquier argumento. Por tanto, cualquier llamada a métodos como transferir(), retirar() o consultarSaldo() es un joinpoint donde se engancha el aspecto. 

- El advice es un @Around advice porque rodea la ejecución del método interceptado. Esto significa que el código en auditarMetodo: 
    - Se ejecuta antes de que el método original se ejecute
    - Controla cuándo se llama el método original con joinPoint.proceed()
    - Se ejecute después de la ejecución original para, por ejemplo, medir tiempo o modificar el resultado

A continuación, la clase Usuario:

[**Usuario.java**](./src/main/java/com/ejemplo/Usuario.java)

```java
package com.ejemplo;

@Auditable
public class Usuario {
    private String nombre;

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

```
Clase que define le usuario que vamos a tener en el sistema simplificamos la clase teniendo unicamente un nombre

La clase BancoServicio que se encarga de las principales tareas:
[**BancoServicio.java**](./src/main/java/com/ejemplo/BancoServicio.java)

```java
package com.ejemplo;

public class BancoServicio {

    public void transferir(String origen, String destino, double monto) {
        System.out.printf("Transferencia de %.2f€ de %s a %s realizada.%n", monto, origen, destino);
    }

    public void retirar(String cuenta, double monto) {
        System.out.printf("Retiro de %.2f€ de la cuenta %s realizado.%n", monto, cuenta);
    }

    public void consultarSaldo(String cuenta) {
        System.out.printf("Saldo consultado de la cuenta %s.%n", cuenta);
    }
}

```
Tenemos los métodos transferir(), retirar y consultarSaldo que serán los métodos base para nuestro ejemplo que nos proporcionaran transfererir cantidad de dinero, retirar una cierta cantidad de dinero de una cuenta y consultar el saldo de una cuenta 

Y esta es la clase Auditable
[**Auditable.java**](./src/main/java/com/ejemplo/Auditable.java)
```java
package com.ejemplo;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Auditable {
}
```
La funcion de esta clase es tener una anotacion personalizada que definiremos para marcar la clase Usuario como "auditables", es decir, queremos que la clase Usuario tenga un comportamiento especial (ser sujetos de auditoría)

Y esta es la clase main.java que la hemos usado para las pruebas
[**Main.java**](./src/main/java/com/ejemplo/Main.java)
```java
package com.ejemplo;

public class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario("Ana");
        BancoServicio banco = new BancoServicio();

        banco.consultarSaldo(usuario.getNombre());
        banco.transferir(usuario.getNombre(), "Carlos", 200.0);
        banco.retirar(usuario.getNombre(), 50.0);
    }
}

```
## Código de tests
Ahora, se muestra unos tests para probar el correcto del ejemplo:
[**BancoServicioTest.java**](./src/main/java/com/ejemplo/BancoServicio.java)

```java
package com.ejemplo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BancoServicioTest {

    private BancoServicio banco;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        banco = new BancoServicio();
        usuario = new Usuario("Ana");
    }

    @Test
    void testTransferencia() {
        banco.transferir(usuario.getNombre(), "Carlos", 200.0);
    }

    @Test
    void testRetiro() {
        banco.retirar(usuario.getNombre(), 50.0);
    }

    @Test
    void testConsultaSaldo() {
        banco.consultarSaldo(usuario.getNombre());
    }
}

```
El sistema de gestión de proyectos utilizado en el código es **Maven**, una herramienta de software para la gestión y construcción de proyectos Java. Maven simplifica el proceso de construcción del proyecto, incluida la compilación del código, la ejecución de pruebas y el empaquetado del resultado final en formatos distribuibles como JARs o WARs. Utiliza un archivo `pom.xml` para gestionar dependencias de bibliotecas, configuraciones de compilación y plugins, incluidos los necesarios para ejecutar pruebas unitarias.

Maven integra soporte para correr pruebas automáticamente durante el proceso de construcción utilizando frameworks de pruebas como JUnit. Al definir las dependencias y configurar los plugins adecuados en el archivo `pom.xml`, Maven puede compilar el proyecto y ejecutar todas las pruebas unitarias definidas, garantizando que el software cumpla con los requisitos especificados antes de cualquier despliegue o entrega. Esto asegura una integración continua eficiente y efectiva, y es esencial para mantener la calidad del código en desarrollos colaborativos y dinámicos.

Aquí vemos el archivo `pom.xml`:
[**pom.xml**](./pom.xml)
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.ejemplo</groupId>
  <artifactId>aspectj-banco</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>aspectj-banco</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjrt</artifactId>
      <version>1.9.20</version>
    </dependency>
      <dependency>
          <groupId>org.junit.jupiter</groupId>
          <artifactId>junit-jupiter</artifactId>
          <version>5.9.3</version>
          <scope>test</scope>
      </dependency>
          <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.9.3</version>
        <scope>test</scope>
    </dependency>
  </dependencies>
  <build>
        <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>aspectj-maven-plugin</artifactId>
            <version>1.11</version>
            <configuration>
                <complianceLevel>1.8</complianceLevel>
                <source>1.8</source>
                <target>1.8</target>
                <aspectLibraries>
                    <aspectLibrary>
                        <groupId>org.aspectj</groupId>
                        <artifactId>aspectjrt</artifactId>
                    </aspectLibrary>
                </aspectLibraries>
                <verbose>true</verbose>
                <showWeaveInfo>true</showWeaveInfo>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>compile</goal>
                        <goal>test-compile</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
            <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.10.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>
            <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M8</version> <!-- versión compatible con JUnit 5 -->
            <configuration>
                <useModulePath>false</useModulePath>
            </configuration>
        </plugin>
    </plugins>
  </build>
</project>

``` 

Configuración del Proyecto Maven en `pom.xml`:

El archivo `pom.xml` define la estructura y configuración de gestión del proyecto Java, utilizando Maven para manejar dependencias, compilar el proyecto y ejecutar pruebas. Aquí se detallan los aspectos clave del archivo:

- **Información del Proyecto**:
  - `groupId`: Identifica el grupo del proyecto, aquí es `com.ejemplo`.
  - `artifactId`: El nombre del artefacto que Maven construye, aquí es `aspectJ-banco`.
  - `version`: La versión del artefacto, aquí es `1.0-SNAPSHOT`.

- **Configuración del Compilador**:
  - La versión de Java configurada es la 1.8 para compilación (source y target en 1.8), garantizando compatibilidad con Java 8.
    - Esto se establece en el plugin `maven-compiler-plugin` y también en la configuración del `aspectj-maven-plugin` para que AspectJ compile con la misma versión.

- **Dependencias**:
  - **JUnit 5**: JUnit 5 (junit-jupiter y junit-jupiter-engine versión 5.9.3) están incluidas para permitir la ejecución y escritura de pruebas unitarias, todas con scope test para que solo se usen en la fase de testeo.

  - **AspectJ**: Dependencias `aspectjrt proporciona la infraestructura necesaria para ejecutar código que use programación orientada a aspectos, es decir, para que los aspectos se puedan aplicar en tiempo de ejecución.

- **Plugins**:
  - **Maven Surefire Plugin**: Configurado para automatizar la ejecución de pruebas durante el proceso de construcción. Este plugin busca y ejecuta pruebas dentro del proyecto, integrándose con JUnit para reportar resultados.

Este archivo `pom.xml` es fundamental para el manejo eficiente del ciclo de vida del proyecto, desde la compilación hasta la ejecución de pruebas, garantizando que todos los componentes se integren adecuadamente bajo la configuración especificada.


## Ejecución Test
Para ejecutar el código y pasar los test de dicho código, realiza los siguientes pasos detallados que incluyen la creación de un Jenkinsfile, creación pipeline y ejecución del pipeline

### 1. Creación Jenkinsfile
A continuación, hemos creado el Jenkinsfile necesario para realizar el pipeline, este se encuentra en la carpeta con el resto de código

```Jenkinsfile
pipeline {
    agent {
        docker {
            image 'maven:3.8.6-jdk-8' // Usa un contenedor Docker con Maven y JDK 11
        }
    }

    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                dir('temas/aspectos/java/aspectj-banco') {
                    sh 'mvn clean test'
                }
            }
        }
    }

    post {
        always {
            junit 'temas/aspectos/java/aspectj-banco/target/surefire-reports/*.xml'
        }
        success {
            echo 'Build y tests OK!'
        }
        failure {
            echo 'Algo falló, revisa los tests o build!'
        }
    }
}
```

### 2. Crear Pipeline
Una vez realizados los pasos anteriores, abrimos Jenkins y creamos un nuevo Pipeline. Para ello: 

 - Lo definimos como `Pipeline script from SCM` y como SCM seleccionamos `Git`.
 - Ponemos la siguiente URL: `https://github.com/uca-iiss/WIRTH-impl-25`.
 - En branch ponemos `*/feature-aspectos`.
 - Por último, en Script Path añadimos `temas/aspectos/java/aspectjbanco/Jenkinsfile`

Y con esta configuración hemos creado el pipeline necesario para la ejecución de los test

### 3. Ejecutar los Tests
Una vez creado el pipeline, ejecutamos dando a `Construir ahora` y el propio Jenkins pasará los test automaticamente.