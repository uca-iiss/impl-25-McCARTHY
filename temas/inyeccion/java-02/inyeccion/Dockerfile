<<<<<<< HEAD
FROM jenkins/jenkins:lts

USER root

# Instalar Maven y limpiar caché
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Verificar instalación
RUN java -version && mvn -version

USER jenkins
=======
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/inyeccion-1.0-SNAPSHOT-jar-with-dependencies.jar ./inyeccion.jar

CMD ["java", "-jar", "inyeccion.jar"]
>>>>>>> ba67c50 (Solucionando errores.)
