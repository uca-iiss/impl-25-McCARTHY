FROM ubuntu:22.04

# Instalar herramientas necesarias
RUN apt-get update && \
    apt-get install -y cmake g++ git build-essential libgtest-dev && \
    cd /usr/src/gtest && cmake . && make && cp lib/*.a /usr/lib

WORKDIR /app

COPY . .

RUN cmake -S . -B build && \
    cmake --build build

CMD ["./build/main"]
