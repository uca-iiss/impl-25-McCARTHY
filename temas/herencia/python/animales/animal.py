from abc import ABC, abstractmethod

class Animal(ABC):
    def __init__(self, name: str):
        self.name = name

    @abstractmethod
    def hacer_sonido(self) -> str:
        pass

    @abstractmethod
    def describir(self) -> str:
        pass