FROM gcc:12

# Instalar CMake, GTest y ctest
RUN apt-get update && \
    apt-get install -y cmake libgtest-dev && \
    cd /usr/src/gtest && cmake . && make && cp lib/*.a /usr/lib && \
    apt-get clean

WORKDIR /app

COPY . .

RUN cmake -S . -B build && cmake --build build

CMD ["ctest", "--test-dir", "build"]
