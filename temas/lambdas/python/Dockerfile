FROM python:3.12-slim

WORKDIR /app

# Copiar todo el proyecto antes de instalar
COPY . .

# Instalar las dependencias incluyendo el paquete editable
RUN pip install --no-cache-dir -r requirements.txt

# Ejecutar los tests por defecto
CMD ["pytest"]
