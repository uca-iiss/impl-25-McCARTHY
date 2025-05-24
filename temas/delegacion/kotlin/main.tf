terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.2"
    }
  }
}

provider "docker" {}

# 1. Red personalizada
resource "docker_network" "jenkins_net_kotlin" {
  name = "jenkins_net_kotlin"
}

# 2. Volúmenes para persistencia y certificados
resource "docker_volume" "jenkins_home_kotlin" {
  name = "jenkins_home_kotlin"
}

resource "docker_volume" "docker_certs_kotlin" {
  name = "docker_certs_kotlin"
}

# 3. Imagen personalizada de Jenkins
resource "docker_image" "jenkins_custom_kotlin" {
  name = "jenkins-custom-kotlin:latest"
  build {
    context    = "${path.module}/."
    dockerfile = "Dockerfile"
  }
}

# 4. Contenedor Jenkins
resource "docker_container" "jenkins_kotlin" {
  name  = "jenkins_kotlin"
  image = docker_image.jenkins_custom_kotlin.name
  restart = "unless-stopped"

  ports {
    internal = 8080
    external = 8080
  }

  ports {
    internal = 50000
    external = 50000
  }

  env = [
    "DOCKER_HOST=tcp://docker_kotlin:2376",
    "DOCKER_CERT_PATH=/certs/client",
    "DOCKER_TLS_VERIFY=1"
  ]

  volumes {
    volume_name    = docker_volume.jenkins_home_kotlin.name
    container_path = "/var/jenkins_home"
  }

  volumes {
    volume_name    = docker_volume.docker_certs_kotlin.name
    container_path = "/certs/client"
    read_only      = true
  }

  networks_advanced {
    name = docker_network.jenkins_net_kotlin.name
  }
}

# 5. Contenedor Docker-in-Docker (DinD)
resource "docker_container" "docker_kotlin" {
  name  = "docker_kotlin"
  image = "docker:dind"
  restart = "unless-stopped"
  privileged = true

  env = [
    "DOCKER_TLS_CERTDIR=/certs"
  ]

  ports {
    internal = 3000
    external = 3000
  }

  ports {
    internal = 5000
    external = 5000
  }

  volumes {
    volume_name    = docker_volume.docker_certs_kotlin.name
    container_path = "/certs/client"
  }

  networks_advanced {
    name = docker_network.jenkins_net_kotlin.name
  }
}
