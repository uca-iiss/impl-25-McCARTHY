# ¿Qué es la Inyección de Dependencias?

La Inyección de Dependencias (Dependency Injection, DI) es un principio de diseño que forma parte del concepto más amplio de Inversión de Dependencias (IoC - Inversión de Control). Consiste en proveer desde el exterior los objetos de los que una clase depende en lugar de que la propia clase cree esos objetos.

## ¿Por qué es útil?

- Favorece la desacoplamiento entre clases.
- Permite cambiar fácilmente las implementaciones (por ejemplo, de `EmailNotificador` a `SmsNotificador`).
- Mejora la testabilidad, ya que puedes inyectar mocks o stubs.
- Aumenta la flexibilidad y la mantenibilidad del código.

## ¿Cómo funciona Guice?

Google Guice es un framework de inyección de dependencias ligero para Java. Su objetivo es reemplazar la creación manual de objetos con una configuración declarativa basada en bindings (enlaces).

### Ventajas de Guice

- Inyección por constructor, campo o método
- Sin necesidad de XML ni anotaciones pesadas
- Muy adecuado para proyectos pequeños y medianos
- Uso sencillo con clases Java normales

## � ¿Cómo se aplica en este ejemplo?

- Tenemos una interfaz abstracta: `Notificador`
- Varias implementaciones posibles: `EmailNotificador`, `SmsNotificador`, etc.
- `ServicioMensajes` depende de `Notificador`, pero no sabe cuál (no hace `new EmailNotificador()`).
- Guice se encarga de inyectar la clase concreta en tiempo de ejecución, gracias a la clase `AppModule` que define los bindings.

## Descripción de clases

### `Notificador.java`
Interfaz que define el contrato para enviar un mensaje.

```java
public interface Notificador {
    void enviar(String mensaje);
}
```

### `EmailNotificador.java`
Implementación concreta de `Notificador`.

```java
public class EmailNotificador implements Notificador {
    public void enviar(String mensaje) {
        System.out.println("[EMAIL] " + mensaje);
    }
}
```

### `ServicioMensajes.java`
Clase que depende de un `Notificador`. La dependencia se inyecta por constructor con la anotación `@Inject`.

```java
public class ServicioMensajes {
    private final Notificador notificador;

    @Inject
    public ServicioMensajes(Notificador notificador) {
        this.notificador = notificador;
    }

    public void notificar(String mensaje) {
        notificador.enviar(mensaje);
    }
}
```

### `AppModule.java`
Clase de configuración de Guice que define qué implementación inyectar para la interfaz.

```java
public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Notificador.class).to(EmailNotificador.class);
    }
}
```

### `Main.java`
Punto de entrada de la aplicación. Crea el inyector de Guice y ejecuta la lógica.

```java
public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        ServicioMensajes servicio = injector.getInstance(ServicioMensajes.class);
        servicio.notificar("Tu pedido ha sido procesado.");
    }
}
```

## Test automatizado

### `ServicioMensajesTest.java`
Este test comprueba que la clase `ServicioMensajes` funciona correctamente cuando se le inyecta una implementación real (`EmailNotificador`):

```java
class ServicioMensajesTest {
    @Test
    void testNotificacionEmail() {
        Notificador notificador = new EmailNotificador();
        ServicioMensajes servicio = new ServicioMensajes(notificador);
        assertDoesNotThrow(() -> servicio.notificar("Mensaje de prueba"));
    }
}
```

Aquí no usamos Guice directamente en el test, sino que hacemos la inyección manual, algo común en pruebas para tener control total del entorno.

## ⚙️ `pom.xml` destacado

El archivo `pom.xml` es el corazón del proyecto Maven. Define:

- las dependencias que usa el proyecto,
- los plugins para compilar y ejecutar,
- y las configuraciones necesarias para los tests y la ejecución con Guice.

```xml
<dependencies>
  <dependency>
    <groupId>com.google.inject</groupId>
    <artifactId>guice</artifactId>
    <version>5.1.0</version>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## 🧪 Ejecución local

Compilar y ejecutar:

```bash
mvn clean compile
mvn exec:java
```

Ejecutar los tests:

```bash
mvn test
```

## 🛠️ CI/CD con Jenkins

### `Jenkinsfile`:

`Jenkinsfile` automatiza dos pasos principales: compilar el proyecto y ejecutar los tests, todo dentro de un contenedor Docker con Maven y Java preinstalado:

```groovy
pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-17'
        }
    }
    stages {
        stage('Build') {
            steps {
                dir('temas/inyeccion/java/inyeccion') {
                    sh 'mvn -B -DskipTests clean package'
                }
            }
        }
        stage('Test') {
            steps {
                dir('temas/inyeccion/java/inyeccion') {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit 'temas/inyeccion/java/inyeccion/target/surefire-reports/*.xml'
                }
            }
        }
    }
}
```
