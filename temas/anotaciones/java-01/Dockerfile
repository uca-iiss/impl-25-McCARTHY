# Usa una imagen oficial de Maven con JDK 11
FROM maven:3.9.4-eclipse-temurin-11

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto al contenedor
COPY pom.xml .
COPY src ./src

# Ejecuta los tests durante el build
RUN mvn clean test

# Para que se pueda usar luego como base, si lo deseas
CMD ["mvn", "clean", "install"]
