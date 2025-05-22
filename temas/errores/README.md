# Sistema de Gestión de Biblioteca con AspectJ

Este documento explora la implementación de un sistema de gestión de biblioteca utilizando **Programación Orientada a Aspectos (AOP)** con AspectJ en Java. El proyecto demuestra cómo separar las preocupaciones transversales, como el registro de actividades, del código principal de la aplicación.

---

## Clases principales
- `Book.java`: Representa un libro con título, ISBN y estado de disponibilidad
- `Member.java`: Representa un miembro de la biblioteca que puede pedir y devolver libros
- `BookManager.java`: Gestiona el inventario de libros y miembros de la biblioteca
- `LoggingAspect.java`: Aspecto para registrar las operaciones de préstamo y devolución

---

## Configuración de AspectJ

- Archivo de configuración AspectJ (`aop.xml`)
- Integración con el sistema de compilación

**Configuración básica:**

Para activar la funcionalidad de AspectJ en el proyecto, se incluye el archivo `aop.xml` que registra los aspectos y define el alcance del tejido (weaving):

```xml
<aspectj>
  <aspects>
    <aspect name="com.ejemplo.proyecto.LoggingAspect"/>
  </aspects>
  <weaver>
    <include within="com.ejemplo.proyecto.*"/>
  </weaver>
</aspectj>
```

---

## Conceptos clave

### Programación Orientada a Aspectos (AOP)

Un paradigma que permite separar las preocupaciones transversales (cross-cutting concerns) del código principal de la aplicación, resultando en un diseño más modular y mantenible.

---

### Puntos de corte (Pointcuts)

Expresiones que definen los puntos de ejecución donde se aplicarán los aspectos. En nuestro sistema definimos:

- `lendBookOperation()`: Captura la ejecución del método de préstamo de libros
- `returnBookOperation()`: Captura la ejecución del método de devolución de libros

---

### Consejos (Advices)

Código que se ejecuta cuando se alcanza un punto de corte definido:

- `@Before`: Se ejecuta antes de que se ejecute el método interceptado
- `@After`: Se ejecuta después de que se ejecute el método interceptado, independientemente del resultado

---

### Tejido (Weaving)

Proceso de integrar los aspectos con el código base para crear el comportamiento final. AspectJ puede realizar el tejido:

- En tiempo de compilación (compile-time weaving)
- En tiempo de carga (load-time weaving)
- En tiempo de ejecución (runtime weaving)

---

## Pruebas

El proyecto incluye un conjunto completo de pruebas unitarias (`AspectosTests.java`) que verifican:

- Funcionalidad básica de las clases `Book` y `Member`
- Operaciones de gestión del `BookManager`
- Casos especiales de préstamo y devolución

---

## Recomendaciones

- Usar aspectos solo para preocupaciones verdaderamente transversales
- Mantener los pointcuts lo más específicos posible
- Documentar claramente la interacción entre aspectos y código base
- Considerar el impacto en el rendimiento de los aspectos en operaciones críticas
- Incluir pruebas específicas para verificar el comportamiento de los aspectos

---

## Configuración del proyecto con Maven

El proyecto utiliza Maven para la gestión de dependencias y el ciclo de vida de construcción. El archivo `pom.xml` define:

- Dependencias de AspectJ (aspectjrt y aspectjweaver)
- Dependencias de pruebas unitarias con JUnit Jupiter
- Configuración del plugin maven-surefire para ejecutar pruebas con AspectJ activado

```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.9.7</version>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.7</version>
</dependency>
```

La configuración del plugin maven-surefire asegura que los aspectos se tejan durante la ejecución de las pruebas:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M5</version>
    <configuration>
        <argLine>
            -javaagent:${settings.localRepository}/org/aspectj/aspectjweaver/1.9.7/aspectjweaver-1.9.7.jar
        </argLine>
    </configuration>
</plugin>
```

---

## CI/CD y Aprovisionamiento

### Integración continua con Jenkins

El proyecto incluye un pipeline de integración continua (`Jenkinsfile`) que automatiza la ejecución de pruebas.

<!-- ```groovy
pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Test') {
            steps {
                dir('temas/aspectos/java-03/mi-proyecto') {
                    sh 'mvn clean test'
                }
            }
            post {
                always {
                    junit 'temas/aspectos/java-03/mi-proyecto/target/surefire-reports/*.xml'
                }
            }
        }
    }
}
``` -->

### Configuración de Docker

Se incluye un `Dockerfile` para la creación de una imagen de Jenkins con todas las herramientas necesarias:
- Maven para construir el proyecto Java
- Docker CLI para la integración con contenedores
- Plugins de Jenkins para orquestación de pipelines

### Despliegue automatizado

Para realizar el despliegue, se proporciona un script Python que ejecuta los comandos de Terraform necesarios:

```bash
python deploy_jenkins.py
```

El script automatiza los siguientes pasos:
1. Inicialización de Terraform
2. Validación de la configuración
3. Aplicación de la infraestructura

Para liberar recursos después del uso:

```bash
docker stop <nombre-contenedor>
docker rm <nombre-contenedor>
```

---

## Para ejecutar el proyecto

```bash
# Compilar y ejecutar pruebas con Maven
mvn clean test

# Compilar el proyecto de forma manual con AspectJ
javac -cp .:aspectjrt.jar com/ejemplo/proyecto/*.java

```