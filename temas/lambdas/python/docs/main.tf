terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.2"
    }
  }
}

provider "docker" {
  host = "unix:///var/run/docker.sock"
}

resource "docker_network" "jenkins_net_lambdas" {
  name = "jenkins_net_lambdas"
}

resource "docker_volume" "jenkins_home_lambdas" {
  name = "jenkins_home_lambdas"
}

resource "docker_image" "jenkins_dind" {
  name         = "jenkins-lambdas"
  keep_locally = true

  build {
    context    = "${path.module}/."
    dockerfile = "Dockerfile"
  }
}

resource "docker_container" "jenkins" {
  name    = "jenkins-lambdas"
  image   = docker_image.jenkins_dind.name
  restart = "unless-stopped"

  ports {
    internal = 8080
    external = 8081
  }

  ports {
    internal = 50000
    external = 50001
  }

  volumes {
    volume_name    = docker_volume.jenkins_home_lambdas.name
    container_path = "/var/jenkins_home"
  }

  networks_advanced {
    name = docker_network.jenkins_net_lambdas.name
  }

  env = [
    "DOCKER_HOST=unix:///var/run/docker.sock"
  ]

  mounts {
    target = "/var/run/docker.sock"
    source = "/var/run/docker.sock"
    type   = "bind"
  }
}

