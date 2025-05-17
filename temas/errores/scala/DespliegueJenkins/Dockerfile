FROM jenkins/jenkins:lts

USER root

ENV DEBIAN_FRONTEND=noninteractive

# Instalar Java y herramientas necesarias
RUN apt-get update && apt-get install -y \
    default-jdk \
    curl \
    zip \
    unzip

# Instalar SDKMAN
RUN curl -s "https://get.sdkman.io" | bash

# Instalar SBT y Scala con SDKMAN
RUN bash -c "source /root/.sdkman/bin/sdkman-init.sh && sdk install sbt && sdk install scala"

# Hacer accesible sbt/scala para el usuario Jenkins
ENV PATH="/root/.sdkman/candidates/sbt/current/bin:/root/.sdkman/candidates/scala/current/bin:$PATH"

RUN java -version && sbt --version && scala -version

USER jenkins