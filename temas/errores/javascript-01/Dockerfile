FROM jenkins/jenkins

USER root

# Actualiza el sistema e instala herramientas necesarias
RUN apt-get update && apt-get install -y \
    lsb-release \
    curl \
    gnupg \
    build-essential

# Instala Node.js y npm
RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash - && \
    apt-get install -y nodejs

# Agrega el repositorio de Docker
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
  https://download.docker.com/linux/debian/gpg

RUN echo "deb [arch=$(dpkg --print-architecture) \
  signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
  https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list

# Instala Docker CLI
RUN apt-get update && apt-get install -y docker-ce-cli

# Verifica las instalaciones
RUN node --version
RUN npm --version

# Cambia de nuevo al usuario jenkins
USER jenkins

# Instala plugins de Jenkins
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"