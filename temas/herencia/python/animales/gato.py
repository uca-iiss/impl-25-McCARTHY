from .animal import Animal

class Gato(Animal):
    def __init__(self, nombre: str):
        super().__init__(nombre)

    def hacer_sonido(self) -> str:
        return "Miau"

    def describir(self) -> str:
        return f"Soy un gato y me llamo {self.name}"