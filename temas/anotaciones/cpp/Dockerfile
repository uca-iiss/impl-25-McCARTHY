FROM gcc:12

WORKDIR /app

COPY . .

RUN chmod +x tests/run_tests.sh

RUN make

CMD ["./main"]
