import sys
import os
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..')))

from animales.animal import Animal
from animales.perro import Perro
from animales.gato import Gato
from animales.pajaro import Pajaro



def test_perro():
    rex = Perro("Rex")
    assert (rex.hacer_sonido(), "Guau")
    assert (rex.describir(), "Soy un perro y me llamo Rex")
    assert isinstance(rex, Animal)  # Verifica que Perro es un Animal

def test_gato():
    misu = Gato("Misu")
    assert (misu.hacer_sonido(), "Miau")
    assert (misu.describir(), "Soy un gato y me llamo Misu")
    assert isinstance(misu, Animal)  # Verifica que Gato es un Animal

def test_pajaro():
    piolin = Pajaro("Piolín")
    assert (piolin.hacer_sonido(), "Pío")
    assert (piolin.describir(), "Soy un pájaro y me llamo Piolín")
    assert isinstance(piolin, Animal)  # Verifica que Pájaro es un Animal
