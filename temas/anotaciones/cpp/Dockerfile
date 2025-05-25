FROM ubuntu:22.04

RUN apt-get update && apt-get install -y \
    build-essential \
    cmake \
    git \
    wget \
    libgtest-dev \
    && rm -rf /var/lib/apt/lists/*

# Construir gtest (no viene compilado en libgtest-dev)
RUN cd /usr/src/gtest && cmake . && make && cp *.a /usr/lib

WORKDIR /app
COPY . /app

RUN mkdir build && cd build && cmake .. && make

CMD ["./build/runTests"]
