# Herencia en Python

## Introducción
Bienvenido a este repositorio donde explicaremos y mostraremos los conceptos de Herencia y Polimorfismo en Python, mediante un ejemplo sencillo con un codigo limpio y accesible para cualquiera

## Estructura de Directorio
- `/README.md`: Archivo actual, donde explicaremos los conceptos y mostraremos los pasos a seguir para comprobar el funcionamiento del     ejemplo
- `/herencia.py`: Archivo python donde se encuentra el codigo de ejemplo.


## Conceptos Previos
Antes de comenzar con el codigo donde se muestra los conceptos de herencia y polimorfismo, vamos a explicar algunos conceptos claves necesarios para entender el funcionamiento de la herencia y el polimorfismo en Python.

- **Herencia Básica**:
  La herencia (o herencia básica) permite que una clase (subclase) herede atributos y métodos de otra clase (superclase). En el ejemplo, las clases `Diseñador`, `Desarrollador` y `EspecialistaDeAccesibilidad` actúan como subclases de la superclase `Trabajador` la cual proporciona atributos como `nombre` o `id del empleado`y métodos básicos como `trabajar` o `calcular salario anual`, que son heredados por las otras clases.

- **Herencia Múltiple**:
  Python permite que una clase herede de múltiples clases, proporcionando una forma de combinar funcionalidades de múltiples superclases. Esto puede resultar en una situación compleja cuando diferentes clases base tienen métodos con el mismo nombre. Python utiliza el método de resolución de orden de método (MRO) para determinar el orden en el que se buscan los métodos. Este orden está definido por el algoritmo C3 linearization. En el código, la clase `FullStack` hereda tanto de la clase `Desarrollador` como de `Diseñador`, combinando sus atributos y métodos. 

- **Polimorfismo**:
  El polimorfismo permite que métodos con el mismo nombre actúen de manera diferente en diferentes clases. Por ejemplo, el método `trabajar` se define tanto en `Trabajador` como en `Diseñador`, `Desarrollador` y `EspecialistaDeAccesibilidad`, pero su implementación puede ser diferente en cada clase.

- **Composición de Clases**:
  Además de la herencia, las clases `Diseñador`, `Desarollador` y `EspecialistaDeAccesibilidad` demuestran cómo la composición puede ser utilizada para agregar diferentes capacidades a las clases. Estas clases introducen nuevos métodos como `crear_prototipo`,  `escribir_codigo` y `auditar_interfaz` que son específicos para sus respectivas funcionalidades.

- **Inicialización de Superclases en Herencia Múltiple**:
  En herencia múltiple, es importante inicializar correctamente todas las superclases para asegurarse de que todos los atributos de la clase base se configuren adecuadamente. En la clase `FullStack`, por ejemplo, se llama explícitamente a los inicializadores de `Desarrollador` y `Diseñador` para asegurar una inicialización completa.

Tras esta breve explicación de ciertos conceptos claves del funcionamiento de la herencia y del polimorfismo, mostraremos a continuación el codigo que se ha usado como ejemplo

## Código de Ejemplo
A continuación, se muestra un ejemplo de cómo se pueden utilizar la herencia en Python:
[**herencia.py**](herencia.py)

