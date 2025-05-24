### ✅ `README.md`

```markdown
# Inyección de Dependencias con Anotaciones Personalizadas en Java

Este proyecto implementa un ejemplo básico de **inyección de dependencias en Java** utilizando **anotaciones personalizadas** (`@Inject`) inspiradas en el estándar **JSR 330** y siguiendo los principios descritos en el artículo de Baeldung:  
👉 [Creating a Custom Annotation in Java](https://www.baeldung.com/java-custom-annotation)

---

## 🎯 Objetivo

Mostrar cómo se pueden usar **anotaciones definidas por el usuario** para marcar dependencias, e inyectarlas automáticamente en tiempo de ejecución mediante **reflexión**.

---

## 📁 Estructura del Proyecto

```

custom-inject-annotations/
├── pom.xml
├── Jenkinsfile
├── README.md
├── src/
│   ├── main/
│   │   └── java/com/example/
│   │       ├── App.java
│   │       ├── annotations/
│   │       │   └── Inject.java
│   │       ├── injector/
│   │       │   └── SimpleInjector.java
│   │       ├── service/
│   │       │   ├── Logger.java
│   │       │   ├── ConsoleLogger.java
│   │       │   └── MyComponent.java
│   └── test/
│       └── java/com/example/service/
│           └── MyComponentTest.java

````

---

## 🧠 ¿Cómo funciona la inyección?

### 1. Definimos una anotación personalizada `@Inject`:

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Inject {}
````

Esto permite marcar campos, constructores o métodos que serán procesados durante la inyección.

---

### 2. Creamos un inyector por reflexión:

```java
public class SimpleInjector {
    public static void inject(Object target) {
        for (Field field : target.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                Object dependency = field.getType().getDeclaredConstructor().newInstance();
                field.setAccessible(true);
                field.set(target, dependency);
            }
        }
    }
}
```

Este inyector busca campos anotados con `@Inject` e instancia automáticamente el tipo requerido.

---

### 3. Aplicamos `@Inject` a nuestras clases:

```java
public class MyComponent {
    @Inject
    private Logger logger;

    public void run() {
        logger.log("¡Inyección completada con éxito!");
    }
}
```

```java
public interface Logger {
    void log(String message);
}
```

```java
public class ConsoleLogger implements Logger {
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
```

---

### 4. Ejecutamos la aplicación:

```java
public class App {
    public static void main(String[] args) {
        MyComponent component = new MyComponent();
        SimpleInjector.inject(component);
        component.run();
    }
}
```

---

## ✅ Tests

Usamos JUnit 5 para probar la correcta inyección y ejecución del componente:

```java
@Test
void testInjectionAndRun() {
    MyComponent component = new MyComponent();
    SimpleInjector.inject(component);
    ...
    assertTrue(out.toString().contains("¡Inyección completada con éxito!"));
}
```

---

## 🚀 ¿Cómo ejecutar el proyecto?

### Requisitos:

* JDK 11 o superior
* Maven 3.6+

### Compilar el código:

```bash
mvn clean compile
```

### Ejecutar la aplicación:

```bash
mvn exec:java
```

### Ejecutar los tests:

```bash
mvn test
```

---

## 🤖 Integración con Jenkins

Este proyecto puede integrarse fácilmente en Jenkins con el siguiente `Jenkinsfile`:

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

## 🔗 Recursos

* [JSR 330 - Dependency Injection for Java](https://jcp.org/en/jsr/detail?id=330)
* [Baeldung - Custom Annotations](https://www.baeldung.com/java-custom-annotation)
* [Java Reflection API](https://docs.oracle.com/javase/tutorial/reflect/)

