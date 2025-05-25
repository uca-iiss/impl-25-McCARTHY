from dataclasses import dataclass
from typing import List

@dataclass
class LogEntry:
    timestamp: str
    nivel: str
    mensaje: str

def ejemplo_logs() -> List[str]:
    return [
        "2025-05-20 12:00:01 ERROR Fallo de conexión",
        "2025-05-20 12:01:01 INFO Usuario conectado",
        "2025-05-20 12:02:01 WARNING Memoria alta",
        "2025-05-20 12:03:01 ERROR Timeout alcanzado",
        "2025-05-20 12:04:01 DEBUG Señal recibida",
        "2025-05-20 12:05:01 INFO Proceso terminado"
    ]

