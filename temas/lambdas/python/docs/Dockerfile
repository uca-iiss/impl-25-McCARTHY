FROM jenkins/jenkins:lts

USER root

# Instala Docker y agrega el usuario jenkins al grupo docker 
RUN apt-get update && \
    apt-get install -y docker.io && \
    groupmod -g 998 docker || true && \
    usermod -aG docker jenkins

USER jenkins

