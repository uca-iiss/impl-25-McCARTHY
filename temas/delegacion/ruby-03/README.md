# Tema B: Delegación (Ruby) - Práctica IISS

Este sistema de notificaciones está diseñado para demostrar una implementación avanzada del patrón de delegación en Ruby, aplicando buenas prácticas y aprovechando mecanismos idiomáticos como el módulo Forwardable. Se trata de una arquitectura limpia, extensible y flexible que permite enviar notificaciones a través de múltiples canales (email, SMS, push), delegando el comportamiento concreto sin acoplar directamente a la clase principal.

Una de las claves del diseño es el uso del módulo Forwardable, que permite delegar métodos concretos a objetos internos de una clase sin necesidad de redefinir manualmente cada uno. Esto representa una forma elegante y explícita de aplicar composición y delegación en Ruby.

En este caso:

La clase Usuario mantiene una colección de canales de notificación.

A través de Forwardable, delega llamadas al método preparar directamente a cada uno de los canales agregados, que implementan una interfaz común.

Esto desacopla el comportamiento del envío concreto (SMS, email, push) de la lógica del usuario, lo que respeta el principio de responsabilidad única y facilita la extensión del sistema (por ejemplo, añadiendo un canal nuevo no requiere modificar Usuario).

## Estructura del proyecto

```
temas/
└── delegacion/
    └── ruby-03/
        ├── lib/
        │   ├── canales.rb           ← Clase colección con Enumerable
        │   ├── email.rb             ← Canal Email
        │   ├── sms.rb               ← Canal SMS
        │   ├── push.rb              ← Canal Push
        │   ├── notificador.rb       ← Clase base abstracta
        │   ├── usuario.rb           ← Usuario con delegación
        ├── spec/
        │   └── usuario_spec.rb      ← Pruebas unitarias con RSpec
        ├── Gemfile                  ← Declaración de dependencias
        ├── Rakefile                 ← Tarea default de tests
        ├── Dockerfile               ← Imagen personalizada para entorno Ruby
        ├── Jenkinsfile              ← Pipeline declarativo de Jenkins
        └── docs/
            ├── main.tf              ← Infraestructura Jenkins y DinD con Terraform
            ├── variables.tf
            ├── outputs.tf
            ├── providers.tf
            └── Dockerfile           ← Imagen Jenkins personalizada
```

---

## Objetivo del ejemplo

Sistema de notificaciones basado en delegación en Ruby. La orquesta mantiene una colección iterable de canales, y delega en ellos el comportamiento `preparar` y `enviar`. El diseño está desacoplado, orientado a interfaz, y flexible para admitir nuevas implementaciones.

Cada clase sigue los principios de encapsulamiento, bajo acoplamiento y alta cohesión.

---

## Ejecución del ejemplo

### 1. Construcción de la imagen Docker

Desde `temas/delegacion/ruby-03/docs`:

```bash
docker build -t myjenkins-ruby .
```

---

## Integración continua con Jenkins

Este proyecto se ejecuta automáticamente mediante un **pipeline declarativo de Jenkins** que ejecuta tests con RSpec y genera salidas verificables.

### 2. Despliegue de Jenkins con Terraform

Desde la carpeta `docs`:

```bash
terraform init
terraform apply
```

Esto lanza Jenkins y Docker-in-Docker con acceso compartido al socket de Docker.

### 3. Acceso a Jenkins

