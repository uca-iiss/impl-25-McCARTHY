FROM jenkins/jenkins:lts

# Cambiar a usuario root para instalar dependencias del sistema
USER root

# Instalar dependencias necesarias para Ruby y compilación de gemas
RUN apt-get update && \
    apt-get install -y \ 
        python3 \
        python3-pip \
        python3-venv && \
    apt-get clean && \
    rm -rf /var/lib/apt/list/*

# Crear entorno virtual
RUN python3 -m venv /opt/venv

# Activar entorno virtual y actualizar pip
RUN /opt/venv/bin/pip install --upgrade pip

# Copiar requirements.txt al contenedor
COPY requirements.txt /tmp/requirements.txt

# Instalar dependencias en el entorno virtual
RUN /opt/venv/bin/pip install --no-cache-dir -r /tmp/requirements.txt

# Añadir el entorno virtual al PATH
ENV PATH="/opt/venv/bin:$PATH"

# Volver al usuario Jenkins
USER jenkins

# Comprobar que todo esté correcto
RUN python3 --version && pip --version