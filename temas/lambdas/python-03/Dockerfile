FROM python:3.12-slim

WORKDIR /app

# Instalar dependencias del sistema para que pyinstaller funcione
RUN apt-get update && apt-get install -y --no-install-recommends \
    gcc \
    build-essential \
    libglib2.0-0 \
    libgl1-mesa-glx \
    libpython3-dev \
    && rm -rf /var/lib/apt/lists/*

# Copiar el código antes de instalar dependencias
COPY . .

# Instalar dependencias de Python, incluyendo pyinstaller y el paquete local
RUN pip install --no-cache-dir -r requirements.txt 

# Comando por defecto, útil para debug/testing
CMD ["pytest"]

