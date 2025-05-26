# Levantamiento de Jenkins con Terraform

## Arquitectura Definida

Terraform crea:

- Una red Docker aislada (`jenkins_network`)
- Un contenedor Jenkins personalizado
- Un contenedor Docker-in-Docker (DinD) para ejecutar contenedores

---

## Proceso Paso a Paso

### 1. Configuración Inicial

```hcl
terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {}
```

- Especifica el proveedor Docker y su versión

---

### 2. Creación de Red

```hcl
resource "docker_network" "jenkins_network" {
  name = "jenkins_network"
}
```

- Crea red aislada para comunicación segura entre contenedores

---

### 3. Contenedor Jenkins

```hcl
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

  networks_advanced {
    name = docker_network.jenkins_network.name
  }
}
```

- Expone puerto 8080 para acceso web  
- Monta el socket de Docker para controlar el daemon desde Jenkins  
- Usa imagen personalizada con Docker CLI instalado  

---

### 4. Contenedor DinD

```hcl
resource "docker_container" "dind" {
  name       = "dind"
  image      = docker_image.dind.name
  privileged = true
  env = ["DOCKER_TLS_CERTDIR=/certs"]
  networks_advanced {
    name = docker_network.jenkins_network.name
  }
}
```

- Configuración especial para Docker anidado  
- Modo privilegiado necesario para DinD  
- Conectado a la misma red que Jenkins  

---

### Comandos para Ejecutar

```bash
terraform init    # Inicializa proveedores
terraform plan    # Muestra cambios a aplicar
terraform apply   # Crea la infraestructura
```

---

### Acceso a Jenkins

- Abrir navegador en: [http://localhost:8080](http://localhost:8080)  
- Contraseña inicial:  
  ```bash
  docker logs jenkins
  ```

---