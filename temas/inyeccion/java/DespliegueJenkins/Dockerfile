FROM jenkins/jenkins:lts

USER root

# Instalar Maven y limpiar caché
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Verificar instalación
RUN java -version && mvn -version

USER jenkins