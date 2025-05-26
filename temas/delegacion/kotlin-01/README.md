# Ejercicio: Delegación en Kotlin

Este proyecto demuestra el uso del patrón **delegación** en Kotlin, como parte de la práctica de la asignatura.  
El objetivo es evitar el abuso de la herencia, utilizando interfaces y delegación a través de objetos.

---

## Estructura del proyecto
/src
├── main/kotlin/
│ ├── Aplicacion.kt
│ ├── ConsoleLogger.kt
│ ├── Logger.kt
│ └── Main.kt
└── test/kotlin/
└── AplicacionTest.kt

build.gradle.kts
settings.gradle.kts
Dockerfile
Jenkinsfile
README.md

---

## ⚙️ Construcción

Para construir el proyecto:
```bash
./gradlew clean build

Pruebas

Para ejecutar las pruebas unitarias:
./gradlew test

Docker
El Dockferfile construye el proyecto usando Gradle:
docker build -t delegacion-kotlin .
docker run --rm delegacion-kotlin

Jenkins

El Jenkinsfile define un pipeline que:

1. Clona el repositorio.

2. Construye el proyecto ( ./gradlew build) .

3. Ejecuta las pruebas ( ./gradlew test) .

4. Ejecuta la aplicación. ( ./gradlew run ).

Este pipeline está listo para integrarse en Jenkins contenerizado.