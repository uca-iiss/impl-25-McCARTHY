# Ejemplo de Aspectos en C++ (simulación)

Este ejemplo muestra cómo simular aspectos (AOP) en C++ usando plantillas y funciones.

## 🧠 Idea

El archivo `AspectLogger.hpp` contiene una plantilla `withLogging()` que:
- Recibe una función
- Ejecuta código antes y después (aspecto)
- No altera el código original de la función

## 🗂️ Archivos

- `AspectLogger.hpp`: define el "aspecto" de logging
- `main.cpp`: funciones reales envueltas con el aspecto
- `Makefile`: compila el proyecto
- `Dockerfile`: ejecuta el programa en contenedor
- `Jenkinsfile`: compila y ejecuta en Jenkins

## ▶️ Ejecución local

```bash
make
./main
