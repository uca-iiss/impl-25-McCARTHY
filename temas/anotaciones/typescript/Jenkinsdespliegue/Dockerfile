# Desde typescript
# docker build -t my-jenkins-image -f JenkinsDespliegue/Dockerfile .

FROM jenkins/jenkins:lts

# Cambiar a usuario root para instalar dependencias del sistema
USER root

# Instalar dependencias necesarias para Node.js y npm
RUN apt-get update && apt-get install -y curl \
 && curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
 && apt-get install -y nodejs \
 && npm install -g npm@latest \
 && apt-get clean

# Volver al usuario Jenkins
USER jenkins

# Cambiar a la carpeta de trabajo en el contenedor
WORKDIR /home/jenkins/agent/app

# Copiar archivos del proyecto
COPY --chown=jenkins:jenkins ../package*.json ./
COPY --chown=jenkins:jenkins ../tsconfig.json ./
COPY --chown=jenkins:jenkins ../jest.config.js ./
COPY --chown=jenkins:jenkins ../src ./src
COPY --chown=jenkins:jenkins ../tests ./tests
