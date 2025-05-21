FROM jenkins/jenkins:lts

USER root

# Instala Docker CLI y Python con pip
RUN apt-get update && \
    apt-get install -y docker.io python3 python3-pip && \
    pip3 install --upgrade pip

# Cambia al usuario Jenkins
USER jenkins

# Instala plugins necesarios
RUN jenkins-plugin-cli --plugins \
    "workflow-aggregator docker-workflow git junit"
