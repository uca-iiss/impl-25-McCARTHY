FROM gcc:12

WORKDIR /app

COPY . .

RUN make
RUN make test

CMD ["./main"]
