# Tema B.3 - Aspectos

Este documento explora la implementación de un sistema de gestión de biblioteca utilizando **Programación Orientada a Aspectos (AOP)** con AspectJ en Java. El proyecto demuestra cómo separar las preocupaciones transversales, como el registro de actividades, del código principal de la aplicación.

---

## Código fuente

### [Book](mi-proyecto/src/main/java/com/ejemplo/proyecto/Book.java) (clase):

- **Atributos:**
    - `title`: título del libro (privado).
    - `isbn`: número de identificación único del libro (privado).
    - `isAvailable`: estado de disponibilidad del libro (privado, por defecto true).

- **Métodos:**
    - `getTitle()`: devuelve el título del libro.
    - `getIsbn()`: devuelve el ISBN del libro.
    - `isAvailable()`: verifica si el libro está disponible para préstamo.
    - `borrow()`: marca el libro como prestado (no disponible).
    - `returnBook()`: marca el libro como devuelto (disponible).

- **Uso:**
    - Representa un libro con título, ISBN y estado de disponibilidad que puede ser prestado y devuelto.

### [Member](mi-proyecto/src/main/java/com/ejemplo/proyecto/Member.java) (clase):

- **Atributos:**
    - `name`: nombre del miembro de la biblioteca (privado).
    - `id`: identificador único del miembro (privado).
    - `borrowedBooks`: lista de libros prestados actualmente (privado).

- **Métodos:**
    - `getName()`: devuelve el nombre del miembro.
    - `getId()`: devuelve el ID del miembro.
    - `getBorrowedBooks()`: devuelve la lista de libros prestados.
    - `borrowBook(Book book)`: añade un libro a la lista de prestados y lo marca como no disponible.
    - `returnBook(Book book)`: remueve un libro de la lista de prestados y lo marca como disponible.

- **Uso:**
    - Representa un miembro de la biblioteca que puede pedir y devolver libros, manteniendo un registro de sus préstamos activos.

### [BookManager](mi-proyecto/src/main/java/com/ejemplo/proyecto/BookManager.java) (clase):

- **Atributos:**
    - `books`: lista de todos los libros en el sistema (privado).
    - `members`: mapa de miembros registrados indexados por ID (privado).

- **Métodos:**
    - `addBook(Book book)`: añade un libro al inventario.
    - `registerMember(Member member)`: registra un nuevo miembro en el sistema.
    - `findBookByIsbn(String isbn)`: busca y devuelve un libro por su ISBN.
    - `findMemberById(String id)`: busca y devuelve un miembro por su ID.
    - `lendBook(String memberId, String isbn)`: procesa el préstamo de un libro a un miembro.
    - `returnBook(String memberId, String isbn)`: procesa la devolución de un libro de un miembro.
    - `getAvailableBooks()`: devuelve una lista de todos los libros disponibles.

- **Uso:**
    - Gestiona el inventario de libros y miembros de la biblioteca, coordinando las operaciones de préstamo y devolución.

### [LoggingAspect](mi-proyecto/src/main/java/com/ejemplo/proyecto/LoggingAspect.java) (aspecto):

- **Pointcuts:**
    - `lendBookOperation()`: intercepta las llamadas al método `lendBook()` de BookManager.
    - `returnBookOperation()`: intercepta las llamadas al método `returnBook()` de BookManager.

- **Advices:**
    - `beforeLendBook()`: registra el inicio de una operación de préstamo con timestamp, ID del miembro e ISBN.
    - `afterLendBook()`: registra la finalización de una operación de préstamo.
    - `beforeReturnBook()`: registra el inicio de una operación de devolución con timestamp, ID del miembro e ISBN.
    - `afterReturnBook()`: registra la finalización de una operación de devolución.

- **Métodos auxiliares:**
    - `getCurrentTime()`: formatea la fecha y hora actual para los logs.

- **Uso:**
    - Aspecto para registrar automáticamente las operaciones de préstamo y devolución sin modificar la lógica de negocio principal.

### [AspectosTests](mi-proyecto/src/test/java/com/ejemplo/proyecto/AspectosTests.java) (test):

- **Configuración:**
    - `setUp()`: inicializa una nueva instancia de BookManager antes de cada test.

- **Tests incluidos:**
    - `testBookCreationAndMethods()`: verifica la creación de libros y el funcionamiento de sus métodos básicos.
    - `testMemberCreationAndMethods()`: prueba la creación de miembros y la gestión de libros prestados.
    - `testAddAndFindBooks()`: valida la adición y búsqueda de libros en el BookManager.
    - `testRegisterAndFindMembers()`: verifica el registro y búsqueda de miembros.
    - `testLendAndReturnBooks()`: prueba el flujo completo de préstamo y devolución de libros.
    - `testSpecialCasesLendAndReturn()`: valida el manejo de casos especiales y errores en préstamos/devoluciones.

- **Uso:**
    - Conjunto completo de pruebas unitarias que verifica la funcionalidad de todas las clases del sistema, incluyendo casos normales y excepcionales.

---

## Fichero de configuración de `AspectJ`

- [aop.xml](mi-proyecto/src/main/resources/META-INF/aop.xml)

## Fichero de configuración del proyecto con `Maven`

- [pom.xml](mi-proyecto/pom.xml)

---

## Conceptos clave

### Programación Orientada a Aspectos (AOP)

Un paradigma que permite separar las preocupaciones transversales (cross-cutting concerns) del código principal de la aplicación, resultando en un diseño más modular y mantenible.

---

### Puntos de corte (Pointcuts)

Expresiones que definen los puntos de ejecución donde se aplicarán los aspectos. En nuestro sistema definimos:

- `lendBookOperation()`: Captura la ejecución del método de préstamo de libros
- `returnBookOperation()`: Captura la ejecución del método de devolución de libros

---

### Consejos (Advices)

Código que se ejecuta cuando se alcanza un punto de corte definido:

- `@Before`: Se ejecuta antes de que se ejecute el método interceptado
- `@After`: Se ejecuta después de que se ejecute el método interceptado, independientemente del resultado

---

### Tejido (Weaving)

Proceso de integrar los aspectos con el código base para crear el comportamiento final. AspectJ puede realizar el tejido:

- En tiempo de compilación (compile-time weaving)
- En tiempo de carga (load-time weaving)
- En tiempo de ejecución (runtime weaving)


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
docker build -t myjenkins-mvn .
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
