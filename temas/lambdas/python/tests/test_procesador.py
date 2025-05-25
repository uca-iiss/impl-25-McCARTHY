from app.procesador import (
    parsear_log, filtrar_por_nivel,
    contar_por_nivel, ordenar_logs, agrupar_por_nivel
)

def test_parsear_log():
    log = parsear_log("2025-05-20 12:00:01 ERROR Fallo de conexión")
    assert log.nivel == "ERROR"
    assert "conexión" in log.mensaje

def test_filtrar_por_nivel():
    logs = [parsear_log(l) for l in [
        "2025-05-20 12:00:01 ERROR Uno",
        "2025-05-20 12:00:02 INFO Dos"
    ]]
    errores = filtrar_por_nivel(logs)
    assert len(errores) == 1
    assert errores[0].nivel == "ERROR"

def test_contar_por_nivel():
    logs = [parsear_log(l) for l in [
        "2025-05-20 12:00:01 ERROR Uno",
        "2025-05-20 12:00:02 ERROR Dos",
        "2025-05-20 12:00:03 INFO Tres"
    ]]
    conteo = contar_por_nivel(logs)
    assert conteo == {"ERROR": 2, "INFO": 1}

def test_ordenar_logs():
    logs = [parsear_log(l) for l in [
        "2025-05-20 12:02:01 INFO Tercero",
        "2025-05-20 12:00:01 ERROR Primero",
        "2025-05-20 12:01:01 WARNING Segundo"
    ]]
    ordenados = ordenar_logs(logs)
    assert ordenados[0].mensaje == "Primero"

def test_agrupar_por_nivel():
    logs = [parsear_log(l) for l in [
        "2025-05-20 12:00:01 ERROR Uno",
        "2025-05-20 12:00:02 INFO Dos",
        "2025-05-20 12:00:03 INFO Tres"
    ]]
    grupos = agrupar_por_nivel(logs)
    assert set(grupos.keys()) == {"ERROR", "INFO"}
    assert len(grupos["INFO"]) == 2

