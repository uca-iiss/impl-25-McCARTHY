FROM jenkins/jenkins:lts-jdk11

USER root

# Instalar Python y dependencias del sistema
RUN apt-get update && \
    apt-get install -y python3 python3-pip python3-venv && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Configurar Python 3 como predeterminado
RUN update-alternatives --install /usr/bin/python python /usr/bin/python3 1 && \
    update-alternatives --install /usr/bin/pip pip /usr/bin/pip3 1

# Instalar dependencias adicionales
RUN apt-get update && apt-get install -y git && apt-get clean

USER jenkins

# Instalar plugins necesarios
RUN jenkins-plugin-cli --plugins "workflow-aggregator git"

# Copiar el Jenkinsfile
COPY aspectos.python-RITCHIE.Jenkinsfile /var/jenkins_home/Jenkinsfile