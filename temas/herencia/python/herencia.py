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
        Disenador.__init__(self, nombre, id_empleado, herramienta_preferida, salario_base)
        Desarrollador.__init__(self, nombre, id_empleado, lenguaje_principal, salario_base)
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
