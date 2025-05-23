# Manejo de Errores en Kotlin

## Introducción
Bienvenido a este repositorio donde explicaremos y mostraremos los conceptos de manejo de errores en Kotlin, mediante un ejemplo sencillo con un código limpio y accesible para cualquiera. El objetivo es comprender cómo funciona el control de errores mediante try, catch, finally, la creación de excepciones personalizadas, y su validación mediante pruebas unitarias y pipelines automatizados.

## Estructura de Directorio
- `/README.md`: Archivo actual, donde explicaremos los conceptos y mostraremos los pasos a seguir para comprobar el funcionamiento del ejemplo.
- `src/main/kotlin/app/MainApp.kt`: Archivo Kotlin donde se encuentra el código de ejemplo.
- `/build.gradle.kts`: Archvio de configuración de Gradle para manejar dependencias y otras configuraciones.
- `src/main/kotlin/app/test.kt`: Archivo Kotlin donde se encuentran las pruebas unitarias.
- `/Jenkinsfile`: Configuración del pipeline para la comprobación automática del funcionamiento del código.

## Conceptos Previos
Antes de comenzar con el código, explicaremos algunos conceptos clave necesarios para entender cómo se gestiona el control de errores en Kotlin:

- **Manejo Básico con try-catch**:
 Kotlin permite manejar excepciones utilizando bloques try-catch, donde el código propenso a errores se coloca dentro del try, y la respuesta ante una excepción se define en catch.

```kotlin
try {
    // código que puede lanzar una excepción
} catch (e: Exception) {
    // manejo de la excepción
}
```

- **Bloque finally**:
El bloque finally, si se incluye, se ejecuta siempre, ocurra o no una excepción. Es útil para liberar recursos como conexiones a bases de datos, archivos abiertos, etc.

```kotlin
try {

} catch (e: Exception) {

}finally {
     // ejecución que siempre se ejecuta
}
```

- **Excepciones Personalizadas**:
    Kotlin permite definir nuestras propias excepciones extendiendo la clase Exception. Esto nos ayuda a manejar casos de error específicos y dar mensajes más claros al usuario.

```kotlin
class CustomException(message: String): Exception(message)
```

- **Validación de Argumentos y Excepciones**:
El uso de require, check y validaciones explícitas permiten lanzar excepciones personalizadas o estándar como IllegalArgumentException, IllegalStateException, etc., cuando los datos no cumplen ciertas condiciones.


## Código de Ejemplo
A continuación, mostramos cómo se puede aplicar el manejo de errores en Kotlin:
[**MainApp.kt**](src/main/kotlin/app/MainApp.kt)

```kotlin
package app

import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.io.File
import java.io.IOException

class MainApp {
    val intro: String
        get() = "Demostración de gestión de errores en Kotlin"
}

class InvalidInputException(message: String) : Exception(message)

fun parseInteger(input: String): Int {
    return try {
        input.toInt()
    } catch (e: NumberFormatException) {
        throw InvalidInputException("El valor '$input' no es un número válido.")
    }
}

fun accessElement(index: Int): Int {
    val data = listOf(10, 20, 30)
    return data[5]
}

fun readConfig(path: String): String {
    try {
        return File(path).readText()
    } catch (e: IOException) {
        throw IOException("Error al leer el archivo de configuración.")
    }
}

fun riskyOperation(value: Int): Int {
    if (value < 0) {
        throw IllegalArgumentException("No se aceptan valores negativos.")
    }
    else{
    return 0 + value
    }
}

```

Ahora vamos a explicar los métodos que se han usado en el ejemplo:

### Clase `MainApp`:

```kotlin
class MainApp {
    val intro: String
        get() = "Demostración de gestión de errores en Kotlin"
}
```

- Define una propiedad de solo lectura intro.

- Al acceder a intro, devuelve un texto introductorio.

- Se usa en main() para imprimir un mensaje al iniciar el programa.

###  Clase personalizada de excepción: `InvalidInputException`:

```kotlin
class InvalidInputException(message: String) : Exception(message)
```


- Define una excepción personalizada que hereda de Exception.

- Se utiliza para lanzar errores cuando un input no puede convertirse a número entero.

- Mejora la claridad del error en lugar de usar NumberFormatException directamente.

###  Función: `parseInteger(input: String): Int`:

```kotlin
fun parseInteger(input: String): Int {
    return try {
        input.toInt()
    } catch (e: NumberFormatException) {
        throw InvalidInputException("El valor '$input' no es un número válido.")
    }
}
```

- Intenta convertir una cadena de texto en número entero.

- Si falla (NumberFormatException), lanza una InvalidInputException con un mensaje más comprensible.

- Ejemplo de manejo de excepciones usando try-catch.


###  Función: `accessElement(index: Int): Int`:

```kotlin
fun accessElement(index: Int): Int {
    val data = listOf(10, 20, 30)
    return data[5]
}
```

- Intenta acceder a un índice de una lista fija.

- Si el índice está fuera de rango, lanzará automáticamente IndexOutOfBoundsException.

- Útil para demostrar errores de acceso a estructuras de datos.

###  Función: `readConfig(path: String): String`:

```kotlin
fun readConfig(path: String): String {
    try {
        return File(path).readText()
    } catch (e: IOException) {
        throw IOException("Error al leer el archivo de configuración.")
    }
}
```

