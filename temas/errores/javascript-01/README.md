# Tema C.1 - Errores

Este documento explora la implementación de un sistema robusto de manejo de **errores** en JavaScript, demostrando diferentes estrategias para capturar, procesar y responder a errores en aplicaciones Node.js. El proyecto incluye ejemplos prácticos de manejo de valores no definidos, errores de procesamiento de álbumes musicales y técnicas de validación de datos.

---

## Código fuente

### [Model](album-handling-js/model.js) (clases base):

- **Track:**
  - `title`: título de la pista musical (privado).
  - `duration`: duración en minutos (privado).
  - `getTitle()`: devuelve el título de la pista.
  - `getDuration()`: devuelve la duración de la pista.

- **Album:**
  - `name`: nombre del álbum (privado).
  - `artist`: artista del álbum (privado).
  - `year`: año de lanzamiento (privado).
  - `getName()`: devuelve el nombre del álbum.
  - `getArtist()`: devuelve el artista del álbum.
  - `getYear()`: devuelve el año de lanzamiento.

- **Uso:**
  - Clases fundamentales que representan el modelo de datos básico para álbumes musicales y sus pistas.

---

### [Database](album-handling-js/database.js) (simulador de base de datos):

- **Datos:**
  - `albumDatabase`: colección de álbumes con información básica.
  - `trackDatabase`: colección de pistas organizadas por álbum.

- **Métodos:**
  - `getAlbum(name)`: obtiene un álbum por nombre, retorna null si no existe.
  - `getAlbumTracks(albumName)`: obtiene las pistas de un álbum, retorna null si no existen.
  - `getTracksDuration(tracks)`: calcula la duración total de un array de pistas.

- **Uso:**
  - Simula una base de datos real con casos edge como álbumes sin pistas para demostrar manejo de valores null.

---

### [Traditional Service](services/traditional-service.js) (enfoque tradicional):

- **Métodos:**
  - `getDurationOfAlbumWithNameTraditional(name)`: calcula la duración total de un álbum usando comprobaciones explícitas de null con if/else.

- **Características:**
  - Manejo explícito de casos null/undefined con estructuras condicionales.
  - Retorna 0 si el álbum no existe o no tiene pistas.
  - Uso de bucles for tradicionales para sumar duraciones.

- **Uso:**
  - Demuestra el enfoque clásico de JavaScript para manejar valores potencialmente nulos.

---

### [Modern Service](services/modern-service.js) (enfoque moderno):

- **Métodos:**
  - `getDurationOfAlbumWithNameModern(name)`: versión extendida con sintaxis moderna.
  - `getDurationOfAlbumWithNameModernCompact(name)`: versión compacta usando encadenamiento opcional y operador de coalescencia nula.

- **Características:**
  - Utiliza operadores `?.` (optional chaining) y `??` (nullish coalescing).
  - Manejo funcional con `reduce()` para calcular totales.
  - Código más conciso y legible.

- **Uso:**
  - Demuestra las características modernas de JavaScript ES2020+ para manejo seguro de valores null/undefined.

---

### [Optional Service](services/optional-service.js) (patrón Optional):

- **Métodos:**
  - `getAlbumOptional(name)`: envuelve la búsqueda de álbum en un Optional.
  - `getAlbumTracksOptional(albumName)`: envuelve la búsqueda de pistas en un Optional.
  - `getDurationOfAlbumWithNameOptional(name)`: calcula duración usando encadenamiento de Optional.

- **Características:**
  - Usa la clase Optional personalizada para manejo elegante de valores nulos.
  - Encadenamiento fluido con `flatMap()` y `map()`.
  - Valor por defecto con `orElse()`.

- **Uso:**
  - Implementa el patrón Optional de Java/Scala en JavaScript para evitar null pointer exceptions.

---

### [Optional](services/optional.js) (clase contenedora):

- **Métodos estáticos:**
  - `Optional.of(value)`: crea un Optional con valor no nulo (lanza error si es null).
  - `Optional.ofNullable(value)`: crea un Optional que acepta valores null.
  - `Optional.empty()`: crea un Optional vacío.

- **Métodos de instancia:**
  - `isPresent()`: verifica si contiene un valor.
  - `get()`: obtiene el valor (lanza error si está vacío).
  - `orElse(other)`: devuelve el valor o un alternativo si está vacío.
  - `map(mapper)`: aplica una función al valor si está presente.
  - `flatMap(mapper)`: aplica una función que devuelve Optional.

