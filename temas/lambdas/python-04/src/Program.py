from functools import reduce

# === LAMBDAS ===
lambda_add = lambda x, y: x + y
lambda_even = lambda x: x % 2 == 0
lambda_order = lambda x: len(x)

# === FUNCIONES DE ORDEN SUPERIOR ===

def orden_superior_map(funcion, lista):
    return list(map(funcion, lista))

def orden_superior_filter(funcion, lista):
    return list(filter(funcion, lista))

def orden_superior_reduce(funcion, lista):
    return reduce(funcion, lista)

def orden_superior_sorted(funcion, lista):
    return sorted(lista, key=funcion)

# === CLAUSURA ===

def closure(n):
    return lambda x: x * n

# === INTERFAZ FUNCIONAL SIMULADA ===

class OrdenarPorLongitud:
    def __call__(self, cadena):
        return len(cadena)

class FiltrarPorPar:
    def __call__(self, numero):
        return numero % 2 == 0

# === PROCESAMIENTO ESTILO STREAM ===

def ejemplo_procesamiento_stream(lista):
    return reduce(
        lambda x, y: x + y,
        map(lambda x: x ** 2,
            filter(lambda x: x % 2 == 0, lista))
    )
