from perro import Perro
from gato import Gato
from pajaro import Pajaro

animales = [
    Perro("Firulais"),
    Gato("Miau"),
    Pajaro("Pío")
]

for animal in animales:
    print(animal.describir())
    print(f"Sonido: {animal.hacer_sonido()}")