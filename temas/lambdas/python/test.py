import unittest
from lambdas import *

class TestLambdas(unittest.TestCase):

    def test_operacion_basica(self):
        resultado = operacion_basica()
        self.assertEqual(resultado, 28)

    def test_transformar_lista(self):
        resultado = transformar_lista()
        self.assertEqual(resultado, ["MANZANA", "BANANA", "CEREZA"])

    def test_filtrar_numeros(self):
        resultado = filtrar_numeros()
        self.assertEqual(resultado, [12, 21, 15])

    def test_evaluacion_condicional(self):
        resultado_neg = evaluacion_condicional()
        self.assertEqual(resultado_neg, "Negativo")

        clasificar = lambda x: "Positivo" if x > 0 else ("Negativo" if x < 0 else "Cero")
        self.assertEqual(clasificar(10), "Positivo")
        self.assertEqual(clasificar(0), "Cero")

    def test_concatenar_cadenas(self):
        resultado = concatenar_cadenas()
        self.assertEqual(resultado, "Python es genial")

if __name__ == "__main__":
    unittest.main()
