from app.procesador import *

raw_logs = [
    "2025-05-20 12:00:01 ERROR Fallo de conexión",
    "2025-05-20 12:01:01 INFO Usuario conectado",
    "2025-05-20 12:02:01 WARNING Memoria alta",
    "2025-05-20 12:03:01 ERROR Timeout alcanzado"
]

logs = list(map(parsear_log, raw_logs))

print("\n Filtrando errores:")
for log in filtrar_por_nivel(logs, 'ERROR'):
    print(log)

print("\n Conteo por nivel:")
print(contar_por_nivel(logs))

print("\n Logs ordenados:")
for log in ordenar_logs(logs):
    print(log)

print("\n Agrupación por nivel:")
grupos = agrupar_por_nivel(logs)
for nivel, grupo in grupos.items():
    print(f"{nivel}: {len(grupo)} entradas")
