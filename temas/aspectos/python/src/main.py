from operaciones import Calculadora                 # Importar la clase Calculadora desde el módulo operaciones
from aspectos import aplicar_aspectos               # Importar la función de aplicación de aspectos

# Aplicar los aspectos antes de usar la calculadora
aplicar_aspectos()

def demo_aspectos():
    """Función para demostrar el funcionamiento de los aspectos en las operaciones"""
    calc = Calculadora()                            # Crear una instancia de la calculadora
    
    # Probar la operación de suma
    print("\n--- Probando suma ---")
    resultado = calc.suma(5, 3)                     # Sumar 5 y 3
    print(f"Resultado: {resultado}")
    
    # Probar la operación de división exitosa
    print("\n--- Probando división exitosa ---")
    resultado = calc.division(10, 2)                # Dividir 10 entre 2
    print(f"Resultado: {resultado}")
    
    # Probar la operación de división por cero
    print("\n--- Probando división por cero ---")
    try:
        resultado = calc.division(10, 0)            # Intentar dividir por cero
    except ValueError as e:                         # Capturar el error esperado
        print(f"Error esperado: {e}")

# Ejecutar la función demo_aspectos solo si el script es ejecutado directamente
if __name__ == "__main__":
    demo_aspectos()
