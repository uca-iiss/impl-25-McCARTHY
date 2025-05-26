# Ejemplo de uso de Herencia con python

Este documento describe:

- Cómo hemos implementado un ejemplo de herencia utilizando Python.
- Batería de pruebas y automatización con pytest.
- Cómo se construye una imagen personalizada de Jenkins con python y se crean los contenedores.
- Ejecución de pruebas automatizadas usando un `Jenkinsfile`.

---

## Análisis de herencia con python

La **abstracción** es un mecanismo de la programación orientada a objetos (POO) que permite a una clase (clase hija o derivada) heredar las características (atributos y métodos) de otra clase (clase padre o base).

En el ejemplo que veremos, hemos implementado una clase abstracta base llamada Animal, de la que heredan otras como Perro o Gato. Utilizamos herencia para:

- Estructurar la orientación a objetos.
- Facilitar la escalabilidad.
- Ocultar información y abstraerse.

---

## Explicación del código en python

Este proyecto define una clase llamada `Animal`, de la que heredan las clases `Gato`, `Perro` y `Pajaro`. Cada una en un archivo aparte.

---

### Clases

### 'Animal'

```python
from abc import ABC, abstractmethod

class Animal(ABC):
    def __init__(self, name: str):
        self.name = name

    @abstractmethod
    def hacer_sonido(self) -> str:
        pass

    @abstractmethod
    def describir(self) -> str:
        pass
```

Clase abstracta de la que heredarán las demás. Al ser abstracta no se puede instanciar y es por ello que sus métodos deben ser definidas en las clases hijas. Define un constructor que inicializa su nombre.


### 'Gato'

```python
from .animal import Animal

class Gato(Animal):
    def __init__(self, nombre: str):
        super().__init__(nombre)

    def hacer_sonido(self) -> str:
        return "Miau"

    def describir(self) -> str:
        return f"Soy un gato y me llamo {self.name}"
```

Clase hija que redefine los métodos abstractos.

### 'Perro'

```python
from .animal import Animal

class Perro(Animal):
    def __init__(self, nombre: str):
        super().__init__(nombre)

    def hacer_sonido(self) -> str:
        return "Guau"

    def describir(self) -> str:
        return f"Soy un perro y me llamo {self.name}"
```

Clase hija que redefine los métodos abstractos.

### 'Pajaro'

```python
from .animal import Animal

class Pajaro(Animal):
    def __init__(self, nombre: str):
        super().__init__(nombre)

    def hacer_sonido(self) -> str:
        return "Pío"

    def describir(self) -> str:
        return f"Soy un pájaro y me llamo {self.name}"
```

Clase hija que redefine los métodos abstractos.

**Nota:** Las clases hijas no agregan métodos nuevos ni modifican lo que reciben ya que esto puede dar problemas a largo plazo. 

## Ventajas de la herencia

1. **Extensibilidad:** Facilita la extensión del comportamiento de las clases.

2. **Reutilización de código:** Facilita la creación de componentes modulares y reutilizables, mejorando la eficiencia en el desarrollo.

3. **Polimorfismo:** Permite tratar objetos de diferentes clases derivadas de la misma clase base de manera uniforme, haciendo el código más flexible y escalable.

---

Ahora pasaremos a ver el programa de pruebas implementada en el directorio de [tests](animales/tests) con el uso de pytest.

---

## Casos de prueba

### 1. Comprobación de métodos y si es instancia de la superclase de Perro

```python
def test_perro():
    rex = Perro("Rex")
    assert (rex.hacer_sonido(), "Guau")
    assert (rex.describir(), "Soy un perro y me llamo Rex")
    assert isinstance(rex, Animal)  # Verifica que Perro es un Animal
```

- El constructor inicializa el nombre
- Comprobación de que hace sonido de perro
- Comprobación de que se describe correctamente
- Comprobación de que es una instancia de Animal
---

### 2. Comprobación de métodos y si es instancia de la superclase de Gato

```python
def test_gato():
    misu = Gato("Misu")
    assert (misu.hacer_sonido(), "Miau")
    assert (misu.describir(), "Soy un gato y me llamo Misu")
    assert isinstance(misu, Animal)  # Verifica que Gato es un Animal
```

- El constructor inicializa el nombre
- Comprobación de que hace sonido de gato
- Comprobación de que se describe correctamente
- Comprobación de que es una instancia de Animal

---
### 3. Comprobación de métodos y si es instancia de la superclase de Pajaro

```python
def test_pajaro():
    piolin = Pajaro("Piolín")
    assert (piolin.hacer_sonido(), "Pío")
    assert (piolin.describir(), "Soy un pájaro y me llamo Piolín")
    assert isinstance(piolin, Animal)  # Verifica que Pájaro es un Animal
```

- El constructor inicializa el nombre
- Comprobación de que hace sonido de pájaro
- Comprobación de que se describe correctamente
- Comprobación de que es una instancia de Animal

