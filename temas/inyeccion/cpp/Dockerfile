FROM gcc:12

# Instalar herramientas necesarias
RUN apt-get update && \
    apt-get install -y cmake libgtest-dev && \
    cd /usr/src/gtest && cmake . && make && cp lib/*.a /usr/lib && \
    apt-get clean

# Directorio de trabajo
WORKDIR /app

# Copiar el código
COPY . .

# Compilar el proyecto
RUN cmake -S . -B build && cmake --build build

# Ejecutar los tests al lanzar el contenedor
CMD ["ctest", "--test-dir", "build", "--output-on-failure"]
