import unittest
from animal import Animal
from perro import Perro
from gato import Gato
from pajaro import Pajaro

class TestAnimales(unittest.TestCase):

    def test_perro(self):
        rex = Perro("Rex")
        self.assertEqual(rex.hacer_sonido(), "Guau")
        self.assertEqual(rex.describir(), "Soy un perro y me llamo Rex")
        self.assertIsInstance(rex, Animal)  # Verifica que Perro es un Animal

    def test_gato(self):
        misu = Gato("Misu")
        self.assertEqual(misu.hacer_sonido(), "Miau")
        self.assertEqual(misu.describir(), "Soy un gato y me llamo Misu")
        self.assertIsInstance(misu, Animal)  # Verifica que Gato es un Animal

    def test_pajaro(self):
        piolin = Pajaro("Piolín")
        self.assertEqual(piolin.hacer_sonido(), "Pío")
        self.assertEqual(piolin.describir(), "Soy un pájaro y me llamo Piolín")
        self.assertIsInstance(piolin, Animal)  # Verifica que Pájaro es un Animal


if __name__ == "__main__":
    unittest.main()
