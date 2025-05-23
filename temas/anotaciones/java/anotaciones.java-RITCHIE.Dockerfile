FROM jenkins/jenkins:lts-jdk17

USER root

# Instalar Maven y JDK 17 (aunque la imagen ya incluye JDK 17)
RUN apt-get update && apt-get install -y maven

RUN apt-get update && apt-get install -y openjdk-17-jdk
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

# Volver al usuario jenkins
USER jenkins

ENV JENKINS_UC_DOWNLOAD=https://updates.jenkins.io
ENV JENKINS_UC=https://updates.jenkins.io

# Instalar plugins necesarios
RUN jenkins-plugin-cli --plugins workflow-aggregator:2.6 github:1.34.1 maven-plugin:3.22