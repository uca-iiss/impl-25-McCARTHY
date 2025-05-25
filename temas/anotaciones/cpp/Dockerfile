FROM gcc:12

WORKDIR /app

# Copiar todo el contenido (código fuente y Makefile)
COPY . .

# Compilar usando Makefile
RUN make

# Establecer el binario principal (ajusta si tiene otro nombre)
CMD ["./main"]
