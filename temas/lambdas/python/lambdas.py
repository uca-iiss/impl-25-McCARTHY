from functools import reduce

def operacion_basica():
  #Multiplicamos dos números
  multiplicar = lambda a, b: a * b
  return multiplicar(7, 4)

def transformar_lista():
  #Conviertimos una lista de cadenas a mayúsculas
  palabras = ["manzana", "banana", "cereza"]
  return list(map(lambda palabra: palabra.upper(), palabras)) 

def filtrar_numeros():
  #Filtramos una lista de números para obtener solo los mayores de 10
  numeros = [5, 12, 8, 21, 15, 3]
  return list(filter(lambda num: num > 10, numeros)) 

def evaluacion_condicional():
  #Determinamos si un número es positivo, negativo o cero 
  clasificar_numero = lambda x: "Positivo" if x > 0 else ("Negativo" if x < 0 else "Cero")
  return clasificar_numero(-5)

def concatenar_cadenas():
  #Concatena una lista de cadenas
  fragmentos = ["Python", " ", "es", " ", "genial"]
  return reduce(lambda acumulador, elemento: acumulador + elemento, fragmentos)