Ir a: [http://localhost:8083](http://localhost:8083)

#### Contraseña inicial

```bash
docker exec -it jenkins-blueocean cat /var/jenkins_home/secrets/initialAdminPassword
```

Una vez dentro:
1. Crear un nuevo ítem: `Delegacion-Ruby-Pipeline`
2. Tipo: **Pipeline**
3. En **Pipeline script from SCM**:
   - SCM: Git
   - URL: `https://github.com/uca-iiss/impl-25-McCARTHY`
   - Branch: `*/main`
   - Script Path: `temas/delegacion/ruby-03/Jenkinsfile`

---

## Detalle de los archivos

### `canales.rb` [`lib/canales.rb`](./lib/canales.rb)

Contenedor iterable de canales de notificación. Usa `Enumerable` para exponer solo `each`, protegiendo la estructura interna.

```ruby
class Canales
  include Enumerable

  def initialize
    @canales = []
  end

  def each(&block)
    @canales.each(&block)
  end

  def agregar(canal)
    @canales << canal
  end

  def quitar(canal)
    @canales.delete(canal)
  end
end
```

### `email.rb`, `sms.rb`, `push.rb` [`lib/email.rb`](./lib/email.rb) [`lib/sms.rb`](./lib/sms.rb) [`lib/push.rb`](./lib/push.rb)

Cada clase representa un canal y define el método `preparar` y `enviar`.

```ruby
class Email
  def preparar
    puts "[Email] Estableciendo conexión SMTP..."
  end

  def enviar(mensaje)
    puts "[Email] Enviando mensaje: #{mensaje}"
  end
end
```

(Similares para SMS y Push)

### `notificador.rb` [`lib/notificador.rb`](./lib/notificador.rb)

Define la interfaz común para los canales. Facilita composición.

```ruby
class Notificador
  def preparar
    # Comportamiento por defecto opcional
  end

  def enviar(_mensaje)
    raise NotImplementedError, "Debe implementar #enviar"
  end
end
```

### `usuario.rb` [`lib/usuario.rb`](./lib/usuario.rb)

Delegación completa con `Forwardable`.

```ruby
require_relative 'canales'
require 'forwardable'

class Usuario
  extend Forwardable
  def_delegators :@canales, :agregar, :quitar, :each

  def initialize(nombre)
    @nombre = nombre
    @canales = Canales.new
  end

  def notificar_a_todos(mensaje)
    each do |canal|
      canal.preparar
      canal.enviar(mensaje)
    end
  end
end
```

### `usuario_spec.rb` [`spec/usuario_spec.rb`](./spec/usuario_spec.rb)

Test funcional del sistema completo:

```ruby
require_relative '../lib/usuario'
require_relative '../lib/email'

RSpec.describe Usuario do
  it 'envía notificaciones a través de todos los canales' do
    email = Email.new
    usuario = Usuario.new("Ana")
    usuario.agregar(email)

    expect {
      usuario.notificar_a_todos("Prueba")
    }.to output(/Enviando mensaje/).to_stdout
  end
end
```

---

## Infraestructura del Proyecto

### `Jenkinsfile` [`Jenkinsfile`](./Jenkinsfile)

```groovy
pipeline {
    agent none

    stages {
        stage('Build') {
            agent {
                docker {
                    image 'arevalo8/delegacion-ruby:latest'
                }
            }
            steps {
                dir('temas/delegacion/ruby') {
                    sh 'bundle check || bundle install'
                    sh 'rake'  // Compilar y verificar el proyecto ejecutando las tareas default
                }
            }
        }

        stage('Test') {
            agent {
                docker {
                    image 'arevalo8/delegacion-ruby:latest'
                }
            }
            steps {
                dir('temas/delegacion/ruby') {
                    sh '''
                        mkdir -p test-reports
                        rspec --format documentation --format RspecJunitFormatter --out test-reports/results.xml
                    '''
                }
            }
            post {
                always {
                    junit 'temas/delegacion/ruby/test-reports/results.xml'
                }
            }
        }

        stage('Deliver') {
            agent {
                docker {
                    image 'arevalo8/delegacion-ruby:latest'
                }
            }
            steps {
                dir('temas/delegacion/ruby') {
                    sh '''
                        mkdir -p dist
                        tar -czf dist/delegacion-ruby.tar.gz lib/ Rakefile Gemfile main.rb
                    '''
                }
            }
            post {
                success {
                    archiveArtifacts artifacts: 'temas/delegacion/ruby/dist/delegacion-ruby.tar.gz', fingerprint: true
                }
            }
        }
    }
}
```

---

### `Dockerfile` [`Dockerfile`](./Dockerfile)

```dockerfile
FROM ruby:3.2-slim

WORKDIR /app

RUN apt-get update && apt-get install -y --no-install-recommends \
    build-essential && rm -rf /var/lib/apt/lists/*

COPY Gemfile Gemfile.lock ./
RUN gem install bundler && bundle install

COPY . .

CMD ["rake"]
```

---

### `docs/Dockerfile` [`docs/Dockerfile`](./docs/Dockerfile)

Imagen personalizada de Jenkins con Ruby, Bundler, RSpec y plugins para pipelines.

```dockerfile
FROM jenkins/jenkins:lts

USER root

RUN apt-get update && \
    apt-get install -y ruby ruby-dev build-essential curl docker.io && \
    gem install bundler rspec

RUN jenkins-plugin-cli --plugins blueocean docker-workflow

USER jenkins
```

---

### `main.tf`, `providers.tf`,  [`docs/main.tf`](./docs/main.tf) [`docs/providers.tf`](./docs/providers.tf) 

Configuración estándar para desplegar Jenkins con Docker-in-Docker usando volúmenes, certificados y red personalizada, similar al tema de lambdas.

'''
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
  name         = "myjenkins-ruby"
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
    external = 8083
  }

  ports {
    internal = 50000
    external = 50003
  }
}
'''

'''
terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.2"
    }
  }
}

provider "docker" {}
'''


---

## Archivos importantes

| Archivo                | Descripción                                                  |
|------------------------|--------------------------------------------------------------|
| `canales.rb`           | Colección iterable que encapsula los canales                 |
| `email.rb`, `sms.rb`   | Canales con lógica propia de preparación y envío             |
| `usuario.rb`           | Usuario que delega en sus canales mediante `Forwardable`     |
| `usuario_spec.rb`      | Pruebas unitarias con RSpec                                  |
| `Dockerfile`           | Imagen Docker personalizada con Ruby y Bundler               |
| `Jenkinsfile`          | Pipeline declarativo que ejecuta tests                       |
| `docs/*.tf`            | Infraestructura contenerizada de Jenkins con Terraform       |

---

## Limpieza de imágenes, contenedores, redes y volumenes

```bash
docker rmi myjenkins-ruby
docker network rm jenkins
docker rm -f jenkins-blueocean jenkins-docker
docker volume rm jenkins-data jenkins-docker-certs
rm -f terraform.tfstate terraform.tfstate.backup .terraform.lock.hcl
rm -rf .terraform
terraform destroy -auto-approve
```

---

## Resultado final

El sistema de notificaciones permite a cualquier `Usuario` añadir canales de forma dinámica y delegar tanto la preparación como el envío de mensajes, demostrando el principio de **delegación controlada** y diseño desacoplado en Ruby.
