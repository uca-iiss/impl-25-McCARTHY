FROM jenkins/jenkins:lts

USER root

RUN apt-get update && \
    apt-get install -y docker.io python3 python3-pip && \
    pip install --break-system-packages --upgrade pip


# Cambia al usuario Jenkins
USER jenkins

# Instala plugins necesarios
RUN jenkins-plugin-cli --plugins \
    "workflow-aggregator docker-workflow git junit"
