Perfecto, gracias por la aclaración.

A continuación te dejo un `README.md` **único y completo** para el proyecto de **`empresa.py`**, basado en herencia en Python, con pruebas automatizadas y pipeline en Jenkins con Docker y Terraform. Este fichero está listo para copiar y pegar:

---

````markdown
# 🏢 Proyecto: Empresa con Herencia en Python + CI/CD con Jenkins

Este proyecto demuestra cómo modelar una jerarquía de empleados en una empresa usando **herencia en Python**, pruebas automatizadas con **pytest**, e integración continua con **Jenkins**, todo gestionado mediante **Docker** y **Terraform**.

---

## 📌 Contenidos

- Herencia y clases abstractas en Python.
- Pruebas unitarias con `pytest`.
- Contenedor Docker con Jenkins + Python.
- Jenkinsfile para CI/CD.
- Despliegue automatizado con `Terraform`.

---

## 🧠 Lógica de negocio

El archivo principal `empresa.py` define una clase base `Empleado` y tres clases hijas:

- `Desarrollador`
- `Gerente`
- `Diseñador`

Cada clase hija hereda de `Empleado` y redefine su comportamiento mediante **métodos polimórficos**.

### Estructura de clases

```python
from abc import ABC, abstractmethod

class Empleado(ABC):
    def __init__(self, nombre: str, salario: float):
        self.nombre = nombre
        self.salario = salario

    @abstractmethod
    def calcular_bonus(self) -> float:
        pass

class Desarrollador(Empleado):
    def calcular_bonus(self) -> float:
        return self.salario * 0.10

class Gerente(Empleado):
    def calcular_bonus(self) -> float:
        return self.salario * 0.20

class Diseñador(Empleado):
    def calcular_bonus(self) -> float:
        return self.salario * 0.15
````

---

## ✅ Pruebas con Pytest

Las pruebas están en el archivo `tests/tests_empresa.py`:

```python
from empresa import Desarrollador, Gerente, Diseñador

def test_bonus_desarrollador():
    d = Desarrollador("Ana", 3000)
    assert d.calcular_bonus() == 300.0

def test_bonus_gerente():
    g = Gerente("Luis", 5000)
    assert g.calcular_bonus() == 1000.0

def test_bonus_diseñador():
    dis = Diseñador("Laura", 4000)
    assert dis.calcular_bonus() == 600.0
```

---

## 🐳 Dockerfile personalizado con Jenkins + Python

Archivo: `Dockerfile`

```dockerfile
FROM jenkins/jenkins:lts

USER root

RUN apt-get update && \
    apt-get install -y python3 python3-pip python3-venv && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

RUN python3 -m venv /opt/venv
RUN /opt/venv/bin/pip install --upgrade pip
COPY requirements.txt /tmp/requirements.txt
RUN /opt/venv/bin/pip install -r /tmp/requirements.txt

ENV PATH="/opt/venv/bin:$PATH"
USER jenkins
```

Archivo: `requirements.txt`

```
pytest
```

---

## 🔁 Jenkinsfile (CI/CD)

```groovy
pipeline {
    agent any

    stages {
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
                echo 'Ejecutando pruebas...'
                dir('temas/herencia/python/empresa') {
                    sh 'pytest'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado.'
        }
        success {
            echo 'Pruebas exitosas.'
        }
        failure {
            echo 'Falló alguna etapa del pipeline.'
        }
    }
}
```

---

## ☁️ Terraform: despliegue de contenedores

Archivo: `main.tf`

```hcl
terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {
  host = "unix:///var/run/docker.sock"
}

resource "docker_network" "ci_network" {
  name = "ci_network"
}

resource "docker_image" "dind" {
  name = "docker:dind"
}

resource "docker_container" "dind" {
  image      = docker_image.dind.image_id
  name       = "dind"
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

resource "docker_container" "jenkins" {
  image   = "my-jenkins-image"
  name    = "jenkins"
  restart = "always"

  ports {
    internal = 8080
    external = 8080
  }

  networks_advanced {
    name = docker_network.ci_network.name
  }

  volumes {
    host_path      = "/var/run/docker.sock"
    container_path = "/var/run/docker.sock"
  }
}
```

### Comandos Terraform

```bash
terraform init
terraform apply
```

---

## 🧪 Ejecutar pruebas localmente

```bash
cd empresa
pytest
```

---

## 🧰 Requisitos

* Python 3.8+
* Docker y Docker Compose
* Terraform
* Jenkins
* Pytest

---

## 📚 Conclusión

Este proyecto demuestra cómo aplicar los conceptos de **herencia en Python** en un escenario empresarial y cómo integrarlo con una **pipeline CI/CD moderna** usando Jenkins, Docker y Terraform.

```

---
