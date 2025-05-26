# Usa imagen base oficial de Python
FROM python:3.10-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia e instala dependencias
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copia el código fuente y tests (respetando la estructura de Jenkins)
COPY app/ app/
COPY tests/ tests/

# Asegura que los módulos locales se puedan importar
ENV PYTHONPATH=/app

# Comando por defecto al correr el contenedor → ejecutar los tests
CMD ["pytest", "tests"]
