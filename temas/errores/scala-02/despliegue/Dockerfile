FROM jenkins/jenkins:lts

USER root

ENV DEBIAN_FRONTEND=noninteractive

# Instalar Java y herramientas necesarias
RUN apt-get update && apt-get install -y \
    default-jdk \
    curl \
    zip \
    unzip \
    git

# Crear el directorio /app y establecer permisos adecuados para el usuario jenkins
RUN mkdir -p /app && chown jenkins:jenkins /app

USER jenkins

ENV HOME=/var/jenkins_home
ENV SDKMAN_DIR="/var/jenkins_home/.sdkman"
SHELL ["/bin/bash", "-c"]

# *** ORDEN CRÍTICO: Establecer WORKDIR y copiar archivos antes de instalar SDKMAN y compilar ***
WORKDIR /app # Establecer el directorio de trabajo para las siguientes instrucciones COPY

# Copiar los archivos del proyecto Scala al contenedor.
# Estos estarán disponibles en /app cuando el siguiente comando RUN se ejecute.
COPY build.sbt .
COPY project ./project
COPY src ./src

# Instalar SDKMAN, SBT y Scala, y luego compilar y testear en el mismo comando RUN.
# Esto asegura que el entorno de SDKMAN esté activo para los comandos sbt.
# Como ya estamos en /app y los archivos del proyecto ya están copiados,
# podemos ejecutar directamente 'sbt compile' y 'sbt test'.
RUN bash -c ' \
    curl -s "https://get.sdkman.io" | bash; \
    echo "sdkman_auto_answer=true" > "$SDKMAN_DIR/etc/sdkmanrc"; \
    source "$SDKMAN_DIR/bin/sdkman-init.sh"; \
    sdk install sbt; \
    sdk install scala; \
    # Opcional: Verificar instalaciones y existencia de archivos (para depuración)
    ls -la "$SDKMAN_DIR/bin/sdkman-init.sh"; \
    ls -la "$SDKMAN_DIR/candidates/sbt/current"; \
    ls -la "$SDKMAN_DIR/candidates/scala/current"; \
    \
    # Compilar y testear el proyecto en la misma sesión de shell
    sbt compile && sbt test \
'

# Comando para ejecutar la aplicación cuando el contenedor se inicie
CMD bash -c 'source "$SDKMAN_DIR/bin/sdkman-init.sh" && sbt run'