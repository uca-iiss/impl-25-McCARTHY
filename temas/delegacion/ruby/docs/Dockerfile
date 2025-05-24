FROM jenkins/jenkins:lts

USER root

# Instalar Ruby, bundler y herramientas necesarias
RUN apt-get update && \
    apt-get install -y ruby-full build-essential curl && \
    gem install bundler rake rspec

# Instalar plugins de Jenkins necesarios
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"

USER jenkins
