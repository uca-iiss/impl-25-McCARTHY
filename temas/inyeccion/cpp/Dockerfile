FROM gcc:12

# Instalar cmake y gtest
RUN apt-get update && \
    apt-get install -y cmake libgtest-dev && \
    cd /usr/src/gtest && cmake . && make && cp lib/*.a /usr/lib && \
    apt-get clean

WORKDIR /app

COPY . .

# Construcción del proyecto
RUN cmake -S . -B build && cmake --build build
