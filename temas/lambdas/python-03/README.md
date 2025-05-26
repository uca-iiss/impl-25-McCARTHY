# Tema C: Lambdas (Python) - Práctica IISS

Este proyecto demuestra el uso avanzado de **lambdas** y programación funcional en Python, aplicando conceptos como composición, filtrado, agrupamiento y ordenación de datos, con integración de pruebas automatizadas y despliegue CI/CD usando Jenkins, Docker y Terraform.

## Estructura del proyecto

```
temas/
└── lambdas/
    └── python-03/
        ├── app/
        │   ├── logs.py           ← Módulo con funciones que usan lambdas
        │   └── procesador.py     ← Funciones funcionales que procesan los logs
        ├── tests/
        │   └── test_procesador.py ← Pruebas unitarias con pytest
        ├── main.py               ← Script de entrada
        ├── setup.py              ← Instalación del paquete
        ├── requirements.txt      ← Dependencias del proyecto
        ├── Dockerfile            ← Imagen personalizada para entorno Python
        ├── Jenkinsfile           ← Pipeline declarativo de Jenkins
        └── docs/
            ├── main.tf           ← Infraestructura Jenkins y DinD con Terraform
            ├── variables.tf
            └── outputs.tf
```

---

## Objetivo del ejemplo

El sistema simula un procesador de logs donde cada línea tiene un `timestamp`, un `nivel` (`INFO`, `ERROR`, `WARNING`) y un `mensaje`.

Las funciones usan `lambdas`, `filter`, `map`, `sorted`, `groupby` y `functools.reduce` para:

- Filtrar por nivel (`filtrar_por_nivel`)
- Contar logs por nivel (`contar_por_nivel`)
- Ordenar logs (`ordenar_logs`)
- Agrupar por nivel (`agrupar_por_nivel`)

El uso de `@dataclass` y expresiones lambda permite un diseño limpio, compacto y funcional.

---

## Ejecución del ejemplo

### 1. Construcción de la imagen Docker

Desde `temas/lambdas/python-03/docs`:

```bash
docker build -t myjenkins-python .
```

## Integración continua con Jenkins

Este proyecto se ejecuta automáticamente mediante un **pipeline declarativo de Jenkins** que compila, testea, empaqueta con PyInstaller y archiva el ejecutable y su salida.

### 2. Despliegue de Jenkins con Terraform

Desde la carpeta `docs`:

```bash
terraform init
terraform apply
```

Esto lanza Jenkins y Docker-in-Docker con acceso compartido al socket de Docker.

### 3. Acceso a Jenkins

