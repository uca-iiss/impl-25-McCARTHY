FROM jenkins/jenkins

USER root

# Instalar utilidades base
RUN apt-get update && apt-get install -y \
    lsb-release \
    curl \
    gnupg2 \
    wget \
    software-properties-common \
    apt-transport-https \
    ca-certificates \
    unzip \
    make \
    gcc \
    luarocks \
    lua5.3 \
    lua5.3-dev

# Docker CLI
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc https://download.docker.com/linux/debian/gpg && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
    https://download.docker.com/linux/debian $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list && \
    apt-get update && apt-get install -y docker-ce-cli

# Instalar Lua y Busted para pruebas unitarias
RUN luarocks install busted

USER jenkins

# Instalar plugins de Jenkins
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"
