# Tema A: Herencia (Scala) - Práctica IISS

Este proyecto muestra un ejemplo avanzado del uso de **herencia** en Scala, siguiendo buenas prácticas de diseño orientado a objetos y aplicando conceptos como *abstracción*, *cohesión*, *acoplamiento bajo*, *traits* como mixins, *overriding*, y la integración de pruebas y pipelines CI/CD con Jenkins.

## Estructura del proyecto

```
temas/
└── herencia/
    └── scala-02/
        ├── build.sbt
        ├── Dockerfile
        ├── Jenkinsfile
        ├── project/
        ├── src/
        │   ├── main/scala/       ← Clases del ejemplo
        │   └── test/scala/       ← Pruebas con ScalaTest
        └── infra/                ← Terraform y despliegue Jenkins
            ├── main.tf
            ├── variables.tf
            └── outputs.tf
```

## Objetivo del ejemplo

El sistema modela una jerarquía de personas en un entorno académico:

- `Persona`: clase abstracta base.
- `Estudiante`, `Profesor`, `Investigador`: clases derivadas con comportamiento específico.
- Uso de traits `Academico` y `Evaluador` para demostrar composición con herencia múltiple (mixins).
- Aplicación de `override` en métodos concretos.

---

## Ejecución del ejemplo

### 1. Construcción de la imagen Docker

Desde la raíz del proyecto:

```bash
cd temas/herencia/scala-02
docker build -t scala-herencia:dev .
```

### 2. Compilación del código

```bash
docker run --rm -v "$PWD":/app -w /app scala-herencia:dev sbt compile
```

### 3. Ejecución de pruebas

```bash
docker run --rm -v "$PWD":/app -w /app scala-herencia:dev sbt test
```

---

## Integración continua con Jenkins

Este ejemplo se integra en un pipeline de Jenkins para compilar, testear y empaquetar automáticamente el código.

### 4. Despliegue de Jenkins con Terraform

Desde la carpeta `infra/`:

```bash
cd temas/herencia/scala-02/infra
terraform init
terraform apply
```

> Esto desplegará Jenkins y Docker-in-Docker en contenedores, con credenciales y red definidas en `main.tf`.

### 5. Acceso a Jenkins

Una vez desplegado, Jenkins estará accesible en `http://localhost:8080`.

#### Contraseña inicial

Consultar con:

```bash
docker exec -it jenkins-herencia cat /var/jenkins_home/secrets/initialAdminPassword
```

Después de introducir la contraseña:
- Aceptar la opción **"Instalar los plugins recomendados"**.
- Skip and continue as Admin > Start using Jenkins
- Cuando Jenkins esté listo, ir a **Manage Jenkins > Manage Plugins > Available plugins** y buscar e instalar el plugin llamado **Docker Pipeline**.

---

### 6. Creación del pipeline en Jenkins

1. En el panel de control de Jenkins, hacer clic en **"New Item"**.
2. Introducir un nombre como `Scala-Herencia-Pipeline`.
3. Seleccionar **"Pipeline"** y pulsar **OK**.
4. En la sección **Pipeline**, configurar:
   - En **Definition**, seleccionar: `Pipeline script from SCM`.
   - En **SCM**, seleccionar: `Git`.
   - En **Repository URL**, poner: `https://github.com/uca-iiss/impl-25-McCARTHY`
   - En **Branch Specifier**, escribir: `*/main`
   - En **Script Path**, poner: `temas/herencia/scala-02/Jenkinsfile`
5. Guardar y lanzar el pipeline (Build now).

---

## Detalle de las clases

### `Persona` [`src/main/scala/Persona.scala`](./src/main/scala/Persona.scala)  

Clase abstracta base con los atributos comunes (`nombre`, `edad`) y un método abstracto `saludar()`.

```scala
abstract class Persona(val nombre: String, val edad: Int) {
  def saludar(): String
}
```

---

