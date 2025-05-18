terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.2"
    }
  }
}

provider "docker" {}

# Imagen personalizada de Jenkins (puedes cambiarla si ya la construiste tú)
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

  volumes {
    volume_name    = docker_volume.jenkins_home.name
    container_path = "/var/jenkins_home"
  }
}

