
## 👨‍🏫 ¿Qué es la herencia en Python?

La **herencia** es un concepto fundamental en la programación orientada a objetos. Nos permite definir una clase general (por ejemplo, una *figura*) y luego crear clases más concretas (como *rectángulo* o *círculo*) que heredan de ella sus propiedades y métodos.

En este ejemplo:

- La clase `Figura` es la base, y define un método `area()` que *debería* implementar cada subclase.
- `Rectangulo` y `Circulo` son clases hijas que **sobrescriben** ese método para devolver el área correspondiente.

Ejemplo simplificado:

```python
class Figura:
    def area(self):
        raise NotImplementedError("Este método debe ser implementado por las subclases")

class Rectangulo(Figura):
    def __init__(self, ancho, alto):
        self.ancho = ancho
        self.alto = alto

    def area(self):
        return self.ancho * self.alto
````

---

## 🗂️ Estructura del proyecto

```plaintext
.
├── app
│   └── figuras.py              # Clases Figura, Rectángulo y Círculo
├── tests
│   └── test_figuras.py         # Pruebas unitarias usando pytest
├── Dockerfile                  # Imagen Docker para ejecutar los tests
├── Jenkinsfile                 # Pipeline Jenkins para construir y probar
├── requirements.txt            # Lista de dependencias
└── README.md                   # Este archivo
```

---

## 💡 ¿Qué hace el código?

1. Tenemos una clase base `Figura`, que no tiene implementación de `area()` (para obligar a que las subclases lo definan).
2. Luego definimos:

   * `Rectangulo`: recibe ancho y alto, y calcula su área como `ancho * alto`.
   * `Circulo`: recibe un radio y calcula el área usando la fórmula `π * radio²`.

---

## 🧰 Requisitos para usarlo

Para probar el proyecto localmente necesitas tener instalado:

* [Python 3.10 o superior](https://www.python.org/)
* [Docker](https://www.docker.com/)
* [Jenkins](https://www.jenkins.io/) (solo si quieres probar la integración continua)

---

## 🚀 ¿Cómo probarlo en tu máquina?

### 1. Clona el repositorio

```bash
git clone https://github.com/davidabuinESI/impl-25-GOSLING.git
cd impl-25-GOSLING
```

### 2. Construye la imagen Docker

```bash
docker build -t herencia-python .
```

### 3. Ejecuta los tests dentro del contenedor

```bash
docker run --rm herencia-python
```

---

## ⚙️ Cómo usarlo con Jenkins

Este proyecto ya incluye un `Jenkinsfile` que automatiza el proceso de testing. La pipeline hace lo siguiente:

1. Clona el repositorio.
2. Construye la imagen Docker (`herencia-python`).
3. Ejecuta los tests automáticamente.

### 🛠️ Pasos para configurarlo en Jenkins

1. Abre Jenkins en tu navegador (por defecto es [http://localhost:8080](http://localhost:8080)).
2. Crea un nuevo proyecto de tipo **Pipeline**.
3. En la sección *Pipeline script from SCM*:

   * Marca la opción "Git".
   * Pega esta URL del repo:
     `https://github.com/davidabuinESI/impl-25-GOSLING.git`
4. Guarda y dale a "Build Now" o "Ejecutar".

---
