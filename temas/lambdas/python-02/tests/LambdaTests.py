import unittest
import sys
import os

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '../src')))

from Program import (
    lambda_add, lambda_even, lambda_order,
    orden_superior_map, orden_superior_filter, orden_superior_reduce, orden_superior_sorted,
    closure, OrdenarPorLongitud, FiltrarPorPar,
    ejemplo_procesamiento_stream
)

class TestFuncionesFuncionales(unittest.TestCase):

    def setUp(self):
        self.datos = [1, 2, 3, 4, 5]
        self.cadenas = ["python", "universidad", "hola"]

    def test_map(self):
        self.assertEqual(orden_superior_map(lambda x: x * 2, self.datos), [2, 4, 6, 8, 10])

    def test_filter(self):
        self.assertEqual(orden_superior_filter(lambda_even, self.datos), [2, 4])

    def test_reduce(self):
        self.assertEqual(orden_superior_reduce(lambda_add, self.datos), 15)

    def test_sorted(self):
        self.assertEqual(orden_superior_sorted(lambda_order, self.cadenas),
                         ["hola", "python", "universidad"])

    def test_closure(self):
        duplicar = closure(2)
        self.assertEqual(duplicar(10), 20)

    def test_interface_sorted(self):
        self.assertEqual(orden_superior_sorted(OrdenarPorLongitud(), self.cadenas),
                         ["hola", "python", "universidad"])

    def test_interface_filter(self):
        self.assertEqual(orden_superior_filter(FiltrarPorPar(), self.datos), [2, 4])

    def test_stream(self):
        self.assertEqual(ejemplo_procesamiento_stream(self.datos), 20)  # 4 + 16

if __name__ == "__main__":
    unittest.main()
