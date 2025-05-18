FROM jenkins/jenkins:lts

USER root

# Instalar dependencias básicas
RUN apt-get update && apt-get install -y \
    lsb-release \
    apt-transport-https \
    curl \
    gnupg \
    ca-certificates \
    software-properties-common \
    wget

# Instalar Docker CLI
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] \
    https://download.docker.com/linux/debian $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list && \
    apt-get update && apt-get install -y docker-ce-cli

# Instalar .NET SDK 6.0
RUN wget https://packages.microsoft.com/config/debian/$(lsb_release -rs)/packages-microsoft-prod.deb -O packages-microsoft-prod.deb && \
    dpkg -i packages-microsoft-prod.deb && \
    rm packages-microsoft-prod.deb && \
    apt-get update && \
    apt-get install -y dotnet-sdk-6.0

USER jenkins

# Instalar plugins de Jenkins
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"
