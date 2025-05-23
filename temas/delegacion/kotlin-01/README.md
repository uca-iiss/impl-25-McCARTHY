# Ejemplo de Delegación en Kotlin con Jenkins

Este ejemplo muestra cómo Kotlin permite delegar interfaces usando la palabra clave `by`.

## Estructura

- `Logger.kt`: interfaz con métodos `logInfo()` y `logError()`
- `ConsoleLogger.kt`: implementación simple que imprime en consola
- `Aplicacion.kt`: clase que delega el comportamiento de `Logger`
- `main.kt`: función principal que demuestra el uso
- `build.gradle.kts`: configuración de proyecto Kotlin + Gradle
- `Dockerfile`: ejecuta el proyecto en un contenedor
- `Jenkinsfile`: compila y ejecuta el ejemplo en Jenkins

## Ejecución

```bash
./gradlew run
