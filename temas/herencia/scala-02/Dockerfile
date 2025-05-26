# Imagen moderna y oficial de sbt + Scala + JDK 11
FROM sbtscala/scala-sbt:eclipse-temurin-17.0.4_1.7.1_2.13.9

# Crear directorio de trabajo
WORKDIR /app

# Copiar los archivos del proyecto
COPY . .

# Comando por defecto
CMD ["sbt", "compile"]



