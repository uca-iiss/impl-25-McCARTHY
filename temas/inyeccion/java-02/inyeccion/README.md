
```markdown
# Guice Dependency Injection Example

Este proyecto es un ejemplo básico de **inyección de dependencias en Java** utilizando el framework [Google Guice](https://github.com/google/guice).

## 🧩 ¿Qué es la inyección de dependencias?

La inyección de dependencias (DI) es un patrón de diseño que permite desacoplar componentes de software. En lugar de que una clase cree o busque sus propias dependencias, se le **inyectan desde el exterior**, generalmente por un framework.

Guice permite declarar las dependencias mediante anotaciones (`@Inject`) y definir cómo resolverlas a través de módulos (`AbstractModule`).

---

## 📁 Estructura del proyecto

```

guice-injection-example/
├── pom.xml                        # Configuración del proyecto y dependencias
├── src/
│   ├── main/
│   │   └── java/com/example/
│   │       ├── App.java               # Clase principal que arranca el inyector Guice
│   │       ├── BillingService.java    # Servicio con dependencia inyectada
│   │       ├── CreditCardProcessor.java  # Interfaz (contrato)
│   │       ├── PaypalProcessor.java   # Implementación concreta
│   │       └── GuiceModule.java       # Configuración del binding
│   └── test/
│       └── java/com/example/
│           ├── PaypalProcessorTest.java  # Prueba de implementación
│           └── BillingServiceTest.java   # Prueba del servicio con mock

````

---

## ⚙️ ¿Cómo funciona el ejemplo?

### 1. Definimos una **interfaz**:
```java
public interface CreditCardProcessor {
    void process(String amount);
}
````

### 2. Creamos una **implementación concreta**:

```java
public class PaypalProcessor implements CreditCardProcessor {
    public void process(String amount) {
        System.out.println("Procesando pago de " + amount + " vía PayPal.");
    }
}
```

### 3. Inyectamos esa dependencia en otra clase:

```java
public class BillingService {
    private final CreditCardProcessor processor;

    @Inject
    public BillingService(CreditCardProcessor processor) {
        this.processor = processor;
    }

    public void checkout(String amount) {
        processor.process(amount);
    }
}
```

### 4. Configuramos Guice en un módulo:

```java
public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CreditCardProcessor.class).to(PaypalProcessor.class);
    }
}
```

### 5. En `App.java`, arrancamos Guice e instanciamos el servicio:

```java
Injector injector = Guice.createInjector(new GuiceModule());
BillingService service = injector.getInstance(BillingService.class);
service.checkout("100.00€");
```

---

## 🚀 Cómo compilar y ejecutar

### Compilar:

```bash
mvn clean compile
```

### Ejecutar la aplicación:

```bash
mvn exec:java -Dexec.mainClass="com.example.App"
```

### Ejecutar los tests:

```bash
mvn test
```

---

## 🧪 Testing

Se incluyen dos pruebas básicas con JUnit 5:

* `PaypalProcessorTest`: verifica que el método `process` imprime correctamente.
* `BillingServiceTest`: usa una clase mock interna para validar que se llama al procesador de pagos.

Los resultados de los tests se generan en `target/surefire-reports`.

---

## 🤖 Integración con Jenkins

Este proyecto puede integrarse fácilmente en un pipeline de Jenkins. Ejemplo de `Jenkinsfile`:

```groovy
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
```

---

## ✅ Requisitos

* JDK 11 o superior
* Maven 3.6+
* Conexión a internet para descargar dependencias

---

## 📚 Referencias

* [Google Guice](https://github.com/google/guice)
* [Documentación oficial de JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
* [Guía rápida de Dependency Injection](https://martinfowler.com/articles/injection.html)
