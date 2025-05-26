
```markdown
# Ejercicio de Herencia en Python

Este proyecto es un ejemplo sencillo del uso del principio de **Herencia** en programación orientada a objetos utilizando el lenguaje **Python**. El objetivo es ilustrar cómo una clase base puede ser extendida por clases hijas que comparten atributos y comportamientos comunes.

Además, el proyecto incluye pruebas automatizadas con `pytest`, y está preparado para su ejecución en entornos de integración continua con **Docker** y **Jenkins**.

## Estructura del proyecto

```

.
├── app
│   └── figuras.py              # Definición de clases (Figura, Rectángulo, Círculo)
├── tests
│   └── test\_figuras.py         # Tests unitarios con pytest
├── Dockerfile                  # Imagen Docker para ejecutar las pruebas
├── Jenkinsfile                 # Pipeline Jenkins que construye y prueba el proyecto
├── requirements.txt            # Dependencias del proyecto
└── README.md                   # Este archivo

````

## ¿Qué se implementa?

Se define una clase base `Figura` que representa una figura geométrica genérica, y dos clases hijas:

- `Rectangulo`: con atributos `ancho` y `alto`, y método `area()`.
- `Circulo`: con atributo `radio`, y método `area()`.

Cada subclase sobrescribe el método `area` para calcular su área correspondiente.

## Requisitos

Para ejecutar el proyecto localmente necesitas tener instalado:

- [Python 3.10+](https://www.python.org/)
- [Docker](https://www.docker.com/)
- [Jenkins](https://www.jenkins.io/) (solo si deseas ejecutar el pipeline)

## Instalación y ejecución en local

1. Clona el repositorio:

```bash
git clone https://github.com/davidabuinESI/impl-25-GOSLING.git
cd impl-25-GOSLING
````

2. Construye la imagen Docker:

```bash
docker build -t herencia-python .
```

3. Ejecuta los tests:

```bash
docker run --rm herencia-python
```

## Ejecución en Jenkins

1. Asegúrate de tener configurado Jenkins con soporte para Docker.

2. El pipeline Jenkins definido en el archivo `Jenkinsfile` realiza lo siguiente:

* Clona el repositorio.
* Construye la imagen Docker (`herencia-python`).
* Ejecuta los tests automáticamente.

### ¿Cómo configurarlo?

En Jenkins:

1. Crea un nuevo **proyecto de tipo Pipeline**.
2. Marca la opción de **"Pipeline script from SCM"**.
3. Selecciona **Git** y coloca la URL del repositorio.
4. Guarda y ejecuta el pipeline.
