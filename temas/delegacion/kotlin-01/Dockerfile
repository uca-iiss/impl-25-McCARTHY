FROM gradle:8.2.1-jdk17 AS builder
COPY . /app
WORKDIR /app
RUN gradle build --no-daemon

FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
