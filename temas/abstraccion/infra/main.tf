terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"  
    }
  }
}

provider "docker" {}

resource "docker_image" "jenkins" {
  name         = "jenkins-dotnet:latest"
  keep_locally = true
  build {
    context    = "."
    dockerfile = "Dockerfile"
  }
}

resource "docker_volume" "jenkins_data"{
  name = "jenkins_data"
}

resource "docker_container" "jenkins" {
  image = docker_image.jenkins.image_id
  name  = "jenkins-csharp"
  
  ports {
    internal = 8080
    external = 8080
  }
  
  ports {
    internal = 50000
    external = 50000
  }
  
  volumes {
    volume_name = docker_volume.jenkins_data.name
    container_path = "/var/jenkins_home"
  }
  
  restart = "unless-stopped"
  privileged = true
  
 
  shm_size = 1073741824
  
  env = [
    "JAVA_OPTS=-Djenkins.install.runSetupWizard=false",
    "JENKINS_OPTS=--argumentsRealm.passwd.admin=admin --argumentsRealm.roles.admin=admin"
  ]
  
  healthcheck {
    test         = ["CMD", "curl", "-f", "http://localhost:8080"]
    interval     = "30s"
    timeout      = "10s"
    start_period = "30s"
    retries      = 5
  }
}

output "jenkins_url" {
  value = "http://localhost:8080"
}