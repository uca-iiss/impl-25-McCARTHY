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

# Crear el directorio /app (como root)
RUN mkdir -p /app

# Establecer el directorio de trabajo para las siguientes instrucciones COPY
WORKDIR /app

# Copiar los archivos del proyecto Scala al contenedor.
# Estos estarán disponibles en /app cuando el siguiente comando RUN se ejecute.
COPY build.sbt .
COPY project ./project
COPY src ./src

# *** CAMBIO CLAVE AQUÍ: Asegurar permisos de escritura recursivos para jenkins como ROOT. ***
# Ahora root cambia la propiedad y los permisos de todo el contenido de /app.
RUN chown -R jenkins:jenkins /app && chmod -R u+rwX /app

# Ahora sí, cambiar al usuario jenkins.
USER jenkins

ENV HOME=/var/jenkins_home
ENV SDKMAN_DIR="/var/jenkins_home/.sdkman"
SHELL ["/bin/bash", "-c"]

# Instalar SDKMAN, SBT y Scala, y luego verificar sus versiones en el MISMO comando RUN.
# Esto es CRÍTICO para asegurar que el entorno de SDKMAN esté activo para los comandos.
RUN bash -c ' \
    curl -s "https://get.sdkman.io" | bash; \
    echo "sdkman_auto_answer=true" > "$SDKMAN_DIR/etc/sdkmanrc"; \
    source "$SDKMAN_DIR/bin/sdkman-init.sh"; \
    sdk install sbt; \
    sdk install scala; \
    \
    # Verificar versiones dentro de la misma sesión de shell
    java -version; \
    sbt --version; \
    scala -version; \
    \
    # Si todo lo anterior funciona, entonces puedes añadir aquí la compilación y pruebas del proyecto:
    sbt compile && sbt test \
'

# Comando para ejecutar la aplicación cuando el contenedor se inicie
# Aseguramos que el entorno SDKMAN esté activo para el 'sbt run' final
CMD bash -c 'source "$SDKMAN_DIR/bin/sdkman-init.sh" && sbt run'