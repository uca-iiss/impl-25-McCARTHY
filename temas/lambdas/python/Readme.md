# Lambdas en Python

## Introducción
Bienvenido al repositorio sobre Lambdas en Python. Aquí encontrarás información detallada sobre cómo utilizar funciones lambda en Python para escribir código más limpio y eficiente con la ayuda de este concepto que te ahorrará tiempo en ciertos casos. 

## Estructura de Directorio
- `/README.md`: Archivo actual, donde explicaremos el concepto de lambda y como podemos utilizarlo
- `/lambdas.py`: Archivo python donde se encuentra el código de ejemplo.
- `/test.py`: Archivo python donde se encuentra el codigo de los tests.
- `/Jenkinsfile`: Archivo necesario para la creación del pipeline y la automatización de los test.

## Conceptos Previos
Antes de ver los ejemplos, es importante comprender algunos conceptos esenciales sobre las funciones lambda en Python. Son funciones pequeñas y sin nombre definidas con la palabra clave `lambda`. Los ejemplos incluyen varios usos típicos:

- **Funciones Lambda para Operaciones Simples**:
  Las funciones lambda se emplean comúnmente para realizar operaciones sencillas, como sumar dos valores. Son una forma compacta de escribir funciones que solo requieren una línea de código.

- **Uso de Lambda con `map()`**:
  La función `map()` aplica una función a cada elemento de un iterable y devuelve un nuevo iterable. Usar funciones lambda con `map()` permite aplicar operaciones complejas de manera concisa sin necesidad de definir una nueva función.

- **Uso de Lambda con `filter()`**:
  La función `filter()` construye un iterador a partir de aquellos elementos de un iterable para los que una función devuelve verdadero. Las funciones lambda son útiles aquí para definir rápidamente la función de filtrado sin separar la definición de la función.

- **Funciones Lambda para Condiciones Ternarias**:
  Las funciones lambda también pueden ser utilizadas para ejecutar expresiones condicionales, lo cual permite integrar lógica condicional dentro de una definición funcional breve y directa.

- **Uso de Lambda con `reduce()`**:
  La función `reduce()` de la biblioteca `functools` se utiliza para aplicar una función de dos argumentos acumulativamente a los elementos de una secuencia, de manera que se reduzca la secuencia a un único valor. Las funciones lambda son ideales para definir la función de reducción de forma rápida y en el mismo lugar donde se utiliza.

## Código de Ejemplo
A continuación un fragmento de `lambdas.py` con las funciones y su uso:

[**lambdas.py**](./lambdas.py)
```python
from functools import reduce

def operacion_basica():
  #Multiplicamos dos números
  multiplicar = lambda a, b: a * b
  return multiplicar(7, 4)

def transformar_lista():
  #Conviertimos una lista de cadenas a mayúsculas
  palabras = ["manzana", "banana", "cereza"]
  return list(map(lambda palabra: palabra.upper(), palabras)) 

def filtrar_numeros():
  #Filtramos una lista de números para obtener solo los mayores de 10
  numeros = [5, 12, 8, 21, 15, 3]
  return list(filter(lambda num: num > 10, numeros)) 

def evaluacion_condicional():
  #Determinamos si un número es positivo, negativo o cero 
  clasificar_numero = lambda x: "Positivo" if x > 0 else ("Negativo" if x < 0 else "Cero")
  return clasificar_numero(-5)

def concatenar_cadenas():
  #Concatena una lista de cadenas
  fragmentos = ["Python", " ", "es", " ", "genial"]
  return reduce(lambda acumulador, elemento: acumulador + elemento, fragmentos)
```

Métodos usados en el ejemplo:

`operacion_basica()`  
Utiliza una función lambda para multiplicar dos números. La expresión `lambda a, b: a * b` toma dos argumentos y devuelve su producto. Este método ilustra el uso de una lambda para realizar una operación matemática sencilla, de forma directa y sin necesidad de una función tradicional.

`transformar_lista()`  
Emplea `map()` junto con una lambda para transformar cada palabra de una lista a mayúsculas. La lambda `lambda palabra: palabra.upper()` toma una cadena de texto y devuelve su versión en mayúsculas. Es un ejemplo claro de cómo aplicar una transformación a todos los elementos de una lista de forma compacta.

`filtrar_numeros()`  
Usa `filter()` con una lambda para obtener solo los números mayores a 10 dentro de una lista. La lambda `lambda num: num > 10` filtra los elementos según esa condición. Este ejemplo demuestra el poder de las lambdas en operaciones de filtrado sin necesidad de definir funciones auxiliares.

