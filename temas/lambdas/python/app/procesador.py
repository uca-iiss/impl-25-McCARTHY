from app.logs import LogEntry
from typing import List, Dict
from functools import reduce
from itertools import groupby
from operator import attrgetter
from collections import Counter

def parsear_log(log_linea: str) -> LogEntry:
    partes = log_linea.strip().split(' ', 3)
    return LogEntry(
        timestamp=f"{partes[0]} {partes[1]}",
        nivel=partes[2],
        mensaje=partes[3] if len(partes) > 3 else ''
    )

def filtrar_por_nivel(logs: List[LogEntry], nivel: str = 'ERROR') -> List[LogEntry]:
    return list(filter(lambda log: log.nivel == nivel, logs))

def contar_por_nivel(logs: List[LogEntry]) -> Dict[str, int]:
    return dict(Counter(map(lambda log: log.nivel, logs)))

def ordenar_logs(logs: List[LogEntry]) -> List[LogEntry]:
    return sorted(logs, key=attrgetter('timestamp'))

def agrupar_por_nivel(logs: List[LogEntry]) -> Dict[str, List[LogEntry]]:
    logs_ordenados = sorted(logs, key=attrgetter('nivel'))
    return {
        nivel: list(grupo)
        for nivel, grupo in groupby(logs_ordenados, key=attrgetter('nivel'))
    }

