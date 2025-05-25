FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

CMD ["java", "-jar", "target/inyeccion-1.0-SNAPSHOT.jar"]
