from .animal import Animal

class Pajaro(Animal):
    def __init__(self, nombre: str):
        super().__init__(nombre)

    def hacer_sonido(self) -> str:
        return "Pío"

    def describir(self) -> str:
        return f"Soy un pájaro y me llamo {self.name}"