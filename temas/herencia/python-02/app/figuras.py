# app/figuras.py

class Figura:
    def area(self):
        raise NotImplementedError("Método no implementado")

class Rectangulo(Figura):
    def __init__(self, ancho, alto):
        self.ancho = ancho
        self.alto = alto

    def area(self):
        return self.ancho * self.alto

class Circulo(Figura):
    def __init__(self, radio):
        self.radio = radio

    def area(self):
        return 3.1416 * (self.radio ** 2)
