FROM jenkins/jenkins:lts

USER root

ENV DEBIAN_FRONTEND=noninteractive

# Instalar Java y herramientas necesarias
RUN apt-get update && apt-get install -y \
    default-jdk \
    curl \
    zip \
    unzip

USER jenkins

# Define explícitamente HOME y usa SHELL bash
ENV HOME=/var/jenkins_home
SHELL ["/bin/bash", "-c"]

# Instalar SDKMAN, sbt y scala
RUN curl -s "https://get.sdkman.io" | bash && \
    source "$HOME/.sdkman/bin/sdkman-init.sh" && \
    sdk install sbt && \
    sdk install scala

# Agregar sbt y scala al PATH
ENV PATH="$HOME/.sdkman/candidates/sbt/current/bin:$HOME/.sdkman/candidates/scala/current/bin:$PATH"

# Verificar versiones
RUN java -version && sbt --version && scala -version
