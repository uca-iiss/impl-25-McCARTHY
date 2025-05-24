terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.2"
    }
  }
}

provider "docker" {}

resource "docker_network" "jenkins_net" {
  name = "jenkins_net_herencia"
}

resource "docker_volume" "jenkins_home" {
  name = "jenkins_home_herencia"
}

resource "docker_volume" "docker_certs" {
  name = "docker_certs_herencia"
}

resource "docker_image" "jenkins_custom" {
  name = "jenkins-custom-herencia:latest"
  build {
    context    = "${path.module}/."
    dockerfile = "Dockerfile"
  }
}

resource "docker_container" "docker" {
  name       = "docker_herencia"
  image      = "docker:dind"
  restart    = "unless-stopped"
  privileged = true

  env = [
    "DOCKER_TLS_CERTDIR=/certs"
  ]

  ports {
    internal = 2376
    external = 2376
  }

  volumes {
    volume_name    = docker_volume.docker_certs.name
    container_path = "/certs"
  }

  networks_advanced {
    name    = docker_network.jenkins_net.name
    aliases = ["docker_herencia"] # ← importante
  }
}

resource "docker_container" "jenkins" {
  name    = "jenkins_herencia"
  image   = docker_image.jenkins_custom.name
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
    "DOCKER_HOST=tcp://docker_herencia:2376",
    "DOCKER_CERT_PATH=/certs/client",
    "DOCKER_TLS_VERIFY=1"
  ]

  volumes {
    volume_name    = docker_volume.jenkins_home.name
    container_path = "/var/jenkins_home"
  }

  volumes {
    volume_name    = docker_volume.docker_certs.name
    container_path = "/certs/client"
    read_only      = true
  }

  networks_advanced {
    name = docker_network.jenkins_net.name
  }
}
