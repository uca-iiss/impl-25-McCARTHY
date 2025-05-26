FROM jenkins/jenkins:lts

USER root

# Instalar dependencias básicas
RUN apt-get update && apt-get install -y \
    lsb-release \
    curl \
    gnupg \
    ca-certificates \
    sudo \
    build-essential

# Instalar Docker CLI
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc https://download.docker.com/linux/debian/gpg && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
    https://download.docker.com/linux/debian $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list && \
    apt-get update && apt-get install -y docker-ce-cli

# Instalación de Node.js (usamos NodeSource LTS)
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs

# Instalar TypeScript y ts-node globalmente
RUN npm install -g typescript ts-node reflect-metadata

USER jenkins

# Instalar plugins de Jenkins
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"