### `Estudiante` [`src/main/scala/Estudiante.scala`](./src/main/scala/Estudiante.scala)  

Hereda de `Persona` y del trait `Academico`. Redefine el método `saludar()` y proporciona el nombre del departamento.

```scala
class Estudiante(nombre: String, edad: Int, val universidad: String)
  extends Persona(nombre, edad) with Academico {

  def departamento: String = "Estudios Generales"
  def saludar(): String = s"Hola, soy $nombre, tengo $edad años y estudio en $universidad."
}
```

---

### `Profesor` [`src/main/scala/Profesor.scala`](./src/main/scala/Profesor.scala)  

Hereda de `Persona` e implementa dos traits: `Academico` y `Evaluador`. Redefine `saludar()` y ofrece funcionalidad de evaluación.

```scala
class Profesor(nombre: String, edad: Int, val departamento: String)
  extends Persona(nombre, edad) with Academico with Evaluador {

  def saludar(): String = s"Hola, soy el profesor $nombre del departamento de $departamento."
}
```

---

### `Investigador` [`src/main/scala/Investigador.scala`](./src/main/scala/Investigador.scala)  

Extiende `Persona`, introduce una propiedad `especialidad` y ofrece diferentes formas de saludo.

```scala
class Investigador(nombre: String, edad: Int, val especialidad: String)
  extends Persona(nombre, edad) {

  def saludar(): String = s"Soy $nombre, investigador en $especialidad."
  def saludar(formal: Boolean): String =
    if (formal) s"Buenas, mi nombre es Dr. $nombre, especialista en $especialidad."
    else saludar()

  def presentarse(): String = saludar(formal = true)
}
```

---

### Traits

`Academico` [`src/main/scala/Academico.scala`](./src/main/scala/Academico.scala)  

```scala
trait Academico {
  def departamento: String
  def infoAcademica(): String = s"Perteneciente al departamento de $departamento"
}
```

`Evaluador`[`src/main/scala/Evaluador.scala`](./src/main/scala/Evaluador.scala)  
```scala
trait Evaluador {
  def evaluar(): String = "Realizando evaluación de estudiantes..."
}
```

---

## Infraestructura del Proyecto

En esta sección se detallan los archivos usados para contenerizar el entorno, automatizar la ejecución del proyecto Scala y desplegar Jenkins mediante Terraform y Docker.

---

### `Dockerfile` [`/Dockerfile`](./Dockerfile)  

Este fichero define la **imagen personalizada de Docker** que se usará para compilar, testear y empaquetar el proyecto Scala.

```dockerfile
FROM hseeberger/scala-sbt:17.0.4.1_1.7.1_2.13.10

WORKDIR /app

COPY . .

RUN sbt update

CMD ["sbt"]
```

Explicación:

Usa una imagen base que incluye Java 17, Scala 2.13.10 y SBT 1.7.1.

Establece /app como directorio de trabajo.

Copia el código fuente del proyecto al contenedor.

Ejecuta sbt update para resolver dependencias.

Define sbt como comando por defecto al arrancar el contenedor.

### 'Jenkinsfile' [`/Jenkinsfile`](./Jenkinsfile)  

Este fichero describe el pipeline declarativo que Jenkins ejecutará para automatizar el flujo de integración continua.

```groovy
pipeline {
  agent any

  environment {
    IMAGE = "scala-herencia:dev"
  }

  stages {
    stage('Compilar') {
      steps {
        sh 'docker inspect -f . $IMAGE || docker build -t $IMAGE temas/herencia/scala'
        sh 'docker run --rm -v $PWD:/app -w /app $IMAGE sbt compile'
      }
    }

    stage('Test') {
      steps {
        sh 'docker run --rm -v $PWD:/app -w /app $IMAGE sbt test'
      }
    }

    stage('Empaquetar') {
      steps {
        sh 'docker run --rm -v $PWD:/app -w /app $IMAGE sbt package'
      }
    }
  }

  post {
    always {
      echo 'Pipeline finalizado.'
    }
    failure {
      echo 'El pipeline falló. Revisa los logs anteriores.'
    }
  }
}
```

