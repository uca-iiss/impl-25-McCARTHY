# tests/test_figuras.py
import pytest
from app.figuras import Rectangulo, Circulo

def test_area_rectangulo():
    r = Rectangulo(4, 5)
    assert r.area() == 20

def test_area_circulo():
    c = Circulo(3)
    assert round(c.area(), 2) == 28.27
