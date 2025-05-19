terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.2"
    }
  }
}

provider "docker" {}

# Imagen personalizada de Jenkins
resource "docker_image" "jenkins" {
  name         = var.jenkins_image
  keep_locally = true
}

# Volumen para persistencia de Jenkins
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

  # Volumen para datos persistentes de Jenkins
  volumes {
    volume_name    = docker_volume.jenkins_home.name
    container_path = "/var/jenkins_home"
  }

  # Volumen para permitir acceso al Docker del host
  volumes {
    host_path      = "/var/run/docker.sock"
    container_path = "/var/run/docker.sock"
  }
}

