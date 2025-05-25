# Tema C.2 - Lambdas

Este documento explora el principio de **abstracción** aplicado en Python, especialmente en el contexto de la programación funcional. Se presentan conceptos clave como funciones lambda, funciones de orden superior, clausuras, y el uso de funciones o clases como interfaces funcionales.

---

## Código fuente

### [Program](src/Program.py) (Programa principal):

- **Lambdas:**
    - `lambda_add`: función lambda que suma dos números (x + y).
    - `lambda_even`: función lambda que verifica si un número es par (x % 2 == 0).
    - `lambda_order`: función lambda que devuelve la longitud de una cadena (len(x)).

- **Funciones de orden superior:**
    - `orden_superior_map(funcion, lista)`: aplica una función a cada elemento de una lista usando map().
    - `orden_superior_filter(funcion, lista)`: filtra elementos de una lista según un predicado usando filter().
    - `orden_superior_reduce(funcion, lista)`: reduce una lista a un solo valor usando reduce().
    - `orden_superior_sorted(funcion, lista)`: ordena una lista usando una función como criterio de ordenamiento.

- **Clausura (Closure):**
    - `closure(n)`: devuelve una función lambda que multiplica su argumento por n, demostrando el concepto de clausura.

- **Interfaz funcional simulada:**
    - `OrdenarPorLongitud`: clase callable que actúa como función para ordenar por longitud de cadena.
    - `FiltrarPorPar`: clase callable que actúa como predicado para filtrar números pares.

- **Procesamiento estilo Stream:**
    - `ejemplo_procesamiento_stream(lista)`: demuestra el encadenamiento funcional filtrando números pares, elevándolos al cuadrado y sumándolos.

- **Uso:**
    - Implementa conceptos fundamentales de programación funcional incluyendo funciones de orden superior, lambdas, clausuras e interfaces funcionales simuladas.

### [LambdaTests](tests/LambdaTests.py) (Pruebas unitarias):

- **Configuración:**
    - `setUp()`: inicializa datos de prueba con lista de números `[1, 2, 3, 4, 5]` y cadenas `["python", "universidad", "hola"]`.

- **Tests de funciones de orden superior:**
    - `test_map()`: verifica que map duplique correctamente cada elemento de la lista `[2, 4, 6, 8, 10]`.
    - `test_filter()`: valida que filter extraiga solo números pares `[2, 4]`.
    - `test_reduce()`: comprueba que reduce sume todos los elementos obteniendo `15`.
    - `test_sorted()`: verifica que sorted ordene cadenas por longitud `["hola", "python", "universidad"]`.

- **Tests de clausura:**
    - `test_closure()`: prueba que la clausura `closure(2)` genere una función que duplica el valor `duplicar(10) = 20`.

- **Tests de interfaces funcionales:**
    - `test_interface_sorted()`: valida que la clase `OrdenarPorLongitud` funcione como criterio de ordenamiento.
    - `test_interface_filter()`: verifica que la clase `FiltrarPorPar` funcione como predicado de filtrado.

- **Tests de procesamiento stream:**
    - `test_stream()`: comprueba el procesamiento encadenado que filtra pares [2,4], los eleva al cuadrado [4,16] y los suma `20`.

- **Uso:**
    - Conjunto completo de pruebas unitarias que valida la funcionalidad de todas las características de programación funcional implementadas, incluyendo lambdas, funciones de orden superior, clausuras e interfaces funcionales.

---

## Conceptos clave

### Funciones lambda

Una función anónima definida en una sola línea. Se usa para funciones simples y temporales, comúnmente como argumentos de funciones de orden superior.

---

### Funciones de orden superior

Funciones que reciben otras funciones como argumentos. Permiten una programación más declarativa y expresiva.

- `map(func, iterable)`: Aplica una función a todos los elementos del iterable.
- `filter(func, iterable)`: Filtra elementos según una condición.
- `reduce(func, iterable)`: Reduce una secuencia a un solo valor.
- `sorted(iterable, key=func)`: Ordena los elementos usando una función como criterio.

---

### Clausuras (Closures)

Funciones que retienen el entorno en el que fueron definidas. Permiten encapsular comportamiento junto con su contexto.

---

### Interfaces funcionales simuladas

Simulación de interfaces mediante funciones o clases con el método especial `__call__` para abstraer criterios de ordenamiento u operación.

---

### Procesamiento tipo "stream"

Encadenamiento de operaciones como filtrado, transformación y agregación sobre colecciones, similar al enfoque de Java 8.

---

## Ficheros de configuración 

### [Dockerfile](Dockerfile)
Define la imagen de Docker utilizada en el entorno del proyecto.

### [main.tf](main.tf)
Archivo principal de configuración de infraestructura en Terraform.

### [Jenkinsfile](Jenkinsfile)
Contiene la configuración del pipeline de integración continua (CI) usando Jenkins.

---

## Despliegue
Se requiere descargar la imagen necesaria para llevar a cabo el despliegue:
````bash
docker build -t myjenkins-python .
````

Para realizar el despliegue, se debe ejecutar el siguiente script. Este creará los recursos necesarios en Docker y ejecutará los comandos correspondientes de Terraform:
````bash
python ./deploy_jenkins.py
````

Una vez realizado este paso, podemos usar Jenkins en el localhost.

Para liberar los recursos, es necesario realizar los siguientes comandos.
````bash
docker stop <nombre-contenedor-dind>
docker stop <nombre-contenedor-jenkins>
docker rm <nombre-contenedor-dind>
docker rm <nombre-contenedor-jenkins>
docker network rm <id-network-jenkins>
````
