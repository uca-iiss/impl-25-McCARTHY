# Usamos imagen oficial de Maven con JDK 17 (o la versión que uses)
FROM maven:3.9.4-eclipse-temurin-17

# Establecemos directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos pom.xml y descargamos dependencias para cachear layers
COPY pom.xml .

RUN mvn dependency:go-offline

# Copiamos todo el código fuente
COPY src ./src

# Compilamos y testeamos el proyecto
RUN mvn clean verify

# Comando por defecto para ejecutar la app (ajusta si es necesario)
CMD ["mvn", "exec:java", "-Dexec.mainClass=com.example.App"]
