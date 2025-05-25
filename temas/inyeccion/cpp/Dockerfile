FROM gcc:12

RUN apt-get update && apt-get install -y cmake git libgtest-dev

WORKDIR /app

COPY . .

RUN cmake -S . -B build
RUN cmake --build build