```python
# Clase base
class Trabajador:
    def __init__(self, nombre, id_empleado, salario_base):
        self.nombre = nombre
        self.id_empleado = id_empleado
        self.salario_base = salario_base

    def __str__(self):
        return f"Nombre: {self.nombre}, ID: {self.id_empleado}"

    def trabajar(self):
        return f"{self.nombre} está realizando tareas generales."

    def calcular_salario_anual(self):
        return self.salario_base

# --- Herencia Simple ---
# Clase Diseñador hereda de Trabajador
class Disenador(Trabajador):
    def __init__(self, nombre, id_empleado, herramienta_preferida, salario_base):
        super().__init__(nombre, id_empleado, salario_base)
        self.herramienta_preferida = herramienta_preferida
        self.bonus_disenador = 5000

    # Sobrescritura de método (Polimorfismo)
    def trabajar(self):
        return f"{self.nombre} está diseñando interfaces con {self.herramienta_preferida}."

    def crear_prototipo(self):
        return f"{self.nombre} está creando un prototipo interactivo."

    def calcular_salario_anual(self):
        return self.salario_base + self.bonus_disenador

# Clase Desarrollador hereda de Trabajador
class Desarrollador(Trabajador):
    def __init__(self, nombre, id_empleado, lenguaje_principal, salario_base):
        super().__init__(nombre, id_empleado, salario_base)
        self.lenguaje_principal = lenguaje_principal
        self.bonus_desarrollador = 8000

    # Sobrescritura de método (Polimorfismo)
    def trabajar(self):
        return f"{self.nombre} está desarrollando software en {self.lenguaje_principal}."

    def escribir_codigo(self):
        return f"{self.nombre} está escribiendo código limpio y eficiente."

    def calcular_salario_anual(self):
        return self.salario_base + self.bonus_desarrollador
    

class EspecialistaDeAccesibilidad(Trabajador):
    def __init__(self, nombre, id_empleado, normativa_referencia, salario_base):
        super().__init__(nombre, id_empleado, salario_base)
        self.normativa_referencia = normativa_referencia
        self.bonus_accesibilidad = 6000

    def trabajar(self):
        return f"{self.nombre} está evaluando accesibilidad según la normativa {self.normativa_referencia}."

    def auditar_interfaz(self):
        return f"{self.nombre} está auditando la interfaz para garantizar accesibilidad."

    def calcular_salario_anual(self):
        return self.salario_base + self.bonus_accesibilidad

# --- Herencia Múltiple ---

# Clase Fullstack hereda de Desarrollador y Disenador
class Fullstack(Desarrollador, Disenador):
    def __init__(self, nombre, id_empleado, lenguaje_principal, herramienta_preferida, anos_experiencia, salario_base):
        Desarrollador.__init__(self, nombre, id_empleado, lenguaje_principal, salario_base)
        Disenador.__init__(self, nombre, id_empleado, herramienta_preferida, salario_base)
        self.salario_base = salario_base
        self.anos_experiencia = anos_experiencia
        self.bonus_fullstack = 10000

    # Sobrescritura de método (Polimorfismo)
    def trabajar(self):
        # Podemos llamar a los métodos de las clases padre si queremos combinar comportamientos
        trabajo_dev = Desarrollador.trabajar(self) # Accedemos directamente
        trabajo_dis = Disenador.trabajar(self)   # Accedemos directamente
        return f"{self.nombre} (Fullstack) está:\n  - {trabajo_dev}\n  - {trabajo_dis}\n  - Integrando frontend y backend."

    def gestionar_proyecto_completo(self):
        return f"{self.nombre} está gestionando un proyecto de desarrollo completo."

    def calcular_salario_anual(self):
        # Salario base de Fullstack + bonus por experiencia y combinación de habilidades
        return self.salario_base + (self.anos_experiencia * 1000) + self.bonus_fullstack

# Clase UXEngineer hereda de Disenador y EspecialistaAccesibilidad
class UXEngineer(Disenador, EspecialistaDeAccesibilidad):
    def __init__(self, nombre, id_empleado, herramienta_preferida, normativa_referencia, especialidad_ux, anos_experiencia, salario_base):
        Disenador.__init__(self, nombre, id_empleado, herramienta_preferida, salario_base)
        EspecialistaDeAccesibilidad.__init__(self, nombre, id_empleado, normativa_referencia, salario_base)
        self.salario_base = salario_base
        self.especialidad_ux = especialidad_ux
        self.anos_experiencia = anos_experiencia
        self.bonus_ux = 9000

    def trabajar(self):
        trabajo_dis = Disenador.trabajar(self)
        trabajo_acc = EspecialistaDeAccesibilidad.trabajar(self)
        return f"{self.nombre} (UX Engineer) está:\n  - {trabajo_dis}\n  - {trabajo_acc}\n  - Diseñando experiencias centradas en el usuario ({self.especialidad_ux})."

    def realizar_pruebas_usabilidad(self):
        return f"{self.nombre} está conduciendo pruebas de usabilidad."

    def calcular_salario_anual(self):
        return self.salario_base + (self.anos_experiencia * 1000) + self.bonus_ux
```

Ahora vamos a explicar los métodos que se han usado en el ejemplo:


Este código proporciona un claro ejemplo de cómo la herencia simple y la herencia múltiple pueden ser usadas en Python para crear jerarquías de clases flexibles y reutilizar funcionalidades, permitiendo a las subclases extender o modificar comportamientos de las superclases. La herencia también facilita la implementación de polimorfismo, donde métodos con el mismo nombre pueden tener comportamientos diferentes en diferentes clases.


A continuacion mostraremos como se verificaran este codigo

## Ejecución desde una Terminal
