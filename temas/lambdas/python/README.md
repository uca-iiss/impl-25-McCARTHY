# Tema C: Lambdas (Python) - Práctica IISS

Este proyecto demuestra el uso avanzado de **lambdas** y programación funcional en Python, aplicando conceptos como composición, filtrado, agrupamiento y ordenación de datos, con integración de pruebas automatizadas y despliegue CI/CD usando Jenkins, Docker y Terraform.

## Estructura del proyecto

```
temas/
└── lambdas/
    └── python/
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

Desde `temas/lambdas/python`:

```bash
docker build -t arevalo8/custom-lambdas .
```

### 2. Ejecutar dentro del contenedor

```bash
docker run --rm -it -v "$PWD":/app -w /app arevalo8/custom-lambdas bash
```

### 3. Ejecutar pruebas manualmente

```bash
pytest -v temas/lambdas/python/tests
```

---

## Integración continua con Jenkins

Este proyecto se ejecuta automáticamente mediante un **pipeline declarativo de Jenkins** que compila, testea, empaqueta con PyInstaller y archiva el ejecutable y su salida.

### 4. Despliegue de Jenkins con Terraform

Desde la carpeta `docs`:

```bash
cd temas/lambdas/python/docs
terraform init
terraform apply
```

Esto lanza Jenkins y Docker-in-Docker con acceso compartido al socket de Docker.

### 5. Acceso a Jenkins

Ir a: [http://localhost:8080](http://localhost:8080)

#### Contraseña inicial

```bash
docker exec -it jenkins-lambdas cat /var/jenkins_home/secrets/initialAdminPassword
```

Una vez dentro:
- Instalar plugins recomendados
- Instalar plugin `Docker Pipeline`
- Crear pipeline desde SCM con:
  - Git URL: https://github.com/uca-iiss/impl-25-McCARTHY
  - Branch: `feature/lambdas`
  - Script Path: `temas/lambdas/python/Jenkinsfile`

---

### 6. Etapas del pipeline (`Jenkinsfile`)

```groovy
pipeline {
    agent none
    stages {
        stage('Build') {
            agent { docker { image 'arevalo8/custom-lambdas' } }
            steps {
                sh '''
                    python -m py_compile temas/lambdas/python/app/*.py
                    python -m py_compile temas/lambdas/python/main.py
                '''
            }
        }
        stage('Test') {
            agent { docker { image 'arevalo8/custom-lambdas' } }
            steps {
                sh '''
                    export PYTHONPATH=$(pwd)/temas/lambdas/python:$PYTHONPATH
                    py.test --verbose --junit-xml test-reports/results.xml temas/lambdas/python/tests
                '''
            }
            post {
                always {
                    junit 'test-reports/results.xml'
                }
            }
        }
        stage('Deliver') {
            agent { docker { image 'arevalo8/custom-lambdas' } }
            steps {
                sh '''
                    pyinstaller --onefile temas/lambdas/python/main.py --name procesador
                    chmod +x dist/procesador
                    ./dist/procesador > salida.txt || true
                '''
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
docker rm -f jenkins-lambdas
docker rmi arevalo8/custom-lambdas
```

---

## Resultado final

El artefacto generado (`dist/procesador`) es un ejecutable que puede procesar los logs automáticamente y genera una salida en `salida.txt` como parte del pipeline.
