from functools import reduce
from collections import Counter

# Log de ejemplo: "2025-05-20 12:00:01 ERROR Fallo de conexión"
def parsear_log(log_linea):
    partes = log_linea.strip().split(' ', 3)
    return {
        'timestamp': f"{partes[0]} {partes[1]}",
        'nivel': partes[2],
        'mensaje': partes[3] if len(partes) > 3 else ''
    }

# Función para filtrar por nivel usando una lambda
def filtrar_por_nivel(logs, nivel='ERROR'):
    return list(filter(lambda log: log['nivel'] == nivel, logs))

# Función para contar logs por nivel
def contar_por_nivel(logs):
    niveles = list(map(lambda log: log['nivel'], logs))
    return dict(Counter(niveles))

# Ordenar por timestamp
def ordenar_logs(logs):
    return sorted(logs, key=lambda log: log['timestamp'])

# Agrupar por nivel (usando reduce y lambdas)
def agrupar_por_nivel(logs):
    return reduce(lambda acc, log: {**acc, log['nivel']: acc.get(log['nivel'], []) + [log]}, logs, {})
