FROM jenkins/jenkins:lts-jdk11

USER root

# Instalar .NET SDK
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://packages.microsoft.com/config/debian/11/packages-microsoft-prod.deb -O packages-microsoft-prod.deb && \
    dpkg -i packages-microsoft-prod.deb && \
    rm packages-microsoft-prod.deb && \
    apt-get update && \
    apt-get install -y dotnet-sdk-8.0

# Instalar dependencias adicionales
RUN apt-get install -y git

USER jenkins

# Instalar plugins necesarios
RUN jenkins-plugin-cli --plugins "workflow-aggregator git"

COPY abstraccion.csharp-RITCHIE.Jenkinsfile /var/jenkins_home/Jenkinsfile