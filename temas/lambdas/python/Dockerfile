FROM jenkins/jenkins:lts

USER root

# Instalar Docker dentro del contenedor
RUN apt-get update && \
    apt-get install -y docker.io && \
    apt-get clean

# Añadir usuario jenkins al grupo docker
RUN groupadd docker || true && usermod -aG docker jenkins

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el fichero de dependencias Python
COPY requirements.txt .

# Instalar pip y las dependencias (usando el flag para entornos gestionados)
RUN apt-get install -y python3-pip && \
    pip install --no-cache-dir --break-system-packages -r requirements.txt

USER jenkins

