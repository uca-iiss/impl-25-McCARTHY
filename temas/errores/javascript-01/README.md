# Sistema de Manejo de Errores en JavaScript

Este documento explora la implementación de un sistema robusto de manejo de errores en JavaScript, demostrando diferentes estrategias para capturar, procesar y responder a errores en aplicaciones Node.js. El proyecto incluye ejemplos prácticos de manejo de valores no definidos, errores de procesamiento de álbumes musicales y técnicas de validación de datos.

---

## Clases principales
- `./album-handling-js/model.js`: Define las clases base `Track` y `Album` con validación de entrada
- `./album-handling-js/database.js`: Simula una base de datos con manejo de casos edge y errores
- `./services/traditional-service.js`: Implementa manejo de errores tradicional con try-catch
- `./services/modern-service.js`: Utiliza técnicas modernas de manejo de errores con Optional patterns
- `./services/optional-service.js`: Demuestra el patrón Optional para evitar valores null/undefined
- `./services/optional.js`: Crea una clase que envuelve valores opcionales (que pueden no existir) y ofrece métodos seguros para usarlos sin errores.
- `./services/stream-processing.js`: Procesa streams de datos con manejo robusto de errores
- `./tests/functional-tests.js`: Suite de pruebas que valida el comportamiento ante errores

---

## Configuración del proyecto con Node.js

El proyecto utiliza ES6 modules y está configurado para ejecutarse con Node.js moderno. El archivo `package.json` define:

- Configuración de módulos ES6 con `"type": "module"`
- Scripts de construcción y pruebas automatizadas
- Dependencias mínimas para maximizar la compatibilidad

```json
{
  "name": "album-handling-js",
  "version": "1.0.0",
  "type": "module",
  "scripts": {
    "start": "node ./tests/functional-tests.js",
    "build": "echo 'Build completado exitosamente' && node -c ./tests/functional-tests.js",
    "test": "node ./tests/functional-tests.js"
  }
}
```

---

## Conceptos clave

### Manejo de Errores Tradicional

Utiliza bloques `try-catch` para capturar y manejar excepciones de manera explícita.

---

### Manejo de Errores Moderno

Emplea técnicas funcionales y validaciones tempranas para prevenir errores.

---

### Patrón Optional

Implementa un wrapper que encapsula valores que pueden ser null o undefined, proporcionando métodos seguros para su manipulación.

---


## Pruebas de Manejo de Errores

El proyecto incluye un conjunto completo de pruebas (`functional-tests.js`) que verifican:

- Comportamiento correcto ante datos válidos
- Respuesta apropiada a datos inválidos
- Manejo de casos edge (álbumes sin tracks, valores null)
- Consistencia entre diferentes enfoques de manejo de errores
- Performance bajo condiciones de error

---

## CI/CD y Aprovisionamiento

### Integración continua con Jenkins

El proyecto incluye un pipeline de integración continua (`Jenkinsfile`) que automatiza la ejecución de pruebas.

### Configuración de Docker

Se incluye un `Dockerfile` para la creación de una imagen de Jenkins con todas las herramientas necesarias:
- Node.js con soporte para ES6 modules
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
# Instalar dependencias (si las hubiera)
npm install

# Ejecutar build y validación
npm run build

# Ejecutar todas las pruebas
npm test

```