Explicación:

El pipeline consta de tres fases:

- Compilar: Construye la imagen (si no existe) y ejecuta sbt compile.

- Test: Ejecuta las pruebas unitarias.

- Empaquetar: Genera el archivo .jar del proyecto.

El uso de --rm garantiza limpieza tras cada ejecución.

### `infra/main.tf` [`infra/main.tf`](./infra/main.tf)  
Archivo principal de Terraform para construir y desplegar Jenkins contenerizado con soporte para Docker-in-Docker.

```hcl
terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.2"
    }
  }
}

provider "docker" {}

resource "docker_image" "jenkins" {
  name         = "jenkins/jenkins-herencia:custom"
  build {
    context    = "${path.module}/jenkins"
    dockerfile = "Dockerfile"
  }
}

resource "docker_container" "jenkins" {
  name  = "jenkins-herencia"
  image = docker_image.jenkins.image_id
  ports {
    internal = 8080
    external = 8080
  }
  ports {
    internal = 50000
    external = 50000
  }
  volumes {
    host_path      = "/var/run/docker.sock"
    container_path = "/var/run/docker.sock"
  }
}
```

Explicación:

Despliega Jenkins como un contenedor Docker usando infraestructura como código.

Usa una imagen personalizada que se construye automáticamente.

Expone los puertos necesarios (8080, 50000) para acceder a Jenkins.

Monta el socket de Docker para permitir la ejecución de contenedores desde dentro de Jenkins (Docker-in-Docker).

### `infra/variables.tf` [`infra/variables.tf`](./infra/variables.tf) 

Variables reutilizables para parametrizar los recursos definidos en Terraform.

```hcl
variable "jenkins_image_name" {
  description = "Nombre de la imagen personalizada de Jenkins"
  default     = "jenkins/jenkins-herencia:custom"
}

variable "jenkins_container_name" {
  description = "Nombre del contenedor Jenkins"
  default     = "jenkins-herencia"
}
```

Explicación:

Permiten modificar el nombre de la imagen o del contenedor fácilmente sin cambiar main.tf.

Mejora la legibilidad y mantenimiento de la infraestructura.

### `infra/outputs.tf` [`infra/outputs.tf`](./infra/outputs.tf) 
Muestra información útil al final de la ejecución de Terraform.

```hcl
output "jenkins_url" {
  value       = "http://localhost:8080"
  description = "URL local de acceso a Jenkins"
}
```

Explicación:

Tras ejecutar terraform apply, se imprime automáticamente la URL local para acceder a Jenkins.

---

## Archivos importantes

| Archivo                            | Descripción                                                                 |
|------------------------------------|-----------------------------------------------------------------------------|
| [`src/main/scala`](./src/main/scala)     | Código fuente Scala                                                        |
| [`src/test/scala`](./src/test/scala)     | Tests automatizados con ScalaTest                                          |
| [`build.sbt`](./build.sbt)               | Configuración del proyecto Scala                                           |
| [`Dockerfile`](./Dockerfile)             | Imagen personalizada con Scala y SBT                                       |
| [`Jenkinsfile`](./Jenkinsfile)           | Definición del pipeline de Jenkins                                         |
| [`infra/main.tf`](./infra)               | Infraestructura como código con Terraform                                  |


---

## Limpieza de imágenes y contenedores usados

Para evitar dejar imágenes y contenedores ocupando espacio en tu sistema, puedes usar los siguientes comandos para limpiar solo los recursos de este proyecto:

```bash
# Borrar contenedor Jenkins (si existe)
docker rm -f jenkins-herencia

# Borrar imagen personalizada de Jenkins
docker rmi jenkins/jenkins-herencia

# Borrar imagen de desarrollo de Scala
docker rmi scala-herencia:dev
```


