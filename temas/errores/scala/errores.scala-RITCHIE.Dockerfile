FROM jenkins/jenkins:lts-jdk11

USER root

# 1. Instalar dependencias del sistema
RUN apt-get update && \
    apt-get install -y \
    apt-transport-https \
    curl \
    gnupg \
    git \
    docker.io  # Para integración con Docker

# 2. Instalar Scala y sbt
RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list && \
    echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | tee /etc/apt/sources.list.d/sbt_old.list && \
    curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add && \
    apt-get update && \
    apt-get install -y scala sbt


# 3. Instalar plugins esenciales de Jenkins
RUN jenkins-plugin-cli --plugins \
    git \
    workflow-aggregator \
    docker-workflow \
    junit \
    blueocean

# 4. Limpieza y permisos
RUN apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    usermod -aG docker jenkins  # Permite ejecutar Docker

USER jenkins
