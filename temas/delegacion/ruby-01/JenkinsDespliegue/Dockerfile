FROM jenkins/jenkins:lts

# Cambiar a usuario root para instalar dependencias del sistema
USER root

# Instalar dependencias necesarias para Ruby y compilación de gemas
RUN apt-get update && \
    apt-get install -y \
        curl \
        gnupg2 \
        build-essential \
        libssl-dev \
        libreadline-dev \
        zlib1g-dev \
        libyaml-dev \
        libffi-dev \
        git \
        libgdbm-dev \
        libncurses5-dev \
        libtool \
        autoconf \
        pkg-config \
        bison \
        libgmp-dev \
        ruby-full && \
    gem install bundler rake && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Comprobar versiones instaladas
RUN ruby -v && gem -v && bundler -v && rake --version

# Volver al usuario Jenkins
USER jenkins