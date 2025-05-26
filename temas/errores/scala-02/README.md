# 🧪 Manejo de valores opcionales con `Option` y `Either`

Este proyecto muestra cómo usar un enfoque funcional en Scala para evitar los típicos errores relacionados con valores nulos (`null`), utilizando herramientas propias del lenguaje:

* `Option`, para representar valores que pueden existir... o no.
* `Either`, para devolver resultados que pueden ser correctos o contener un error con un mensaje claro.
* Transformaciones funcionales (`map`, `flatMap`, `collect`, etc.), para trabajar con datos de forma segura y elegante.

---

## 📁 Estructura del proyecto

```
.
├── despliegue
│   ├── Dockerfile         # Imagen Docker para compilar y ejecutar el proyecto
│   ├── main.tf            # Configuración de Terraform para despliegue ficticio
│   ├── output.tf          # Salidas generadas por Terraform
│   └── variables.tf       # Variables utilizadas en Terraform
├── Jenkinsfile            # Pipeline de integración continua (CI) con Jenkins
├── README.md              # Este archivo 🙂
├── build.sbt              # Configuración del proyecto Scala (sbt)
└── src
    └── main
        └── scala
            ├── domain
            │   └── UserService.scala  # Lógica con Option y Either
            └── main
                └── App.scala          # Punto de entrada principal
```

---

## 🧠 ¿Cómo funciona?

### 🔧 `UserService` (en `domain/`):

* Simula una base de datos de usuarios.
* Contiene métodos para:

  * Buscar un usuario por ID → devuelve un `Option[String]`.
  * Procesar una lista de IDs → ignora los que no existen, usando `Option`.
  * Buscar usuarios y devolver éxito o error → usando `Either[String, String]`.
  * Acumular resultados buenos y errores → útil para informes más completos.

### 🚀 `App` (en `main/`):

* Ejecuta ejemplos con varios IDs, algunos inexistentes.
* Muestra cómo manejar tanto los casos exitosos como los fallidos.

---

## ✅ Requisitos

* Tener **Docker** instalado (para ejecutar fácilmente).
* Tener **sbt** instalado (si prefieres ejecutarlo directamente en local).
* **Jenkins** (si quieres usar la pipeline de CI).
* **Terraform** instalado (para el ejemplo de despliegue).

---

## ▶️ Cómo ejecutar el proyecto

### 🔹 Opción 1: Usando Docker

1. Construye la imagen:

```bash
docker build -t scala-option-example ./despliegue
```

2. Ejecuta el contenedor:

```bash
docker run --rm scala-option-example
```

📦 Verás por consola los resultados de procesar los usuarios, tanto los encontrados como los errores.

---

### 🔹 Opción 2: Ejecutar localmente con sbt

1. Abre una terminal en la raíz del proyecto y ejecuta:

```bash
sbt run
```

👀 El programa se lanzará y verás los mismos resultados que con Docker.

---

### 🔹 Opción 3: Usar Jenkins

El fichero `Jenkinsfile` define una pipeline básica que:

* Clona el repositorio.
* Compila el proyecto con `sbt compile`.
* Ejecuta los tests (puedes añadir más).
* Lanza la aplicación con `sbt run`.

Ideal para automatizar el ciclo de desarrollo.

---

### 🔹 Terraform (opcional)

Para simular un despliegue de infraestructura local:

1. Entra en la carpeta de despliegue:

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

🔧 Se creará un archivo `example.txt` como recurso ficticio. Es solo un ejemplo de integración con Terraform.

---

## 📤 Salida esperada

```text
Process users ignoring missing (Option):
List(ALICE, BOB, CHARLIE)

Process users reporting errors (Either):
Successes: List(ALICE, BOB, CHARLIE)
Errors: List(User with id 4 not found, User with id 5 not found)
```

---

## 🔮 ¿Qué se puede mejorar?

* ✅ Añadir pruebas unitarias con ScalaTest.
* 🔧 Ampliar la pipeline de Jenkins para ejecutarlas automáticamente.
* 🛢️ Conectar con una base de datos real.
* ☁️ Sustituir el despliegue de ejemplo por infraestructura real con Terraform.
