```markdown
# Ejemplo Scala: Manejo de Valores No Definidos con Option y Either

Este proyecto demuestra un enfoque funcional en Scala para manejar valores no definidos y evitar el abuso de `null`, utilizando las herramientas propias del lenguaje:

- `Option` para modelar valores que pueden o no existir.
- `Either` para manejar resultados que pueden ser éxito o error con mensajes claros.
- Procesamiento funcional con `map`, `flatMap`, `collect` para transformar colecciones evitando errores comunes.

---

## Estructura del Proyecto

```

.
├── despliegue
│   ├── Dockerfile         # Imagen Docker para construir y ejecutar el proyecto
│   ├── main.tf            # Configuración Terraform para infraestructura dummy
│   ├── output.tf          # Salidas de Terraform
│   └── variables.tf       # Variables de Terraform
├── Jenkinsfile            # Pipeline básico para CI con Jenkins
├── README.md              # Este archivo
├── build.sbt              # Configuración sbt
└── src
└── main
└── scala
├── domain
│   └── UserService.scala  # Lógica de negocio con Option y Either
└── main
└── App.scala          # Punto de entrada principal

````

---

## Cómo Funciona el Código

- `UserService` (en `domain`) simula una base de datos con usuarios y métodos para:
  - Buscar usuarios por ID devolviendo `Option[String]`.
  - Procesar listas de IDs ignorando los valores no encontrados usando `Option`.
  - Buscar usuarios devolviendo `Either[String, String]` para reportar errores.
  - Procesar listas de IDs acumulando resultados exitosos y errores.
  
- `App` (en `main`) ejecuta estas funciones con una lista de IDs que incluye algunos inválidos para demostrar ambos casos.

---

## Requisitos Previos

- Docker instalado para construir y ejecutar la imagen.
- sbt instalado si quieres ejecutar localmente sin Docker.
- Jenkins configurado si quieres usar integración continua (CI).
- Terraform instalado para ejecutar el despliegue dummy.

---

## Ejecución

### Usando Docker

1. Construye la imagen Docker:

```bash
docker build -t scala-option-example ./despliegue
````

2. Ejecuta el contenedor:

```bash
docker run --rm scala-option-example
```

Deberías ver en consola la salida con los usuarios procesados, indicando los valores encontrados y los errores.

---

### Usando sbt localmente

1. En la raíz del proyecto, ejecuta:

```bash
sbt run
```

Se ejecutará el objeto `main.App` y verás la misma salida que con Docker.

---

### Pipeline Jenkins

El fichero `Jenkinsfile` define un pipeline simple que:

* Hace checkout del código.
* Compila el proyecto con `sbt compile`.
* Ejecuta tests (añade tests en el futuro para aprovechar esta etapa).
* Ejecuta la aplicación con `sbt run`.

---

### Infraestructura con Terraform

Para crear un recurso dummy local (ejemplo de integración Terraform):

1. Entra a la carpeta despliegue:

```bash
cd despliegue
```

2. Inicializa Terraform:

```bash
terraform init
```

3. Aplica la configuración:

```bash
terraform apply
```

Terraform creará un archivo local `example.txt` como recurso ejemplo.

---

## Salida esperada

```
Process users ignoring missing (Option):
List(ALICE, BOB, CHARLIE)

Process users reporting errors (Either):
Successes: List(ALICE, BOB, CHARLIE)
Errors: List(User with id 4 not found, User with id 5 not found)
```

---

## Próximos pasos

* Añadir tests unitarios con ScalaTest.
* Mejorar el pipeline de Jenkins para ejecutar tests.
* Añadir integración con una base de datos real.
* Desplegar infraestructura real con Terraform.