Ir a: [http://localhost:8082](http://localhost:8082)

#### Contraseña inicial

```bash
docker exec -it jenkins-blueocean cat /var/jenkins_home/secrets/initialAdminPassword
```

Una vez dentro:
1. En el panel de control de Jenkins, hacer clic en **"New Item"**.
2. Introducir un nombre como `Scala-Herencia-Pipeline`.
3. Seleccionar **"Pipeline"** y pulsar **OK**.
4. En la sección **Pipeline**, configurar:
   - En **Definition**, seleccionar: `Pipeline script from SCM`.
   - En **SCM**, seleccionar: `Git`.
   - En **Repository URL**, poner: `https://github.com/uca-iiss/impl-25-McCARTHY`
   - En **Branch Specifier**, escribir: `*/main`
   - En **Script Path**, poner: `temas/lambdas/python-03/Jenkinsfile`
5. Guardar y lanzar el pipeline (Build now).

---

## Detalle de las clases

### `logs.py` [`app/logs.py`](./app/logs.py).

Módulo con funciones funcionales basadas en lambda, map, filter, reduce, y groupby:

filtrar_logs: filtra logs por nivel

ordenar_logs: ordena por timestamp

contar_logs: cuenta logs por nivel

agrupar_logs: agrupa logs por nivel

```python
from dataclasses import dataclass
from typing import List

@dataclass
class LogEntry:
    timestamp: str
    nivel: str
    mensaje: str

def ejemplo_logs() -> List[str]:
    return [
        "2025-05-20 12:00:01 ERROR Fallo de conexión",
        "2025-05-20 12:01:01 INFO Usuario conectado",
        "2025-05-20 12:02:01 WARNING Memoria alta",
        "2025-05-20 12:03:01 ERROR Timeout alcanzado",
        "2025-05-20 12:04:01 DEBUG Señal recibida",
        "2025-05-20 12:05:01 INFO Proceso terminado"
    ]
```

---

### `procesador.py` [`app/procesador.py`](./app/procesador.py).

Contiene funciones de alto nivel para manipular los logs:

parsear_log: transforma una línea de texto en un objeto log con dataclass

filtrar_por_nivel, contar_por_nivel, ordenar_logs, agrupar_por_nivel: funciones que combinan la lógica de logs.py

```python
from functools import reduce
from collections import Counter

# Log de ejemplo: "2025-05-20 12:00:01 ERROR Fallo de conexión"
def parsear_log(log_linea):
    partes = log_linea.strip().split(' ', 3)
    return {
        'timestamp': f"{partes[0]} {partes[1]}",
        'nivel': partes[2],
        'mensaje': partes[3] if len(partes) > 3 else ''
    }

# Función para filtrar por nivel usando una lambda
def filtrar_por_nivel(logs, nivel='ERROR'):
    return list(filter(lambda log: log['nivel'] == nivel, logs))

# Función para contar logs por nivel
def contar_por_nivel(logs):
    niveles = list(map(lambda log: log['nivel'], logs))
    return dict(Counter(niveles))

# Ordenar por timestamp
def ordenar_logs(logs):
    return sorted(logs, key=lambda log: log['timestamp'])

# Agrupar por nivel (usando reduce y lambdas)
def agrupar_por_nivel(logs):
    return reduce(lambda acc, log: {**acc, log['nivel']: acc.get(log['nivel'], []) + [log]}, logs, {})
```

---

### `main.py` [`/main.py`](./main.py).

Punto de entrada de la aplicación. Orquesta el procesamiento de logs:

Lee los logs desde una lista fija o un archivo

Aplica las funciones del módulo procesador

Imprime resultados en consola o los guarda en salida.txt

```python
Punto de entrada de la aplicación. Orquesta el procesamiento de logs:

Lee los logs desde una lista fija o un archivo

Aplica las funciones del módulo procesador

Imprime resultados en consola o los guarda en salida.txt
```

---

### `setup.py` [`setup.py`](./setup.py).

```python
from setuptools import setup, find_packages

setup(
    name='lambdas_logs',
    version='0.1',
    packages=find_packages(include=['app', 'app.*']),
    entry_points={
        'console_scripts': [
            'procesador=main:main',
        ],
    },
    include_package_data=True,
    description='Procesamiento de logs con funciones lambda'
)
```

---

### `requirements.txt` [`requirements.txt`](./requirements.txt).

Lista de dependencias necesarias para ejecutar el proyecto:

-pytest
-pyinstaller

```txt
pytest
-e .
pyinstaller
```

---

### `test_procesador.py` [`tests/test_procesador.py`](./tests/test_procesador.py).

Conjunto de pruebas automatizadas con pytest. Prueba:

filtrar_por_nivel

contar_por_nivel

ordenar_logs

agrupar_por_nivel
Usa assert con logs predefinidos para comprobar la lógica del procesador.

```python
from app.procesador import parsear_log, filtrar_por_nivel, contar_por_nivel, ordenar_logs, agrupar_por_nivel

raw_logs = [
    "2025-05-20 12:00:01 ERROR Fallo de conexión",
    "2025-05-20 12:01:01 INFO Usuario conectado",
    "2025-05-20 12:02:01 WARNING Memoria alta",
    "2025-05-20 12:03:01 ERROR Timeout alcanzado"
]

parsed_logs = list(map(parsear_log, raw_logs))

def test_filtrar_por_nivel():
    errores = filtrar_por_nivel(parsed_logs, 'ERROR')
    assert len(errores) == 2
    assert errores[0]['mensaje'] == 'Fallo de conexión'

def test_contar_por_nivel():
    conteo = contar_por_nivel(parsed_logs)
    assert conteo == {'ERROR': 2, 'INFO': 1, 'WARNING': 1}

def test_ordenar_logs():
    ordenados = ordenar_logs(parsed_logs)
    assert ordenados[0]['timestamp'] == '2025-05-20 12:00:01'

def test_agrupar_por_nivel():
    grupos = agrupar_por_nivel(parsed_logs)
    assert 'ERROR' in grupos and len(grupos['ERROR']) == 2
```

---

## Infraestructura del Proyecto

### `Jenkinsfile` [`Jenkinsfile`](./Jenkinsfile).

```groovy
pipeline {
    agent none
    stages {
        stage('Build') {
            agent {
                docker {
                    image 'arevalo8/custom-lambdas'
                }
            }
            steps {
                sh '''
                    python -m py_compile temas/lambdas/python/app/procesador.py
                    python -m py_compile temas/lambdas/python/app/logs.py
                    python -m py_compile temas/lambdas/python/main.py
                '''        
            }
        }
        stage('Test') {
            agent {
                docker {
                    image 'arevalo8/custom-lambdas'
                }
            }
            steps {
                sh '''
                    export PYTHONPATH=$(pwd)/temas/lambdas/python:$PYTHONPATH
                    py.test --verbose --junit-xml test-reports/results.xml temas/lambdas/python/tests/test_procesador.py
                '''
            }
            post {
                always {
                    junit 'test-reports/results.xml'
                }
            }
        }
        stage('Deliver') {
            agent {
                docker { 
                    image 'arevalo8/custom-lambdas'
                }
            }
            steps {
                sh 'pyinstaller --onefile temas/lambdas/python/main.py --name procesador'
                sh 'ls -la dist/'
                sh 'chmod +x dist/procesador'
                sh './dist/procesador > salida.txt || true'
            }
            post {
                success {
                    archiveArtifacts 'dist/procesador'
                    archiveArtifacts 'salida.txt'
                }
            }
        }

    }
}
```

---

### `Dockerfile` [`Dockerfile`](./Dockerfile).

Construye la imagen personalizada con:

python:3.12-slim como base

Dependencias del sistema necesarias para compilar con PyInstaller (gcc, file, etc.)

Instala las dependencias de Python

```dockerfile
FROM python:3.12-slim

WORKDIR /app

# Instalar dependencias del sistema para que pyinstaller funcione
RUN apt-get update && apt-get install -y --no-install-recommends \
    gcc \
    build-essential \
    libglib2.0-0 \
    libgl1-mesa-glx \
    libpython3-dev \
    && rm -rf /var/lib/apt/lists/*

# Copiar el código antes de instalar dependencias
COPY . .

# Instalar dependencias de Python, incluyendo pyinstaller y el paquete local
RUN pip install --no-cache-dir -r requirements.txt 

# Comando por defecto, útil para debug/testing
CMD ["pytest"]
```

---

### `main.tf` [`docs/main.tf`](./docs/main.tf).

Este archivo Terraform despliega una infraestructura contenerizada para Jenkins con soporte completo para Docker-in-Docker (DinD), 
permitiendo que Jenkins ejecute contenedores Docker durante sus pipelines.

```hcl
resource "docker_network" "jenkins" {
  name = "jenkins"
}

resource "docker_volume" "jenkins_data" {
  name = "jenkins-data"
}

resource "docker_volume" "docker_certs" {
  name = "jenkins-docker-certs"
}

resource "docker_container" "docker_dind" {
  name     = "jenkins-docker"
  image    = "docker:dind"
  privileged = true
  restart  = "always"

  networks_advanced {
    name    = docker_network.jenkins.name
    aliases = ["docker"]
  }

  env = [
    "DOCKER_TLS_CERTDIR=/certs"
  ]

  volumes {
    volume_name    = docker_volume.docker_certs.name
    container_path = "/certs/client"
  }

  volumes {
    volume_name    = docker_volume.jenkins_data.name
    container_path = "/var/jenkins_home"
  }

  ports {
    internal = 2376
    external = 2376
  }
}

resource "docker_image" "jenkins_image" {
  name         = "myjenkins-python"
  keep_locally = true
}

resource "docker_container" "jenkins" {
  name  = "jenkins-blueocean"
  image = docker_image.jenkins_image.name
  restart = "on-failure"

  networks_advanced {
    name = docker_network.jenkins.name
  }

  env = [
    "DOCKER_HOST=tcp://docker:2376",
    "DOCKER_CERT_PATH=/certs/client",
    "DOCKER_TLS_VERIFY=1"
  ]

  volumes {
    volume_name    = docker_volume.jenkins_data.name
    container_path = "/var/jenkins_home"
  }

  volumes {
    volume_name    = docker_volume.docker_certs.name
    container_path = "/certs/client"
    read_only      = true
  }

  ports {
    internal = 8080
    external = 8082
  }

  ports {
    internal = 50000
    external = 50002
  }
}
```

---

### `providers.tf` [`docs/providers.tf`](./docs/providers.tf).

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
```

---

### `docs/Dockerfile` [`docs/Dockerfile`](./docs/Dockerfile).

construye una imagen personalizada basada en jenkins/jenkins:lts, añadiendo soporte para Python, PyInstaller, pytest, y plugins esenciales de Jenkins, permitiendo ejecutar pipelines Python avanzados desde Jenkins.

```dockerfile
FROM jenkins/jenkins:lts

USER root

#Crear alias
RUN ln -s /usr/bin/python3 /usr/bin/python

# Instalar Python, pip y herramientas necesarias
RUN apt-get update && \
    apt-get install -y python3 python3-pip docker.io curl && \
    pip3 install --no-cache-dir pytest pyinstaller --break-system-packages

# Instalar todos los plugins necesarios con sus dependencias explícitas
RUN jenkins-plugin-cli --plugins \
    blueocean \
    docker-workflow \
    token-macro \
    json-path-api

USER jenkins
```

---

## Archivos importantes

| Archivo                            | Descripción                                                                 |
|------------------------------------|-----------------------------------------------------------------------------|
| `logs.py`                          | Funciones para parsear y representar logs con lambdas                      |
| `procesador.py`                    | Funciones de filtrado, orden y agrupación con expresiones funcionales      |
| `test_procesador.py`              | Pruebas con `pytest`                                                       |
| `main.py`                          | Punto de entrada, ejecuta flujo completo con logs de ejemplo               |
| `Dockerfile`                       | Imagen Docker personalizada con dependencias del proyecto                  |
| `Jenkinsfile`                      | Pipeline CI/CD completo con build, test y entrega                          |
| `docs/*.tf`                        | Despliegue de Jenkins contenerizado con Terraform                          |

---

## Limpieza de imágenes y contenedores

```bash
# Borrar contenedor Jenkins
docker rm -f jenkins-blueocean

# Borrar contenedor Docker-in-Docker
docker rm -f jenkins-docker

# Borrar imagen Jenkins personalizada 
docker rmi myjenkins-python

#Borrar red jenkins
docker network rm jenkins
```

---

## Resultado final

El artefacto generado (`dist/procesador`) es un ejecutable que puede procesar los logs automáticamente y genera una salida en `salida.txt` como parte del pipeline.
