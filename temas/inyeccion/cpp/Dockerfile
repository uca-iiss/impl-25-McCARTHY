FROM gcc:12

# Instala cmake y GoogleTest
RUN apt-get update && \
    apt-get install -y cmake libgtest-dev && \
    cd /usr/src/gtest && cmake . && make && cp lib/*.a /usr/lib && \
    apt-get clean

WORKDIR /app

# Copiamos todos los archivos del proyecto
COPY . .

# Configuramos CMake y compilamos
RUN cmake -S . -B build && cmake --build build