`evaluacion_condicional()`  
Implementa una lambda con una condición ternaria para clasificar un número como "Positivo", "Negativo" o "Cero". La expresión `lambda x: "Positivo" if x > 0 else ("Negativo" if x < 0 else "Cero")` permite ejecutar lógica condicional de forma concisa y directa dentro de la propia lambda.

`concatenar_cadenas()`  
Utiliza `reduce()` y una lambda para concatenar los elementos de una lista de cadenas en una sola cadena. La expresión `lambda acumulador, elemento: acumulador + elemento` acumula los fragmentos de texto uno a uno. Este método es un buen ejemplo de cómo las lambdas pueden ayudar a realizar operaciones de reducción en listas, como unir múltiples valores en un solo resultado.


## Código de tests
Ahora, se muestra unos tests para probar el correcto uso de las funciones lambda en Python:
[**test.py**](./test.py)

```python
import unittest
from lambdas import *

class TestLambdas(unittest.TestCase):

    def test_operacion_basica(self):
        resultado = operacion_basica()
        self.assertEqual(resultado, 28)

    def test_transformar_lista(self):
        resultado = transformar_lista()
        self.assertEqual(resultado, ["MANZANA", "BANANA", "CEREZA"])

    def test_filtrar_numeros(self):
        resultado = filtrar_numeros()
        self.assertEqual(resultado, [12, 21, 15])

    def test_evaluacion_condicional(self):
        resultado_neg = evaluacion_condicional()
        self.assertEqual(resultado_neg, "Negativo")

        clasificar = lambda x: "Positivo" if x > 0 else ("Negativo" if x < 0 else "Cero")
        self.assertEqual(clasificar(10), "Positivo")
        self.assertEqual(clasificar(0), "Cero")

    def test_concatenar_cadenas(self):
        resultado = concatenar_cadenas()
        self.assertEqual(resultado, "Python es genial")

if __name__ == "__main__":
    unittest.main()

```

El framework utilizado en el código de prueba es `unittest`, un módulo de pruebas unitarias incluido en la biblioteca estándar de Python. `unittest` proporciona una manera rica y robusta de construir y ejecutar pruebas.

Métodos usados en los test:

`test_operacion_basica():` 
Prueba la función operacion_basica(), que utiliza una lambda para multiplicar dos números. La prueba espera que el resultado sea 28, confirmando que lambda a, b: a * b multiplica correctamente 7 por 4.

`test_transformar_lista():` 
Valida que transformar_lista() transforme correctamente todas las palabras de la lista original a mayúsculas. Verifica que el resultado sea ["MANZANA", "BANANA", "CEREZA"].

`test_filtrar_numeros():` 
Confirma que la función filtrar_numeros() devuelve únicamente los valores mayores a 10 de una lista dada. Se espera como salida [12, 21, 15].

`test_evaluacion_condicional():` 
Evalúa si la función evaluacion_condicional() identifica correctamente si un número es positivo, negativo o cero. En este caso, el número es -5, por lo que la función debe devolver "Negativo".

`test_concatenar_cadenas():` 
Verifica que la función concatenar_cadenas() utilice correctamente reduce() para concatenar una lista de cadenas. La salida esperada es "Python es genial", confirmando la correcta acumulación de los fragmentos.

## Ejecución Test
Para ejecutar el código y pasar los test de dicho código, realiza los siguientes pasos detallados que incluyen la creación de un Jenkinsfile, creación pipeline y ejecución del pipeline

### 1. Creación Jenkinsfile
A continuación, hemos creado el Jenkinsfile necesario para realizar el pipeline, este se encuentra en la carpeta con el resto de código

```Jenkinsfile
pipeline {
    agent any

    stages {
        stage('Preparar entorno') {
            steps {
                sh 'python3 -m venv venv'
                sh './venv/bin/pip install --upgrade pip'
            }
        }

        stage('Ejecutar pruebas') {
            steps {
                sh './venv/bin/python temas/lambdas/python/test.py'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado.'
        }
    }
}
```

### 2. Crear Pipeline
Una vez realizados los pasos anteriores, abrimos Jenkins y creamos un nuevo Pipeline. Para ello: 

 - Lo definimos como `Pipeline script from SCM` y como SCM seleccionamos `Git`.
 - Ponemos la siguiente URL: `https://github.com/uca-iiss/WIRTH-impl-25`.
 - En branch ponemos `*/feature-lambdas`.
 - Por último, en Script Path añadimos `temas/lambdas/Jenkinsfile`

Y con esta configuración hemos creado el pipeline necesario para la ejecución de los test

### 3. Ejecutar los Tests
Una vez creado el pipeline, ejecutamos dando a `Construir ahora` y el propion Jenkins pasará los test automaticamente.