terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.2"
    }
  }
}

provider "docker" {}

# Imagen Jenkins personalizada
resource "docker_image" "jenkins" {
  name         = "jenkins-herencia:custom"
  keep_locally = true

  build {
    context    = "${path.module}"
    dockerfile = "Dockerfile"
  }
}

# Volumen para persistencia
resource "docker_volume" "jenkins_home" {
  name = "jenkins_home"
}

# Contenedor de Jenkins
resource "docker_container" "jenkins" {
  name  = "jenkins-herencia"
  image = docker_image.jenkins.image_id

  restart = "unless-stopped"

  ports {
    internal = 8080
    external = 8080
  }

  ports {
    internal = 50000
    external = 50000
  }

  volumes {
    volume_name    = docker_volume.jenkins_home.name
    container_path = "/var/jenkins_home"
  }

  # Para permitir que Jenkins use el Docker del host
  volumes {
    host_path      = "/var/run/docker.sock"
    container_path = "/var/run/docker.sock"
    read_only      = false
  }
}