- Intenta leer el contenido de un archivo desde un path.

- Si ocurre un error de entrada/salida (IOException), lanza una excepción con un mensaje más claro.

- Ideal para mostrar cómo envolver excepciones de bajo nivel.

###  Función: `riskyOperation(value: Int): Int`:

```kotlin
fun riskyOperation(value: Int): Int {
    if (value < 0) {
        throw IllegalArgumentException("No se aceptan valores negativos.")
    }
    else{
    return 0 + value
    }
}
```

- Si el valor es negativo, lanza una IllegalArgumentException.

- Muestra cómo validar argumentos y manejar posibles fallos lógicos.

## Código de test

[*test.kt*](src/test/kotlin/app/test.kt)
```` kotlin
package app

import kotlin.test.*
import java.io.IOException

class MainTest {

    @Test
    fun testGreetingIsNotNull() {
        assertNotNull(MainApp().intro, "app tiene un saludo")
    }

    @Test
    fun testInvalidInputThrowsException() {
        val ex = assertFailsWith<InvalidInputException> {
            parseInteger("xyz")
        }
        assertEquals("El valor 'xyz' no es un número válido.", ex.message)
    }

    @Test
    fun testOutOfBoundsAccess() {
        assertFailsWith<IndexOutOfBoundsException> {
            accessElement(15)
        }
    }
    @Test
    fun testIOExceptionOnFileRead() {
        val ex = assertFailsWith<IOException> {
            readConfig("no_existe.txt")
        }
        assertEquals("Error al leer el archivo de configuración.", ex.message)
    }

    @Test
    fun testIllegalArgumentException() {
        assertFailsWith<IllegalArgumentException> {
            riskyOperation(-5)
        }
    }
}
````


###  ```Test testGreetingIsNotNull```:
``` kotlin
@Test
    fun testGreetingIsNotNull() {
        assertNotNull(MainApp().intro, "app tiene un saludo")
    }

```

- Verifica que la propiedad intro de la clase MainApp no sea nula. Asegura que haya un mensaje de introducción disponible.

###  ```Test testInvalidInputThrowsException```:

```kotlin
    @Test
    fun testInvalidInputThrowsException() {
        val ex = assertFailsWith<InvalidInputException> {
            parseInteger("xyz")
        }
        assertEquals("El valor 'xyz' no es un número válido.", ex.message)
    }
```

- Intenta convertir una cadena inválida ("xyz") a entero. Se espera que lance InvalidInputException con un mensaje personalizado.

###  ``` Test testOutOfBoundsAccess```:

```kotlin
    @Test
    fun testOutOfBoundsAccess() {
        assertFailsWith<IndexOutOfBoundsException> {
            accessElement(15)
        }
    }
```

- Intenta acceder a un índice fuera de rango en una lista. Verifica que se lance IndexOutOfBoundsException.

###  ```Test testIOExceptionOnFileRead```:

```kotlin
    @Test
    fun testIOExceptionOnFileRead() {
        val ex = assertFailsWith<IOException> {
            readConfig("no_existe.txt")
        }
        assertEquals("Error al leer el archivo de configuración.", ex.message)
    }
```

- 	Intenta leer un archivo inexistente. Verifica que se lance IOException con un mensaje personalizado.

###  ```Test testIllegalArgumentException``` :

```kotlin
    @Test
    fun testIllegalArgumentException() {
        assertFailsWith<IllegalArgumentException> {
            riskyOperation(-5)
        }
    }
```

- 	Ejecuta una operación con un valor negativo. Verifica que se lance IllegalArgumentException indicando que los valores negativos no son permitidos.


## Ejecución Test
Para ejecutar el código y pasar los test de dicho código, realiza los siguientes pasos detallados que incluyen la creación de un Jenkinsfile, creación pipeline y ejecución del pipeline

### 1. Creación Jenkinsfile
A continuación, hemos creado el Jenkinsfile necesario para realizar el pipeline, este se encuentra en la carpeta con el resto de código. Es importante que esta tenga un agente que permite ejecutar en Kotlin junto a Gradle.

```Jenkinsfile
pipeline {
    agent {
        docker {
            image 'gradle:jdk17'  // imagen oficial con Gradle y JDK 17
            args '-v $HOME/.gradle:/home/gradle/.gradle'  // para cachear dependencias
        }
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'chmod +x ./gradlew'  // si usas gradlew local
                sh './gradlew clean test' // ejecuta build y pruebas
            }
        }
    }

    post {
        always {
            junit '**/build/test-results/test/*.xml'  // recoge reportes JUnit
        }
    }
}
```

### 2. Crear Pipeline
Una vez realizados los pasos anteriores, abrimos Jenkins y creamos un nuevo Pipeline. Para ello: 

 - Lo definimos como `Pipeline script from SCM` y como SCM seleccionamos `Git`.
 - Ponemos la siguiente URL: `https://github.com/uca-iiss/WIRTH-impl-25`.
 - En branch ponemos `*/feature-errores`.
 - Por último, en Script Path añadimos `temas/errores/kotlin/Jenkinsfile`

Y con esta configuración hemos creado el pipeline necesario para la ejecución de los test

### 3. Ejecutar los Tests
Una vez creado el pipeline, ejecutamos dando a `Construir ahora` y el propion Jenkins pasará los test automaticamente.