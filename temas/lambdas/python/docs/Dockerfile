FROM jenkins/jenkins:lts

USER root

#Crear alias
RUN ln -s /usr/bin/python3 /usr/bin/python

# Instalar Python, pip y herramientas necesarias
RUN apt-get update && \
    apt-get install -y python3 python3-pip docker.io curl && \
    pip3 install --no-cache-dir pytest pyinstaller --break-system-packages

# Instalar plugins
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"

USER jenkins
