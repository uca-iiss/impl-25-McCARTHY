resource "docker_network" "jenkins" {
  name = "jenkins"
}

resource "docker_volume" "jenkins_data" {
  name = "jenkins-data"
}

resource "docker_volume" "docker_certs" {
  name = "jenkins-docker-certs"
}

resource "docker_container" "docker_dind" {
  name     = "jenkins-docker"
  image    = "docker:dind"
  privileged = true
  restart  = "always"

  networks_advanced {
    name    = docker_network.jenkins.name
    aliases = ["docker"]
  }

  env = [
    "DOCKER_TLS_CERTDIR=/certs"
  ]

  volumes {
    volume_name    = docker_volume.docker_certs.name
    container_path = "/certs/client"
  }

  volumes {
    volume_name    = docker_volume.jenkins_data.name
    container_path = "/var/jenkins_home"
  }

  ports {
    internal = 2376
    external = 2376
  }
}

resource "docker_image" "jenkins_image" {
  name         = "myjenkins-python"
  keep_locally = true
}

resource "docker_container" "jenkins" {
  name  = "jenkins-blueocean"
  image = docker_image.jenkins_image.name
  restart = "on-failure"

  networks_advanced {
    name = docker_network.jenkins.name
  }

  env = [
    "DOCKER_HOST=tcp://docker:2376",
    "DOCKER_CERT_PATH=/certs/client",
    "DOCKER_TLS_VERIFY=1"
  ]

  volumes {
    volume_name    = docker_volume.jenkins_data.name
    container_path = "/var/jenkins_home"
  }

  volumes {
    volume_name    = docker_volume.docker_certs.name
    container_path = "/certs/client"
    read_only      = true
  }

  ports {
    internal = 8080
    external = 8082
  }

  ports {
    internal = 50000
    external = 50002
  }
}
