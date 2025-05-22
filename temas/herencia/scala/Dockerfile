FROM jenkins/jenkins:lts

USER root

# Instalar utilidades base y Java (OpenJDK 11)
RUN apt-get update && apt-get install -y \
    lsb-release \
    curl \
    gnupg2 \
    openjdk-17-jdk \
    apt-transport-https \
    ca-certificates \
    wget \
    software-properties-common

# Instalar Docker CLI
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc https://download.docker.com/linux/debian/gpg && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
    https://download.docker.com/linux/debian $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list && \
    apt-get update && apt-get install -y docker-ce-cli

# Instalar Scala
RUN wget https://downloads.lightbend.com/scala/2.13.12/scala-2.13.12.deb
RUN dpkg -i scala-2.13.12.deb
RUN rm scala-2.13.12.deb

# Instalar sbt (build tool de Scala)
RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list
RUN curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x99E82A75642AC823" | apt-key add
RUN apt-get update && apt-get install -y sbt

USER jenkins

# Instalar plugins de Jenkins
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"