---

Este programa sirve como un banco de pruebas básico para:

- Confirmar el correcto uso de la herencia.
- Verificar comportamientos específicos de nuestro programa.

---

## Pytest

Para la realización de pruebas automáticas en python, hemos utilizado pytest.
Simplemente necesitamos ejecutar pytest en el directorio de animales para realizar las pruebas correctamente, habiendo instalado previamente pytest.

## Dockerfile: Jenkins con python

```dockerfile
FROM jenkins/jenkins:lts

# Cambiar a usuario root para instalar dependencias del sistema
USER root

# Instalar dependencias necesarias para Ruby y compilación de gemas
RUN apt-get update && \
    apt-get install -y \ 
        python3 \
        python3-pip \
        python3-venv && \
    apt-get clean && \
    rm -rf /var/lib/apt/list/*

# Crear entorno virtual
RUN python3 -m venv /opt/venv

# Activar entorno virtual y actualizar pip
RUN /opt/venv/bin/pip install --upgrade pip

# Copiar requirements.txt al contenedor
COPY requirements.txt /tmp/requirements.txt

# Instalar dependencias en el entorno virtual
RUN /opt/venv/bin/pip install --no-cache-dir -r /tmp/requirements.txt

# Añadir el entorno virtual al PATH
ENV PATH="/opt/venv/bin:$PATH"

# Volver al usuario Jenkins
USER jenkins

# Comprobar que todo esté correcto
RUN python3 --version && pip --version
```

- Parte de la imagen oficial 'jenkins/jenkins:lts'.
- Instala python3 y herramientas de desarrollo necesarias.
- Realizamos un entorno virtual con pytest para mayor seguridad.
- Instalamos dependencias en ese entorno.
- Vuelve al usuario 'jenkins' para mayor seguridad al ejecutar procesos.

---

## Creación del contenedor Jenkins personalizado

### 1. Construir la imagen

```bash
docker build -t my-jenkins-image .
```

### 2. Crear y ejecutar el contenedor mediante Terraform

Mediante un main.tf el cual incluirá todo lo necesario para poder proceder con la instalación de los contenedores de Jenkins, podremos tener los contenedores funcionales y operativos colocando los comandos de **Terraform Init** y **Terraform Apply**

terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {
  host = "npipe:////./pipe/docker_engine"
}

# Crear una red Docker para Jenkins y DinD
resource "docker_network" "ci_network" {
  name = "ci_network"
}

# Imagen Docker-in-Docker
resource "docker_image" "dind" {
  name = "docker:dind"
}

# Contenedor Docker-in-Docker
resource "docker_container" "dind" {
  image = docker_image.dind.image_id
  name  = "dind"
  privileged = true
  restart    = "always"

  env = [
    "DOCKER_TLS_CERTDIR="
  ]

  networks_advanced {
    name = docker_network.ci_network.name
  }

  ports {
    internal = 2375
    external = 2375
  }
}

# Contenedor Jenkins usando la imagen personalizada creada previamente
resource "docker_container" "jenkins" {
  image   = "my-jenkins-image"  # Usamos la imagen creada manualmente
  name    = "jenkins"
  restart = "always"

  ports {
    internal = 8080
    external = 8080
  }

  networks_advanced {
    name = docker_network.ci_network.name
  }

  # Esto permite que Jenkins use Docker del host
  volumes {
    host_path      = "/var/run/docker.sock"
    container_path = "/var/run/docker.sock"
  }
}

Una vez ejecutado, ahora ya tendremos los contenedores de Jenkins funcionales, ahora procederemos a explicar el JenkinsFile escogido para el lanzamiento de la aplicación mediante un pipeline.


## Jenkinsfile para CI/CD en python y pytest

```groovy
pipeline {
    agent any

    stages {
        stage('Preparar entorno') {
            steps {
                echo 'Preparando entorno Python...'
            }
        }

        stage('Instalar dependencias') {
            steps {
                echo 'Instalando dependencias...'
                dir('temas/herencia/python/JenkinsDespliegue') {
                    sh 'pip install -r requirements.txt'
                }
            }
        }

        stage('Ejecutar pruebas') {
            steps {
                echo 'Ejecutando pruebas con pytest...'
                dir('temas/herencia/python/animales') {
                    sh 'pytest'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado.'
        }
        failure {
            echo 'La ejecución falló.'
        }
        success {
            echo 'La ejecución fue exitosa.'
        }
    }
}
```

'Instalar dependencias' Se encarga de instalar las dependencias de requirements.txt. 
'Ejecutar pruebas' Se encarga de realizar los tests con pytest. 
'post'  Define acciones según el resultado: éxito o fallo. 

Este setup permite:

- Ejecutar Jenkins en Docker con python3 y pytest preinstalado.
- Correr pruebas automatizadas de python usando `pytest`.
- Integrar pruebas a pipelines CI/CD de forma simple y portable.

---