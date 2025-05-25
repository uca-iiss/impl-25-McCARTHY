FROM gradle:8.7-jdk21

WORKDIR /app

COPY . .

RUN ./gradlew build

CMD ["./gradlew", "run"]
