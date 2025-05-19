class Calculadora:
    """
    Clase que realiza operaciones matemáticas básicas.
    Será el objetivo de nuestros aspectos.
    """
    
    #Suma dos números
    def suma(self, a, b):
        return a + b
    
    #Resta dos números
    def resta(self, a, b):
        return a - b
    
    #Multiplica dos números
    def multiplicacion(self, a, b):
        return a * b
    
    #Divide dos números
    def division(self, a, b):
        #Lanza una excepción si se intenta dividir por cero
        if b == 0:
            raise ValueError("No se puede dividir por cero")
        return a / b