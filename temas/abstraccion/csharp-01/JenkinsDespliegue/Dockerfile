FROM jenkins/jenkins:lts

# Cambiar a usuario root para instalar dependencias del sistema
USER root

# Instalar dependencias necesarias para Ruby y compilación de gemas
RUN apt-get update && \
    apt-get install -y \ 
        wget \
        build-essential \
        git &&\
    wget https://packages.microsoft.com/config/debian/11/packages-microsoft-prod.deb -O packages-microsoft-prod.deb && \
    dpkg -i packages-microsoft-prod.deb && \
    rm packages-microsoft-prod.deb && \
    apt-get update && \
    apt-get install -y dotnet-sdk-8.0 dotnet-runtime-8.0 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Comprobar versiones instaladas
RUN dotnet --version && make --version && git --version

# Volver al usuario Jenkins
USER jenkins