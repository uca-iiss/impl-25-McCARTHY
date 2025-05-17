from .animal import Animal

class Perro(Animal):
    def __init__(self, nombre: str):
        super().__init__(nombre)

    def hacer_sonido(self) -> str:
        return "Guau"

    def describir(self) -> str:
        return f"Soy un perro y me llamo {self.name}"