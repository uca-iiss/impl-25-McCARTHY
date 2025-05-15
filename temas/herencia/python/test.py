import unittest
from herencia import *  

class TestTrabajadores(unittest.TestCase):

    def test_instancias(self):
        t = Trabajador("Ana", 1, 30000)
        d = Disenador("Luis", 2, "Figma", 35000)
        dev = Desarrollador("María", 3, "Python", 40000)
        acc = EspecialistaDeAccesibilidad("Carlos", 4, "WCAG", 37000)
        fs = Fullstack("Laura", 5, "JavaScript", "Sketch", 3, 45000)
        ux = UXEngineer("Pepe", 6, "Adobe XD", "ADA", "UX Writing", 2, 42000)

        self.assertIsInstance(d, Trabajador)
        self.assertIsInstance(dev, Trabajador)
        self.assertIsInstance(acc, Trabajador)
        self.assertIsInstance(fs, Desarrollador)
        self.assertIsInstance(fs, Disenador)
        self.assertIsInstance(fs, Trabajador)
        self.assertIsInstance(ux, Disenador)
        self.assertIsInstance(ux, EspecialistaDeAccesibilidad)
        self.assertIsInstance(ux, Trabajador)

    def test_herencia(self):
        self.assertTrue(issubclass(Disenador, Trabajador))
        self.assertTrue(issubclass(Desarrollador, Trabajador))
        self.assertTrue(issubclass(EspecialistaDeAccesibilidad, Trabajador))
        self.assertTrue(issubclass(Fullstack, Desarrollador))
        self.assertTrue(issubclass(Fullstack, Disenador))
        self.assertTrue(issubclass(UXEngineer, Disenador))
        self.assertTrue(issubclass(UXEngineer, EspecialistaDeAccesibilidad))

    def test_trabajar(self):
        dev = Desarrollador("Mario", 7, "Java", 41000)
        self.assertEqual(dev.trabajar(), "Mario está desarrollando software en Java.")

        dis = Disenador("Julia", 8, "Figma", 36000)
        self.assertEqual(dis.trabajar(), "Julia está diseñando interfaces con Figma.")

        acc = EspecialistaDeAccesibilidad("Sonia", 9, "WCAG", 38000)
        self.assertEqual(acc.trabajar(), "Sonia está evaluando accesibilidad según la normativa WCAG.")

        fs = Fullstack("Fran", 10, "Python", "Figma", 2, 50000)
        self.assertIn("Fran (Fullstack) está:", fs.trabajar())

        ux = UXEngineer("Leo", 11, "Sketch", "ADA", "Research", 4, 43000)
        self.assertIn("Leo (UX Engineer) está:", ux.trabajar())

    def test_salario_anual(self):
        d = Disenador("Luis", 2, "Figma", 35000)
        self.assertEqual(d.calcular_salario_anual(), 35000 + 5000)

        dev = Desarrollador("María", 3, "Python", 40000)
        self.assertEqual(dev.calcular_salario_anual(), 40000 + 8000)

        acc = EspecialistaDeAccesibilidad("Carlos", 4, "WCAG", 37000)
        self.assertEqual(acc.calcular_salario_anual(), 37000 + 6000)

        fs = Fullstack("Laura", 5, "JavaScript", "Sketch", 3, 45000)
        self.assertEqual(fs.calcular_salario_anual(), 45000 + (3 * 1000) + 10000)

        ux = UXEngineer("Pepe", 6, "Adobe XD", "ADA", "UX Writing", 2, 42000)
        self.assertEqual(ux.calcular_salario_anual(), 42000 + (2 * 1000) + 9000)

    def test_metodos_propios(self):
        d = Disenador("Paula", 12, "Figma", 30000)
        self.assertEqual(d.crear_prototipo(), "Paula está creando un prototipo interactivo.")

        dev = Desarrollador("Mario", 13, "C++", 31000)
        self.assertEqual(dev.escribir_codigo(), "Mario está escribiendo código limpio y eficiente.")

        acc = EspecialistaDeAccesibilidad("Lucía", 14, "EN301549", 32000)
        self.assertEqual(acc.auditar_interfaz(), "Lucía está auditando la interfaz para garantizar accesibilidad.")

        fs = Fullstack("Fran", 15, "Java", "Sketch", 5, 47000)
        self.assertEqual(fs.gestionar_proyecto_completo(), "Fran está gestionando un proyecto de desarrollo completo.")

        ux = UXEngineer("Leo", 16, "Adobe XD", "WCAG", "Visual Design", 4, 41000)
        self.assertEqual(ux.realizar_pruebas_usabilidad(), "Leo está conduciendo pruebas de usabilidad.")


if __name__ == '__main__':
    unittest.main()