- **Uso:**
  - Crea una clase que envuelve valores opcionales y ofrece métodos seguros para manipularlos sin errores de null/undefined.

---

### [Stream Processing](services/stream-processing.js) (procesamiento funcional):

- **Métodos:**
  - `showAlbumsAfterYear(year)`: filtra y muestra álbumes posteriores a un año.
  - `findOldestAlbum()`: encuentra el álbum más antiguo usando `reduce()`.
  - `calculateTotalDuration()`: suma las duraciones de todos los álbumes.
  - `findLongestTrack()`: encuentra la pista más larga usando `flatMap()` y `reduce()`.

- **Características:**
  - Uso extensivo de métodos funcionales como `filter()`, `map()`, `reduce()`, `flatMap()`.
  - Procesamiento de streams de datos con transformaciones encadenadas.
  - Manejo robusto de colecciones vacías.

- **Uso:**
  - Demuestra técnicas avanzadas de procesamiento funcional para análisis de datos musicales.

---

## Ficheros de configuración del proyecto `Node.js`

- [package.json](package.json)

---

## Conceptos clave

### Manejo de Errores Tradicional

Utiliza comprobaciones explícitas con estructuras condicionales (`if/else`) para verificar la existencia de valores antes de su uso, evitando errores de referencia nula.

---

### Manejo de Errores Moderno

Emplea características de JavaScript ES2020+ como el encadenamiento opcional (`?.`) y el operador de coalescencia nula (`??`) para prevenir errores de manera más elegante y concisa.

---

### Patrón Optional

Implementa un wrapper inspirado en Java/Scala que encapsula valores que pueden ser null o undefined, proporcionando métodos seguros (`map`, `flatMap`, `orElse`) para su manipulación sin riesgo de errores.

---

### Procesamiento Funcional de Streams

Utiliza métodos de arrays como `filter()`, `map()`, `reduce()` y `flatMap()` para procesar colecciones de datos de manera declarativa, con manejo automático de casos edge como arrays vacíos.

---

## Pruebas de Manejo de Errores

El proyecto incluye un conjunto completo de pruebas (`functional-tests.js`) que verifican:

- Comportamiento correcto ante datos válidos
- Respuesta apropiada a datos inválidos o inexistentes
- Manejo de casos edge (álbumes sin tracks, valores null/undefined)
- Consistencia entre diferentes enfoques de manejo de errores
- Precisión en cálculos agregados y búsquedas
- Robustez del procesamiento funcional ante colecciones vacías

---

### [Functional Tests](test/functional-tests.js) (suite de pruebas):

- **Clase TestRunner:**
  - `addTest(name, testFunction)`: registra un test para ejecución.
  - `runAll()`: ejecuta todos los tests registrados.
  - `assert(condition, message)`: verificación básica de condiciones.
  - `assertEquals(actual, expected, message)`: comparación exacta de valores.
  - `assertApproximately(actual, expected, tolerance, message)`: comparación con tolerancia para números decimales.

- **Tests implementados:**
  - Validación de los tres enfoques (tradicional, moderno, optional) con álbumes existentes, inexistentes y sin pistas.
  - Tests de procesamiento de streams para verificar cálculos agregados.
  - Cobertura completa de casos edge y manejo de errores.

- **Uso:**
  - Suite de pruebas automatizada que valida el comportamiento correcto de todos los servicios ante diferentes escenarios y casos límite.

---

## Ficheros de configuración 

### [Dockerfile](Dockerfile)
Define la imagen de Docker utilizada en el entorno del proyecto.

### [main.tf](main.tf)
Archivo principal de configuración de infraestructura en Terraform.

### [Jenkinsfile](Jenkinsfile)
Contiene la configuración del pipeline de integración continua (CI) usando Jenkins.

## Despliegue
Se requiere descargar la imagen necesaria para llevar a cabo el despliegue:
````bash
docker build -t myjenkins-js .
````

Para realizar el despliegue, se debe ejecutar el siguiente script. Este creará los recursos necesarios en Docker y ejecutará los comandos correspondientes de Terraform:
````bash
python ./deploy_jenkins.py
````

Una vez realizado este paso, podemos usar Jenkins en el localhost.

Para liberar los recursos, es necesario realizar los siguientes comandos.
````bash
docker stop <nombre-contenedor-dind>
docker stop <nombre-contenedor-jenkins>
docker rm <nombre-contenedor-dind>
docker rm <nombre-contenedor-jenkins>
docker network rm <id-network-jenkins>
````
