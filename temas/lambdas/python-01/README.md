# Ejemplo de uso de Lambdas con python

Este documento describe:

- Cómo hemos implementado un ejemplo de lambdas utilizando Python.
- Batería de pruebas y automatización con pytest.
- Cómo se construye una imagen personalizada de Jenkins con python y se crean los contenedores.
- Ejecución de pruebas automatizadas usando un `Jenkinsfile`.

---

## Análisis de lambda con python

Una **función lambda** (o función anónima) es una función pequeña y simple que se define en una sola línea y que no tiene nombre. Se utiliza comúnmente para operaciones sencillas y como argumentos para funciones de orden superior, como map, filter y sorted.

En el ejemplo que veremos, hemos implementado una clase llamada Item y varias funciones que filtran un objeto de este tipo dependiendo de varios parámetros. Utilizaremos funciones lambdas para:

- Utilizarlas en funciones de orden superior.
- Facilitar la escalabilidad.
- Filtrar un objeto de varias formas.

---

## Explicación del código en python

Este proyecto define una clase `Item` con varios atributos como `Price` o `Categoría`. Se han realizado varias funciones que devuelven listas como resultado de una función lambda. 

---

### 'Item'

```python
class Item:
    def __init__(self, name, category, price, quantity):
        self.name = name
        self.category = category
        self.price = price
        self.quantity = quantity
    
    def __repr__(self):
        return f"Item(name={self.name}, category={self.category}, price={self.price}, quantity={self.quantity})"

```

Clase que contiene los atributos name, category, price y quantity. Además de un constructor y el método \_\_repr\_\_ para depurar.

### 'build_equals_filter'

```python
def build_equals_filter(attribute: str, value: Any) -> Callable[[Item], bool]:
    """Genera una función lambda para filtrar objetos por igualdad de atributo."""
    return lambda item: getattr(item, attribute) == value
```

Función que filtra objetos que tengan el atributo attribute igual al valor value.

### 'build_greater_than_filter'

```python
def build_greater_than_filter(attribute: str, umbral: float) -> Callable[[Item], bool]:
    """Genera una función lambda para filtrar objetos con atributos mayores a un umbral."""
    return lambda item: getattr(item, attribute) > umbral
```

Función que filtra objetos que tengan el atributo attribute mayor al umbral.

### 'build_less_than_filter'

```python
def build_less_than_filter(attribute: str, umbral: float) -> Callable[[Item], bool]:
    """Genera una función lambda para filtrar objetos con atributos menores a un umbral."""
    return lambda item: getattr(item, attribute) < umbral
```

Función que filtra objetos que tengan el atributo attribute menor al umbral.

### 'build_range_filter'
```python
def build_range_filter(attribute: str, min_value: float, max_value: float) -> Callable[[Item], bool]:
    """Genera una función lambda para filtrar objetos dentro de un rango de valores."""
    return lambda item: min_value <= getattr(item, attribute) <= max_value
```

Función que filtra objetos que tengan el atributo attribute menor al valor max_value y mayor al valor min_value.

### 'build_contains_filter'
```python
def build_contains_filter(attribute: str, substring: str) -> Callable[[Item], bool]:
    """Genera una función lambda para filtrar objetos que contienen un substring en un atributo."""
    return lambda item: substring in getattr(item, attribute)

```

Función que filtra objetos que contengan en el atributo attribute la subcadena substring.

## Ventajas de utilizar lambdas

1. **Código más conciso:** Permiten definir funciones pequeñas en una sola línea, reduciendo el código repetitivo y haciéndolo más limpio.

2. **Mejora la legibilidad en algunos contextos:** Cuando se usan adecuadamente, pueden hacer que las operaciones sean más fáciles de entender al expresar directamente la lógica en el lugar donde se necesita.

3. **Composición funcional:** Son esenciales para estilos de programación funcional, permitiendo combinar funciones de manera más natural.

---

Ahora pasaremos a ver el programa de pruebas implementada en el directorio de [tests](Item/tests) con el uso de pytest.

---

## Casos de prueba

