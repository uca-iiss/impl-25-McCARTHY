# Tema A.2 - Herencia

Este proyecto ejemplifica el concepto de **herencia** en programación orientada a objetos utilizando el lenguaje **Scala**. Se modela una jerarquía de empleados con diferentes roles, todos ellos derivados de una clase base común.

## Código fuente

### [Empleado](src/empleados/Empleado.scala) (clase abstracta):

- **Atributos:**

    - `nombre`: nombre del empleado (público).

    - `salarioBase`: salario base (protegido, accesible en subclases).

- **Métodos:**

    - `calcularSalario()`: método abstracto que debe ser implementado por las subclases.

    - `descripcion()`: devuelve una descripción básica del empleado con su nombre.

- **Uso:**

    - Clase base abstracta para definir distintos tipos de empleados con su propia lógica de cálculo de salario.

---

### [Tecnico](src/empleados/Tecnico.scala) (clase abstracta, subclase de Empleado):

- **Atributos:**

    - `experiencia`: años de experiencia (público).

- **Métodos:**

    - `bonoExperiencia()`: calcula un bono fijo si la experiencia es de 5 años o más.

    - `descripcion()`: extiende la descripción del empleado incluyendo la experiencia.

- **Uso:**

    - Clase intermedia que agrega lógica común para técnicos especializados.

---

### [Tester](src/empleados/Tester.scala) (subclase de Tecnico):

- **Atributos:**

    - `tipoPrueba`: tipo de pruebas que realiza (funcionales, automáticas, etc.) (público).

- **Métodos sobrescritos:**

    - `calcularSalario()`: suma al salario base el bono por experiencia.

    - `descripcion()`: añade a la descripción el tipo de pruebas que ejecuta.

- **Uso:**

    - Representa a un técnico especializado en testing, con salario influido por la experiencia y rol específico.



---

### [Desarrollador](src/empleados/Desarrollador.scala) (subclase de Tecnico):

- **Atributos:**

    - `lenguaje`: lenguaje de programación principal (público).

- **Métodos sobrescritos:**

    - `calcularSalario()`: aplica un bono según el lenguaje (mayor si es Scala) y añade el bono por experiencia.

    - `descripcion()`: añade el lenguaje al texto descriptivo del técnico.

- **Uso:**

    - Representa a un técnico especializado en desarrollo, con salario ajustado por experiencia y tecnología usada.

---

### [ArquitectoSoftware](src/empleados/ArquitectoSoftware.scala) (subclase de Desarrollador):

- **Atributos:**

    - `proyectos`: número de proyectos liderados (público).

- **Métodos sobrescritos:**

    - `calcularSalario()`: suma al salario base un bono por cada proyecto.

    - `descripcion()`: extiende la descripción con el rol de arquitecto y número de proyectos.

- **Uso:**

    - Especializa al desarrollador como arquitecto de software, añadiendo responsabilidad por proyectos y reflejándolo en el salario.

---

### [Administrativo](src/empleados/Administrativo.scala) (clase abstracta, subclase de Empleado):

- **Atributos:**

    - `horasExtras`: cantidad de horas extras trabajadas (público).

- **Métodos:**

    - `pagoHorasExtras()`: calcula el pago adicional por horas extras (15 unidades por hora).

    - `descripcion()`: devuelve una descripción que incluye el rol y las horas extras.

- **Uso:**

    - Clase base para empleados administrativos, incorporando lógica común sobre horas extras. Debe ser extendida por clases concretas.

---

### [Asistente](src/empleados/Asistente.scala) (subclase de Administrativo):

- **Atributos:**

    - `oficina`: ubicación o nombre de la oficina asignada (público).

- **Métodos sobrescritos:**

    - `calcularSalario()`: suma el salario base con el pago por horas extras.

    - `descripcion()`: extiende la descripción incluyendo la oficina.

- **Uso:**

    - Representa a un asistente administrativo con asignación de oficina y cálculo de salario basado en horas extras.

---

### [Contador](src/empleados/Contador.scala) (subclase de Administrativo):

- **Atributos:**

    - `informesAnuales`: número de informes anuales elaborados (público).

- **Métodos sobrescritos:**

    - `calcularSalario()`: suma al salario base el pago por horas extras y un bono por cada informe anual.

    - `descripcion()`: añade a la descripción el número de informes anuales realizados.

- **Uso:**

    - Representa a un contador con funciones administrativas y un bono ligado a su producción de informes.

---

### [Gerente](src/empleados/Gerente.scala) (subclase de Empleado):

- **Atributos:**

    - `bono`: cantidad adicional fija agregada al salario (público).

- **Métodos sobrescritos:**

    - `calcularSalario()`: suma el salario base con el bono.

    - `descripcion()`: devuelve una descripción con el nombre y el bono del gerente.

- **Uso:**

    - Representa a un gerente con un salario fijo más un bono adicional. No depende de horas ni experiencia.

## Tests

### [EmpleadoTest](test/empleados/EmpleadoTest.scala) (conjunto de pruebas unitarias):

- **Herramienta:**

    - Usa ScalaTest con AnyFunSuite.

- **Pruebas incluidas:**

    - `ArquitectoSoftware`: verifica salario con experiencia, bono por lenguaje (Scala) y proyectos.

    - `Tester`: valida si el bono por experiencia se aplica correctamente (a partir de 5 años).

    - `Gerente`: prueba que el salario es la suma del salario base y el bono.

    - `Asistente`: comprueba el pago correcto de horas extras.

    - `Contador`: verifica el cálculo del salario con horas extras e informes anuales.

- **Uso:**

    - Asegura el funcionamiento correcto del cálculo de salarios para distintas subclases de Empleado.

## Ficheros de configuración de `SBT`

- [build.sbt](build.sbt)

## Ficheros de configuración 

### [Dockerfile](Dockerfile)
Define la imagen de Docker utilizada en el entorno del proyecto.

### [main.tf](main.tf)
Archivo principal de configuración de infraestructura en Terraform.

### [Jenkinsfile](Jenkinsfile)
Contiene la configuración del pipeline de integración continua (CI) usando Jenkins.

## Despliegue
Se requiere descargar la imagen necesaria para llevar a cabo el despliegue:
````bash
docker build -t myjenkins-sbt .
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
