FROM jenkins/jenkins:lts

USER root

# Instalar Ruby y Docker CLI
RUN apt-get update && apt-get install -y \
    ruby-full build-essential curl \
    docker.io && \
    gem install bundler rake rspec

# Instalar plugins de Jenkins
RUN jenkins-plugin-cli --plugins \
    blueocean \
    docker-workflow \
    token-macro \
    json-path-api


USER jenkins