__Nota:__ Los casos de prueba parten los siguientes datos:
| Name   | Category     | Price | Quantity |
|----------|--------------|--------|----------|
| Laptop   | Electronics   | 1200   | 5        |
| Mouse   | Electronics     | 25      | 100      |
| Banana  | Groceries | 1      | 200       |
| Shampoo  | Personal Care | 7      | 50       |
| Apple  | Groceries | 2      | 300       |
| Keyboard  | Electronics | 80      | 30       |

Definidos en el siguiente código:

```python
@pytest.fixture
def items():
    return [
        Item("Laptop", "Electronics", 1200, 5),
        Item("Mouse", "Electronics", 25, 100),
        Item("Banana", "Groceries", 1, 200),
        Item("Shampoo", "Personal Care", 7, 50),
        Item("Apple", "Groceries", 2, 300),
        Item("Keyboard", "Electronics", 80, 30),
    ]
```


### 1. Comprobación de la función de filtro igual

```python
def test_build_equals_filter(items):
    electronics_filter = build_equals_filter("category", "Electronics")
    filtered_items = list(filter(electronics_filter, items))
    assert len(filtered_items) == 3
    assert all(item.category == "Electronics" for item in filtered_items)
```

- Se filtran los objetos que sean de categoria Electronics
- Comprobación de que existen tres objetos
- Comprobación de que los objetos son de categoria Electronics
---

### 2. Comprobación de la función de filtro mayor que

```python
def test_build_greater_than_filter(items):
    expensive_filter = build_greater_than_filter("price", 100)
    filtered_items = list(filter(expensive_filter, items))
    assert len(filtered_items) == 1
    assert filtered_items[0].name == "Laptop"
```

- Se filtran los objetos cuyo precio es mayor a 100
- Comprobación de que hay un objeto
- Comprobación de que el nombre del objeto es Laptop

---
### 3. Comprobación de la función de filtro menor que

```python
def test_build_less_than_filter(items):
    cheap_filter = build_less_than_filter("price", 10)
    filtered_items = list(filter(cheap_filter, items))
    assert len(filtered_items) == 3
    assert {item.name for item in filtered_items} == {"Banana", "Apple", "Shampoo"}

```

- Se filtran los objetos cuyo precio es menor a 10
- Comprobación de que hay tres objetos
- Comprobación de que los nombres de los objetos son Banana, Apple y Shampoo

---
### 4. Comprobación de la función de filtro de rango

```python
def test_build_range_filter(items):
    range_filter = build_range_filter("price", 5, 50)
    filtered_items = list(filter(range_filter, items))
    assert len(filtered_items) == 2
    assert {item.name for item in filtered_items} == {"Shampoo", "Mouse"}
```

- Se filtran los objetos cuyo precio está entre 5 y 50
- Comprobación de que hay dos objetos
- Comprobación de que los nombres de los objetos son Mouse y Shampoo

---

### 5. Comprobación de la función de filtro de contener cadena

```python
def test_build_contains_filter(items):
    name_contains_filter = build_contains_filter("name", "a")
    filtered_items = list(filter(name_contains_filter, items))
    assert len(filtered_items) == 4
    assert {item.name for item in filtered_items} == {"Banana", "Shampoo", "Keyboard", "Laptop"}

```

- Se filtran los objetos cuyo nombre contengan "a"
- Comprobación de que hay cuatro objetos
- Comprobación de que los nombres de los objetos son Banana, Keyboard, Laptop y Shampoo

---

Este programa sirve como un banco de pruebas básico para:

- Confirmar el correcto uso de las funciones lambdas.
- Verificar comportamientos específicos de nuestro programa.

---

## Pytest

Para la realización de pruebas automáticas en python, hemos utilizado pytest.
Simplemente necesitamos ejecutar pytest en el directorio de Item para realizar las pruebas correctamente, habiendo instalado previamente pytest.

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
                dir('temas/lambdas/python-01/JenkinsDespliegue') {
                    sh 'pip install -r requirements.txt'
                }
            }
        }

        stage('Ejecutar pruebas') {
            steps {
                echo 'Ejecutando pruebas con pytest...'
                dir('temas/lambdas/python-01/animales') {
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