FROM jenkins/jenkins:lts

# Cambiar a usuario root para instalar dependencias del sistema
USER root

# Instalar dependencias necesarias para Ruby y compilación de gemas
RUN apt-get update && apt-get install -y curl \
 && curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
 && apt-get install -y nodejs \
 && npm install -g npm@latest \
 && apt-get clean

# Volver al usuario Jenkins
USER jenkins

WORKDIR /home/jenkins/agent/app

COPY --chown=jenkins:jenkins package*.json ./
COPY --chown=jenkins:jenkins tsconfig.json ./
COPY --chown=jenkins:jenkins jest.config.js ./
COPY --chown=jenkins:jenkins src ./src
COPY --chown=jenkins:jenkins tests ./tests

# Esto se hará en Jenkins, pero para depuración

RUN npm install

RUN npx tsc

CMD ["npm", "run", "test"]
