FROM ruby:3.2-slim

WORKDIR /app

# Instalar dependencias del sistema necesarias para gems con extensiones nativas
RUN apt-get update && apt-get install -y --no-install-recommends \
    build-essential \
    libglib2.0-0 \
    libgl1-mesa-glx \
    && rm -rf /var/lib/apt/lists/*
    

COPY Gemfile Gemfile.lock ./
RUN gem install bundler && bundle install    

# Copiar el código antes de instalar las dependencias para aprovechar cache
COPY . .



# Comando por defecto (ejecuta tests con RSpec)
CMD ["rake"]
