from app.procesador import parsear_log, filtrar_por_nivel, contar_por_nivel, ordenar_logs, agrupar_por_nivel

raw_logs = [
    "2025-05-20 12:00:01 ERROR Fallo de conexión",
    "2025-05-20 12:01:01 INFO Usuario conectado",
    "2025-05-20 12:02:01 WARNING Memoria alta",
    "2025-05-20 12:03:01 ERROR Timeout alcanzado"
]

parsed_logs = list(map(parsear_log, raw_logs))

def test_filtrar_por_nivel():
    errores = filtrar_por_nivel(parsed_logs, 'ERROR')
    assert len(errores) == 2
    assert errores[0]['mensaje'] == 'Fallo de conexión'

def test_contar_por_nivel():
    conteo = contar_por_nivel(parsed_logs)
    assert conteo == {'ERROR': 2, 'INFO': 1, 'WARNING': 1}

def test_ordenar_logs():
    ordenados = ordenar_logs(parsed_logs)
    assert ordenados[0]['timestamp'] == '2025-05-20 12:00:01'

def test_agrupar_por_nivel():
    grupos = agrupar_por_nivel(parsed_logs)
    assert 'ERROR' in grupos and len(grupos['ERROR']) == 2
