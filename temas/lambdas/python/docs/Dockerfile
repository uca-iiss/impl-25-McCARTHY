FROM jenkins/jenkins:lts

USER root

# Instala cliente de Docker
RUN apt-get update && \
    apt-get install -y docker.io && \
    apt-get clean

# Añade el usuario jenkins al grupo docker 
RUN usermod -aG docker jenkins

USER jenkins

