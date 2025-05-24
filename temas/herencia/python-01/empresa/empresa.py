# archivo: empresa.py

# Clase base general
class Persona:
    def __init__(self, nombre, edad):
        self.nombre = nombre
        self.edad = edad

    def presentarse(self):
        return f"Soy {self.nombre}, tengo {self.edad} años."

# Clase intermedia: herencia simple
class Empleado(Persona):
    def __init__(self, nombre, edad, salario):
        # Llamamos al constructor de Persona
        super().__init__(nombre, edad)
        self.salario = salario

    def trabajar(self):
        return f"{self.nombre} está trabajando."

    def presentarse(self):
        # Sobrescribimos el método base pero reutilizamos parte
        return f"{super().presentarse()} y gano {self.salario}€ al año."

# Clase independiente (no hereda de Persona)
class Freelance:
    def __init__(self, proyectos):
        self.proyectos = proyectos

    def listar_proyectos(self):
        return f"Tengo {len(self.proyectos)} proyectos activos."

# Herencia múltiple: combina Empleado y Freelance
class DesarrolladorFreelance(Empleado, Freelance):
    def __init__(self, nombre, edad, salario, proyectos, lenguaje):
        # Llamamos al constructor de Empleado con super()
        super().__init__(nombre, edad, salario)
        # Freelance no está en la cadena de super(), se llama aparte
        Freelance.__init__(self, proyectos)
        self.lenguaje = lenguaje

    def desarrollar(self):
        return f"{self.nombre} está programando en {self.lenguaje}."

    def presentarse(self):
        # Usamos presentaciones de ambos lados
        return f"{Empleado.presentarse(self)} También soy freelance."

# Código de prueba
if __name__ == "__main__":
    # Crear instancias
    persona = Persona("Laura", 30)
    empleado = Empleado("Carlos", 35, 30000)
    freelance = Freelance(["App Móvil", "Sitio Web"])
    dev = DesarrolladorFreelance("Marta", 28, 40000, ["API REST", "IA"], "Python")

    # Mostrar presentaciones
    print(persona.presentarse())
    print(empleado.presentarse())
    print(dev.presentarse())
    print(dev.desarrollar())
    print(dev.listar_proyectos())

    print("\n--- Información de tipos y herencias ---")

    # type(): muestra el tipo exacto del objeto
    print(f"Tipo de 'dev': {type(dev)}")

    # isinstance(objeto, clase): verifica si un objeto es instancia de una clase o subclase
    print(f"¿dev es una instancia de Empleado? {isinstance(dev, Empleado)}")
    print(f"¿dev es una instancia de Freelance? {isinstance(dev, Freelance)}")
    print(f"¿empleado es una instancia de DesarrolladorFreelance? {isinstance(empleado, DesarrolladorFreelance)}")

    # issubclass(clase1, clase2): verifica si clase1 hereda de clase2
    print(f"¿DesarrolladorFreelance hereda de Empleado? {issubclass(DesarrolladorFreelance, Empleado)}")
    print(f"¿Empleado hereda de Persona? {issubclass(Empleado, Persona)}")
    print(f"¿Freelance hereda de Persona? {issubclass(Freelance, Persona)}")  # False
