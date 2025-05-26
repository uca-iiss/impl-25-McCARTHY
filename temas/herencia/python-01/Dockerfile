# Usa imagen base oficial de Python
FROM python:3.10-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia e instala dependencias
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copia el código fuente y tests
COPY app/ app/
COPY tests/ tests/

# Añade la carpeta /app al PYTHONPATH para que se encuentre el módulo 'app'
ENV PYTHONPATH=/app

# Ejecuta los tests con pytest
CMD ["pytest", "tests"]
