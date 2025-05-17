FROM jenkins/jenkins:lts

USER root

# Instalar Java, curl y otras utilidades necesarias
RUN apt-get update && apt-get install -y \
    openjdk-11-jdk \
    curl \
    gnupg \
    apt-transport-https \
    ca-certificates \
    git \
    unzip

# Instalar SBT
RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list && \
    curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x99E82A75642AC823" | apt-key add - && \
    apt-get update && \
    apt-get install -y sbt

RUN java -version && sbt sbtVersion

USER jenkins