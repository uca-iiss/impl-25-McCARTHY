# Tema A.3 - Delegación

Este proyecto ejemplifica el concepto de **delegación** en programación orientada a objetos utilizando el lenguaje **Ruby**. Se modela una orquesta de instrumentos musicales donde cada tipo de instrumento delega comportamientos específicos a través de módulos e implementa patrones de diseño como Template Method y Composite.

---

## Código fuente

### [Instrumento](lib/instrumento.rb) (módulo base):

- **Métodos abstractos:**
    - `tocar()`: método que debe implementarse en las clases que incluyan el módulo.
    - `afinar()`: método que debe implementarse para la afinación específica del instrumento.

- **Métodos concretos:**
    - `tipo()`: retorna el nombre de la clase del instrumento.
    - `to_s()`: representación en cadena del instrumento con su tipo y object_id.

- **Uso:**
    - Módulo base que implementa el patrón Template Method definiendo la interfaz común para todos los instrumentos musicales.

---

### [Orquesta](lib/orquesta.rb) (clase composite):

- **Atributos:**
    - `@instrumentos`: array que almacena los instrumentos de la orquesta (privado).

- **Métodos:**
    - `add_instrumento(instrumento)`: agrega un instrumento validando que implemente el módulo Instrumento.
    - `remove_instrumento(instrumento)`: remueve un instrumento de la orquesta.
    - `tocar()`: delega el método tocar a todos los instrumentos de la orquesta.
    - `afinar()`: delega la afinación y ejecución a todos los instrumentos.
    - `each(&block)`: permite iteración sobre los instrumentos (incluye Enumerable).
    - `size()`: retorna el número de instrumentos en la orquesta.
    - `empty?()`: verifica si la orquesta está vacía.
    - `to_a()`: retorna una copia del array de instrumentos.

- **Uso:**
    - Implementa el patrón Composite para manejar colecciones de instrumentos como una unidad cohesiva, delegando operaciones a cada instrumento individual.

---

### [Cuerda](lib/instrumentos/cuerda.rb) (clase concreta):

- **Métodos sobrescritos:**
    - `tocar()`: delega la acción a `rasgar()` para producir el sonido específico de instrumentos de cuerda.
    - `afinar()`: implementa la afinación específica ajustando la tensión de las cuerdas.

- **Métodos privados:**
    - `rasgar()`: produce el sonido característico de vibración de cuerdas.

- **Uso:**
    - Representa instrumentos de cuerda que delegan su comportamiento de ejecución al método específico de rasgar cuerdas.

---

### [Percusion](lib/instrumentos/percusion.rb) (clase concreta):

- **Métodos sobrescritos:**
    - `tocar()`: delega la acción a `golpear()` para producir el sonido específico de instrumentos de percusión.
    - `afinar()`: implementa la afinación específica ajustando la tensión del parche.

- **Métodos privados:**
    - `golpear()`: produce el sonido característico por percusión.

- **Uso:**
    - Representa instrumentos de percusión que delegan su comportamiento de ejecución al método específico de golpear.

---

### [Viento](lib/instrumentos/viento.rb) (clase concreta):

- **Métodos sobrescritos:**
    - `tocar()`: delega la acción a `soplar()` para producir el sonido específico de instrumentos de viento.
    - `afinar()`: implementa la afinación específica ajustando embocadura y presión de aire.

- **Métodos privados:**
    - `soplar()`: produce el sonido característico mediante flujo de aire.

- **Uso:**
    - Representa instrumentos de viento que delegan su comportamiento de ejecución al método específico de soplar.

---

## Tests

### [OrquestaTest](test/testOrquesta.rb) (conjunto de pruebas unitarias):

- **Herramienta:**
    - Usa Minitest como framework de testing.

- **Clase auxiliar:**
    - `GuitarraPrueba`: implementa el módulo Instrumento para pruebas con estados `@afinada` y `@tocada`.

- **Pruebas incluidas:**
    - `test_orquesta_inicialmente_vacia`: verifica que la orquesta inicie vacía.
    - `test_agregar_instrumento`: valida la adición correcta de instrumentos.
    - `test_agregar_objeto_invalido`: asegura que solo se agreguen objetos que implementen Instrumento.
    - `test_tocar_orquesta`: comprueba que la delegación del método tocar funcione correctamente.
    - `test_afinar_orquesta`: verifica que la afinación delegue tanto afinar como tocar.
    - `test_remover_instrumento`: valida la remoción correcta de instrumentos.
    - `test_iteracion_con_each`: prueba la funcionalidad Enumerable de la orquesta.
    - `test_metodo_encadenado`: verifica que los métodos retornen self para permitir method chaining.

- **Uso:**
    - Asegura el funcionamiento correcto de la delegación de comportamientos y los patrones implementados en la orquesta y sus instrumentos.

---

## Conceptos clave

### Delegación

Patrón donde un objeto delega responsabilidades específicas a otros objetos o métodos especializados, promoviendo la separación de responsabilidades y reutilización de código.

---

### Patrón Template Method

Define el esqueleto de un algoritmo en una operación, delegando algunos pasos a las subclases sin cambiar la estructura general del algoritmo.

---

### Patrón Composite

Permite tratar objetos individuales y composiciones de objetos de manera uniforme, delegando operaciones a todos los componentes de la composición.

---

### Módulos en Ruby

Mecanismo de Ruby para compartir código entre clases mediante `include`, permitiendo una forma de herencia múltiple y delegación de comportamiento.

---

### Method Chaining

Técnica que permite encadenar llamadas a métodos retornando `self`, facilitando una sintaxis fluida y expresiva.

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
docker build -t myjenkins-ruby .
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