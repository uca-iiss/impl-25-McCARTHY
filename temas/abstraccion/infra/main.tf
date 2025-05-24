terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {}

# Red personalizada para conectar los contenedores
resource "docker_network" "jenkins_network" {
  name = "jenkins_network"
}

# Imagen de Jenkins con Docker instalado
resource "docker_image" "custom_jenkins" {
  name         = "custom-jenkins"
  keep_locally = true
}

# Contenedor Jenkins
resource "docker_container" "jenkins" {
  name  = "jenkins"
  image = docker_image.custom_jenkins.name
  user  = "root"

  ports {
    internal = 8080
    external = 8080
  }

  volumes {
    host_path      = "/var/run/docker.sock"
    container_path = "/var/run/docker.sock"
  }

  volumes {
    volume_name       = "jenkins_volume"
    container_path    = "/var/jenkins_home"
  }

  networks_advanced {
    name = docker_network.jenkins_network.name
  }
}

# Imagen Docker-in-Docker
resource "docker_image" "dind" {
  name         = "docker:dind"
  keep_locally = false
}

# Contenedor DinD configurado correctamente
resource "docker_container" "dind" {
  name       = "dind"
  image      = docker_image.dind.name
  privileged = true
  env = [
    "DOCKER_TLS_CERTDIR=/certs"
  ]

  volumes {
    volume_name = "dind_volume"
    container_path = "/var/lib/docker"
  }

  networks_advanced {
    name = docker_network.jenkins_network.name
  }
}