# archivo: empresa/tests/tests_empresa.py

import unittest
from empresa.empresa import Persona, Empleado, Freelance, DesarrolladorFreelance

# --- CLASES DE TEST ---
# DEBEN empezar con 'Test'
class TestPersona(unittest.TestCase): # CORRECTO: Empieza con Test
    def test_presentarse(self): # CORRECTO: Empieza con test_
        p = Persona("Ana", 25)
        self.assertEqual(p.presentarse(), "Soy Ana, tengo 25 años.")

class TestEmpleado(unittest.TestCase): # CORRECTO
    def test_construccion(self): # CORRECTO
        e = Empleado("Luis", 40, 50000)
        self.assertEqual(e.nombre, "Luis")
        self.assertEqual(e.salario, 50000)

    def test_trabajar(self): # CORRECTO
        e = Empleado("Luis", 40, 50000)
        self.assertIn("trabajando", e.trabajar())

    def test_presentarse(self): # CORRECTO
        e = Empleado("Luis", 40, 50000)
        self.assertIn("gano 50000€", e.presentarse())

class TestFreelance(unittest.TestCase): # CORRECTO
    def test_listar_proyectos(self): # CORRECTO
        f = Freelance(["Web", "App"])
        self.assertEqual(f.listar_proyectos(), "Tengo 2 proyectos activos.")

class TestDesarrolladorFreelance(unittest.TestCase): # CORRECTO
    def test_herencia_mixta(self): # CORRECTO
        d = DesarrolladorFreelance("Marta", 30, 60000, ["Bot", "API"], "Python")
        self.assertEqual(d.nombre, "Marta")
        self.assertEqual(d.lenguaje, "Python")
        self.assertEqual(len(d.proyectos), 2)

    def test_metodos(self): # CORRECTO
        d = DesarrolladorFreelance("Marta", 30, 60000, ["Bot", "API"], "Python")
        self.assertIn("Python", d.desarrollar())
        self.assertIn("freelance", d.presentarse())
        self.assertEqual(d.listar_proyectos(), "Tengo 2 proyectos activos.")

    def test_isinstance(self): # CORRECTO
        d = DesarrolladorFreelance("Marta", 30, 60000, [], "Python")
        self.assertTrue(isinstance(d, Empleado))
        self.assertTrue(isinstance(d, Freelance))
        self.assertTrue(isinstance(d, Persona))

    def test_issubclass(self): # CORRECTO
        self.assertTrue(issubclass(DesarrolladorFreelance, Empleado))
        self.assertTrue(issubclass(Empleado, Persona))
        self.assertFalse(issubclass(Freelance, Persona))


# Esta línea es la clave para la ejecución con unittest.main(),
# PERO Pytest la ignora. Pytest no necesita esta línea.
# Si la quitas, no afectará a Pytest, y no es necesaria para él.
