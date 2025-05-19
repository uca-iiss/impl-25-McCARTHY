# Tema B.3 - Abstracción en Python

Este documento explora el principio de **abstracción** aplicado en Python, especialmente en el contexto de la programación funcional. Se presentan conceptos clave como funciones lambda, funciones de orden superior, clausuras, y el uso de funciones o clases como interfaces funcionales.

---

## Código
- Programa principal (`Program.py`)
- Pruebas unitarias (`LambdaTests.py`)

---

## CI/CD y Aprovisionamiento

- Pipeline de integración continua (`Jenkinsfile`)
- Dockerfile para construcción de imagen (`Dockerfile`)

**Despliegue:**

Para realizar el despliegue, se debe ejecutar el siguiente script. Este creará los recursos necesarios en Docker y ejecutará los comandos correspondientes de Terraform:

```bash
python deploy_jenkins.py
```
Para liberar el puerto que está utilizando el contenedor en localhost, es necesario detener y eliminar dicho contenedor desde Docker utilizando los siguientes comandos.

```bash
docker stop <nombre-contenedor>
docker rm <nombre-contenedor>
```

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

## Recomendaciones

- Usar `lambda` solo para funciones simples.
- Emplear `def` si la lógica es más extensa o reutilizable.
- Utilizar funciones como `map`, `filter`, `reduce` y `sorted` para una programación declarativa.
- Simular interfaces funcionales con funciones o clases que implementen `__call__`.
<!-- 
---

## Recursos adicionales

- [Python Docs - Functional Programming HOWTO](https://docs.python.org/3/howto/functional.html)
- [Fluent Python - Capítulos sobre funciones de orden superior](https://www.oreilly.com/library/view/fluent-python/9781491946237/)
- [Guía de Lambdas en Java - Oracle](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html) -->