from typing import Callable, Any, List

class Item:
    def __init__(self, name, category, price, quantity):
        self.name = name
        self.category = category
        self.price = price
        self.quantity = quantity
    
    def __repr__(self):
        return f"Item(name={self.name}, category={self.category}, price={self.price}, quantity={self.quantity})"

def build_equals_filter(attribute: str, value: Any) -> Callable[[Item], bool]:
    """Genera una función lambda para filtrar objetos por igualdad de atributo."""
    return lambda item: getattr(item, attribute) == value

def build_greater_than_filter(attribute: str, umbral: float) -> Callable[[Item], bool]:
    """Genera una función lambda para filtrar objetos con atributos mayores a un umbral."""
    return lambda item: getattr(item, attribute) > umbral

def build_less_than_filter(attribute: str, umbral: float) -> Callable[[Item], bool]:
    """Genera una función lambda para filtrar objetos con atributos menores a un umbral."""
    return lambda item: getattr(item, attribute) < umbral

def build_range_filter(attribute: str, min_value: float, max_value: float) -> Callable[[Item], bool]:
    """Genera una función lambda para filtrar objetos dentro de un rango de valores."""
    return lambda item: min_value <= getattr(item, attribute) <= max_value

def build_contains_filter(attribute: str, substring: str) -> Callable[[Item], bool]:
    """Genera una función lambda para filtrar objetos que contienen un substring en un atributo."""
    return lambda item: substring in getattr(item, attribute)
