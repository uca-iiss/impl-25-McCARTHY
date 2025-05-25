# main.py

from app.logs import ejemplo_logs
from app.procesador import (
    parsear_log,
    filtrar_por_nivel,
    contar_por_nivel,
    ordenar_logs,
    agrupar_por_nivel
)

def main():
    logs_raw = ejemplo_logs()
    logs = list(map(parsear_log, logs_raw))

    print("\n--- Filtrando errores ---")
    for log in filtrar_por_nivel(logs, 'ERROR'):
        print(log)

    print("\n--- Conteo por nivel ---")
    print(contar_por_nivel(logs))

    print("\n--- Logs ordenados ---")
    for log in ordenar_logs(logs):
        print(log)

    print("\n--- Agrupación por nivel ---")
    agrupados = agrupar_por_nivel(logs)
    for nivel, grupo in agrupados.items():
        print(f"{nivel}: {len(grupo)} entradas")

if __name__ == "__main__":
    main()
