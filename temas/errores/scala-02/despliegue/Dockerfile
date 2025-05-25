FROM debian:stable-slim

USER root

ENV DEBIAN_FRONTEND=noninteractive

# Instalar herramientas básicas y Java.
RUN apt-get update && apt-get install -y \
    curl \
    git \
    openjdk-17-jdk \
    unzip \
    zip \
    # Limpiar caché para reducir el tamaño de la imagen
    && rm -rf /var/lib/apt/lists/*

# Crear el usuario 'appuser' y su directorio HOME
RUN useradd -ms /bin/bash appuser && \
    mkdir -p /home/appuser/.sdkman && \
    chown -R appuser:appuser /home/appuser

# Establecer el directorio de trabajo para el proyecto
WORKDIR /home/appuser/app

# Copiar los archivos del proyecto Scala al WORKDIR.
# Estos archivos serán propiedad de root al principio.
COPY build.sbt .
COPY project ./project
COPY src ./src

# Asegurar que 'appuser' sea propietario de todo el directorio de trabajo
# y tenga los permisos adecuados.
RUN chown -R appuser:appuser /home/appuser/app && \
    chmod -R u+rwX /home/appuser/app

# Cambiar al usuario 'appuser' para las siguientes instrucciones
USER appuser

# Configurar variables de entorno para SDKMAN
ENV HOME=/home/appuser
ENV SDKMAN_DIR="/home/appuser/.sdkman"
SHELL ["/bin/bash", "-c"]

# Instalar SDKMAN, SBT y Scala.
RUN set -e && \
    # Evitar reinstalar SDKMAN si el script ya existe (útil si hay cachés intermedios)
    if [ ! -f "$SDKMAN_DIR/bin/sdkman-init.sh" ]; then \
        curl -s "https://get.sdkman.io" | bash; \
    fi; \
    echo "sdkman_auto_answer=true" > "$SDKMAN_DIR/etc/sdkmanrc"; \
    source "$SDKMAN_DIR/bin/sdkman-init.sh"; \
    sdk install sbt; \
    sdk install scala; \
    \
    # Verificar versiones dentro de la misma sesión de shell (para el build)
    java -version; \
    sbt --version; \
    scala -version; \
    \
    # Compilar y testear (para el build)
    sbt compile && sbt test

# Añadir los directorios de binarios de SBT y Scala al PATH de forma persistente.
ENV PATH="$SDKMAN_DIR/candidates/sbt/current/bin:$SDKMAN_DIR/candidates/scala/current/bin:$PATH"

# Comando de ENTRYPOINT que inicializa SDKMAN y luego ejecuta la aplicación Scala.
# Se ejecuta como appuser, quien ahora tiene control total sobre su $HOME y $SDKMAN_DIR.
ENTRYPOINT ["/bin/bash", "-c", "source \"$SDKMAN_DIR/bin/sdkman-init.sh\" && sbt run"]