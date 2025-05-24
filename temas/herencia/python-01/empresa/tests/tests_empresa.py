# archivo: test_empresa.py

import unittest
from empresa import Persona, Empleado, Freelance, DesarrolladorFreelance

class TestPersona(unittest.TestCase):
    def test_presentarse(self):
        p = Persona("Ana", 25)
        self.assertEqual(p.presentarse(), "Soy Ana, tengo 25 años.")

class TestEmpleado(unittest.TestCase):
    def test_construccion(self):
        e = Empleado("Luis", 40, 50000)
        self.assertEqual(e.nombre, "Luis")
        self.assertEqual(e.salario, 50000)

    def test_trabajar(self):
        e = Empleado("Luis", 40, 50000)
        self.assertIn("trabajando", e.trabajar())

    def test_presentarse(self):
        e = Empleado("Luis", 40, 50000)
        self.assertIn("gano 50000€", e.presentarse())

class TestFreelance(unittest.TestCase):
    def test_listar_proyectos(self):
        f = Freelance(["Web", "App"])
        self.assertEqual(f.listar_proyectos(), "Tengo 2 proyectos activos.")

class TestDesarrolladorFreelance(unittest.TestCase):
    def test_herencia_mixta(self):
        d = DesarrolladorFreelance("Marta", 30, 60000, ["Bot", "API"], "Python")
        self.assertEqual(d.nombre, "Marta")
        self.assertEqual(d.lenguaje, "Python")
        self.assertEqual(len(d.proyectos), 2)

    def test_metodos(self):
        d = DesarrolladorFreelance("Marta", 30, 60000, ["Bot", "API"], "Python")
        self.assertIn("Python", d.desarrollar())
        self.assertIn("freelance", d.presentarse())
        self.assertEqual(d.listar_proyectos(), "Tengo 2 proyectos activos.")

    def test_isinstance(self):
        d = DesarrolladorFreelance("Marta", 30, 60000, [], "Python")
        self.assertTrue(isinstance(d, Empleado))
        self.assertTrue(isinstance(d, Freelance))
        self.assertTrue(isinstance(d, Persona))

    def test_issubclass(self):
        self.assertTrue(issubclass(DesarrolladorFreelance, Empleado))
        self.assertTrue(issubclass(Empleado, Persona))
        self.assertFalse(issubclass(Freelance, Persona))  # Freelance no hereda de Persona


if __name__ == "__main__":
    unittest.main()
