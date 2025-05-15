# Imagen base con JDK 11, Scala 2.13.12 y sbt 1.9.6
FROM hseeberger/scala-sbt:11.0.17_1.9.6_2.13.12

# Directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto
COPY build.sbt .
COPY project ./project
COPY src ./src

# Comando por defecto (puede ser sobreescrito por Jenkins)
CMD ["sbt", "compile"]
