import unittest                                                 # Importar el módulo de pruebas unitarias
from temas.aspectos.python.src.operaciones import Calculadora   # Importar la clase Calculadora desde el módulo operaciones

class TestCalculadora(unittest.TestCase):
    """Pruebas unitarias para la clase Calculadora"""
    
    def setUp(self):
        """Configuración inicial antes de cada prueba"""
        self.calc = Calculadora()                               # Crear una nueva instancia de la calculadora
    
    def test_suma(self):
        """Prueba para verificar la operación de suma"""
        self.assertEqual(self.calc.suma(2, 3), 5)               # Verificar que 2 + 3 = 5
    
    def test_resta(self):
        """Prueba para verificar la operación de resta"""
        self.assertEqual(self.calc.resta(5, 3), 2)              # Verificar que 5 - 3 = 2
    
    def test_multiplicacion(self):
        """Prueba para verificar la operación de multiplicación"""
        self.assertEqual(self.calc.multiplicacion(2, 3), 6)     # Verificar que 2 * 3 = 6
    
    def test_division(self):
        """Prueba para verificar la operación de división"""
        self.assertEqual(self.calc.division(6, 3), 2)           # Verificar que 6 / 3 = 2
    
    def test_division_por_cero(self):
        """Prueba para verificar la división por cero"""
        with self.assertRaises(ValueError):                     # Verificar que se lance un ValueError al dividir por cero
            self.calc.division(5, 0)

# Ejecutar las pruebas si este script es ejecutado directamente
if __name__ == "__main__":
    unittest.main()
