terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {
  host = "npipe:////./pipe/docker_engine"
}

# Crear una red Docker para Jenkins y DinD
resource "docker_network" "ci_network" {
  name = "ci_network"
}

# Imagen Docker-in-Docker
resource "docker_image" "dind" {
  name = "docker:dind"
}

# Contenedor Docker-in-Docker
resource "docker_container" "dind" {
  image = docker_image.dind.image_id
  name  = "dind"
  privileged = true
  restart    = "always"

  env = [
    "DOCKER_TLS_CERTDIR="
  ]

  networks_advanced {
    name = docker_network.ci_network.name
  }

  ports {
    internal = 2375
    external = 2375
  }
}

# Contenedor Jenkins usando la imagen personalizada creada previamente
resource "docker_container" "jenkins" {
  image   = "my-jenkins-image"  # Usamos la imagen creada manualmente
  name    = "jenkins"
  restart = "always"

  ports {
    internal = 8080
    external = 8080
  }

  networks_advanced {
    name = docker_network.ci_network.name
  }

  # Esto permite que Jenkins use Docker del host
  volumes {
    host_path      = "/var/run/docker.sock"
    container_path = "/var/run/docker.sock"
  }
}