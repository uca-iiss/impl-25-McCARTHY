import unittest
from herencia import *

class TestTrabajadores(unittest.TestCase):

    def test_instancias(self):
        t = Trabajador(nombre="Ana", id_empleado=1, salario_base=30000)
        d = Disenador(herramienta_preferida="Figma", nombre="Luis", id_empleado=2, salario_base=35000)
        dev = Desarrollador(lenguaje_principal="Python", nombre="María", id_empleado=3, salario_base=40000)
        acc = EspecialistaDeAccesibilidad(normativa_referencia="WCAG", nombre="Carlos", id_empleado=4, salario_base=37000)
        fs = Fullstack(lenguaje_principal="JavaScript", herramienta_preferida="Sketch", experiencia_fullstack=3,
                       nombre="Laura", id_empleado=5, salario_base=45000)
        ux = UXEngineer(herramienta_preferida="Adobe XD", normativa_referencia="ADA",
                        especialidad_ux="UX Writing", experiencia_ux=2,
                        nombre="Pepe", id_empleado=6, salario_base=42000)

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
        dev = Desarrollador(lenguaje_principal="Java", nombre="Mario", id_empleado=7, salario_base=41000)
        self.assertEqual(dev.trabajar(), "Mario está desarrollando software en Java.")

        dis = Disenador(herramienta_preferida="Figma", nombre="Julia", id_empleado=8, salario_base=36000)
        self.assertEqual(dis.trabajar(), "Julia está diseñando interfaces con Figma.")

        acc = EspecialistaDeAccesibilidad(normativa_referencia="WCAG", nombre="Sonia", id_empleado=9, salario_base=38000)
        self.assertEqual(acc.trabajar(), "Sonia está evaluando accesibilidad según la normativa WCAG.")

        fs = Fullstack(lenguaje_principal="Python", herramienta_preferida="Figma", experiencia_fullstack=2,
                       nombre="Fran", id_empleado=10, salario_base=50000)
        self.assertIn("Fran (Fullstack) está:", fs.trabajar())

        ux = UXEngineer(herramienta_preferida="Sketch", normativa_referencia="ADA", especialidad_ux="Research", experiencia_ux=4,
                        nombre="Leo", id_empleado=11, salario_base=43000)
        self.assertIn("Leo (UX Engineer) está:", ux.trabajar())

    def test_salario_anual(self):
        d = Disenador(herramienta_preferida="Figma", nombre="Luis", id_empleado=2, salario_base=35000)
        self.assertEqual(d.calcular_salario_anual(), 35000 + 5000)

        dev = Desarrollador(lenguaje_principal="Python", nombre="María", id_empleado=3, salario_base=40000)
        self.assertEqual(dev.calcular_salario_anual(), 40000 + 8000)

        acc = EspecialistaDeAccesibilidad(normativa_referencia="WCAG", nombre="Carlos", id_empleado=4, salario_base=37000)
        self.assertEqual(acc.calcular_salario_anual(), 37000 + 6000)

        fs = Fullstack(lenguaje_principal="JavaScript", herramienta_preferida="Sketch", experiencia_fullstack=3,
                       nombre="Laura", id_empleado=5, salario_base=45000)
        self.assertEqual(fs.calcular_salario_anual(), 45000 + (3 * 1000) + 10000)

        ux = UXEngineer(herramienta_preferida="Adobe XD", normativa_referencia="ADA",
                        especialidad_ux="UX Writing", experiencia_ux=2,
                        nombre="Pepe", id_empleado=6, salario_base=42000)
        self.assertEqual(ux.calcular_salario_anual(), 42000 + (2 * 1000) + 9000)

    def test_metodos_propios(self):
        d = Disenador(herramienta_preferida="Figma", nombre="Paula", id_empleado=12, salario_base=30000)
        self.assertEqual(d.crear_prototipo(), "Paula está creando un prototipo interactivo.")

        dev = Desarrollador(lenguaje_principal="C++", nombre="Mario", id_empleado=13, salario_base=31000)
        self.assertEqual(dev.escribir_codigo(), "Mario está escribiendo código limpio y eficiente.")

        acc = EspecialistaDeAccesibilidad(normativa_referencia="EN301549", nombre="Lucía", id_empleado=14, salario_base=32000)
        self.assertEqual(acc.auditar_interfaz(), "Lucía está auditando la interfaz para garantizar accesibilidad.")

        fs = Fullstack(lenguaje_principal="Java", herramienta_preferida="Sketch", experiencia_fullstack=5,
                       nombre="Fran", id_empleado=15, salario_base=47000)
        self.assertEqual(fs.gestionar_proyecto_completo(), "Fran está gestionando un proyecto de desarrollo completo.")

        ux = UXEngineer(herramienta_preferida="Adobe XD", normativa_referencia="WCAG", especialidad_ux="Visual Design", experiencia_ux=4,
                        nombre="Leo", id_empleado=16, salario_base=41000)
        self.assertEqual(ux.realizar_pruebas_usabilidad(), "Leo está conduciendo pruebas de usabilidad.")

if __name__ == '__main__':
    unittest.main()
