# Tema C.2 - Lambdas

Este documento explora el principio de **abstracción** aplicado en Python, especialmente en el contexto de la programación funcional. Se presentan conceptos clave como funciones lambda, funciones de orden superior, clausuras, y el uso de funciones o clases como interfaces funcionales.

---

## Código fuente
<!-- - Programa principal (`Program.py`) -->
### [Program](src/Program.py) (Programa principal)
<!-- - Pruebas unitarias (`LambdaTests.py`) -->
### [LambdaTests](tests/LambdaTests.py) (Pruebas unitarias)

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
