
# Ejemplo de Delegación con Ruby

Este documento describe:

- Cómo hemos creado un ejemplo de delegación en Ruby.
- Batería de pruebas de Ruby con Minitest.
- Uso del Rakefile como automatización de pruebas de Ruby.
- Cómo se construye una imagen personalizada de Jenkins con Ruby y se crean los contenedores.
- Se ejecutan pruebas automatizadas usando un `Jenkinsfile`.

---

# Análisis de delegación en Ruby

La delegación en programación es un patrón en el cual un objeto pasa la responsabilidad de ejecutar un método a otro objeto.

En Ruby, un objeto puede delegar llamadas a métodos que no posee directamente a otro objeto que contiene internamente. Un objeto no necesita heredar para tener ciertos comportamientos, puede delegarlos.

Aplicandolo al ejemplo que veremos más adelante, la delegación permite que un objeto (en este caso, Coche) pase ciertas llamadas de métodos a otro objeto que tiene como atributo (por ejemplo, @motor o @radio), sin necesidad de volver a implementar la lógica de esos métodos en Coche.

Ruby ofrece dos maneras principales de delegación:

- Usando Forwardable (lo que se hace aquí).

- Usando delegación manual (def metodo; @obj.metodo; end, que no se usa en este caso).

---

# Explicación del código 'Coche.rb' con Delegación

Este archivo define tres clases: `Motor`, `Radio`, y `Coche`. La clase `Coche` usa el patrón de delegación para delegar la responsabilidad de manejar el motor y la radio a las clases `Motor` y `Radio` respectivamente, lo que facilita la reutilización de código y mantiene la separación de responsabilidades.

## Componentes

### `Motor`

```ruby
class Motor
  def initialize
    @encendido = false
  end

  def arrancar
    return "Motor ya estaba arrancado." if @encendido

    @encendido = true
    "Motor arrancado."
  end

  def detener
    return "Motor ya estaba detenido." unless @encendido

    @encendido = false
    "Motor detenido."
  end

  def encendido?
    @encendido
  end
end
```

La clase 'Motor' gestiona el estado del motor del coche. Tiene métodos para arrancar, detener y comprobar si el motor está encendido.

### 'Radio'

```ruby
class Radio
  def initialize
    @encendida = false
  end

  def encender
    return "Radio ya estaba encendida." if @encendida

    @encendida = true
    "Radio encendida."
  end

  def apagar
    return "Radio ya estaba apagada." unless @encendida

    @encendida = false
    "Radio apagada."
  end

  def encendida?
    @encendida
  end
end
```

La clase 'Radio' gestiona el estado de la radio, permitiendo encenderla, apagarla y consultar si está encendida.

### 'Coche'

```ruby
class Coche
  extend Forwardable

  def initialize
    @motor = Motor.new
    @radio = Radio.new
  end

  def_delegators :@motor, :arrancar, :detener
  def_delegator :@radio, :encender, :encender_radio
  def_delegator :@radio, :apagar,   :apagar_radio

  def usar_radio
    return "No se puede usar la radio con el motor apagado." unless @motor.encendido?
    encender_radio
  end

  def estado
    {
      motor: @motor.encendido? ? "encendido" : "apagado",
      radio: @radio.encendida? ? "encendida" : "apagada"
    }
  end
end
```

La clase 'Coche' utiliza el 'patrón de delegación' para delegar las responsabilidades de manejar el motor y la radio a los objetos '@motor' y '@radio' respectivamente. A través de 'Forwardable':

- 'def_delegators :@motor, :arrancar, :detener' delega los métodos 'arrancar' y 'detener' a la instancia '@motor'.
- 'ef_delegator :@radio, :encender, :encender_radio' y 'def_delegator :@radio, :apagar, :apagar_radio' delegan la funcionalidad de encender y apagar la radio a '@radio', pero con nombres diferentes ('encender_radio' y 'apagar_radio').
- El método 'usar_radio' contiene lógica de negocio que compone las funcionalidades de los objetos 'Motor' y 'Radio', verificando si el motor está encendido antes de permitir el uso de la radio.

## Ventajas del patrón de Delegación

1. **Separación de responsabilidades**: Cada clase (`Motor`, `Radio`, `Coche`) tiene una responsabilidad clara, lo que facilita el mantenimiento y la comprensión del código.
2. **Reutilización de código**: Los métodos de `Motor` y `Radio` son reutilizados dentro de `Coche` sin necesidad de duplicar la lógica.
3. **Flexibilidad**: Si se necesita cambiar el comportamiento de cómo se maneja el motor o la radio, solo se debe modificar la clase correspondiente, sin necesidad de tocar la clase `Coche`.
4. **Código limpio y mantenible**: El uso de `Forwardable` y la delegación evita la repetición de código y hace que el código sea más conciso y fácil de mantener.

---

Ahora pasaremos a ver el caso de prueba que hemos empleado para analizar nuestro ejemplo de delegación, así como algunas caracteristicas esenciales de Ruby

## Estructura del archivo

```ruby
require "minitest/autorun"
require_relative "../coche"
```

- Importa Minitest.
- Requiere la clase 'Coche' definida en el archivo 'coche.rb'.

---

## 'setup'

```ruby
def setup
  @coche = Coche.new
end
```

- Se ejecuta antes de cada prueba.
- Crea una nueva instancia de 'Coche', garantizando independencia entre tests.

---

## Casos de prueba

### 1. 'test_motor_arranca_correctamente'

```ruby
resultado = @coche.arrancar
assert_equal "Motor arrancado.", resultado
assert_equal "encendido", @coche.estado[:motor]
```

- Verifica que el método 'arrancar' devuelva el mensaje correcto.
- Confirma que el estado del motor sea "encendido" tras ejecutarse.

---

### 2. 'test_radio_no_funciona_sin_motor'

```ruby
resultado = @coche.usar_radio
assert_equal "No se puede usar la radio con el motor apagado.", resultado
assert_equal "apagada", @coche.estado[:radio]
```

- Comprueba que la radio **no se enciende si el motor está apagado**.
- Refleja la lógica compuesta en el método 'usar_radio'.

---

### 3. 'test_radio_funciona_con_motor'

```ruby
@coche.arrancar
resultado = @coche.usar_radio
assert_equal "Radio encendida.", resultado
assert_equal "encendida", @coche.estado[:radio]
```

- Asegura que la radio **sí se enciende si el motor ha arrancado**.
- Verifica el cambio de estado en la radio.

---

### 4. 'test_detener_motor'

```ruby
@coche.arrancar
resultado = @coche.detener
assert_equal "Motor detenido.", resultado
assert_equal "apagado", @coche.estado[:motor]
```

- Verifica el correcto funcionamiento del método 'detener'.
- Asegura que el estado del motor se actualiza correctamente tras detenerlo.

---

Estas pruebas confirman que:

- La delegación está funcionando correctamente ('arrancar', 'detener', 'usar_radio').
- La lógica condicional compuesta (usar la radio solo con el motor encendido) está implementada correctamente.
- El diseño de 'Coche' como fachada que delega comportamientos a 'Motor' y 'Radio' es efectivo y testable.

---

## Rakefile


require "rake/testtask"

Rake::TestTask.new do |t|
  t.pattern = "test/test_*.rb"  # Ejecuta todos los archivos de test
  t.warning = true              # Muestra advertencias
end

task default: :test

---

El Rakefile se encargará de encontrar todos los test que haya dentro de la carpeta tests y los compilará y ejecutará de manera automatica simplemente con la ejecución del comando 'rake'.

---

Ahora pasaremos con la instalación de los contenedores de Jenkins con Ruby


## Dockerfile: Jenkins con Ruby

```dockerfile
FROM jenkins/jenkins:lts

# Cambiar a usuario root para instalar dependencias del sistema
USER root

# Instalar dependencias necesarias para Ruby y compilación de gemas
RUN apt-get update && \
    apt-get install -y \
        curl \
        gnupg2 \
        build-essential \
        libssl-dev \
        libreadline-dev \
        zlib1g-dev \
        libyaml-dev \
        libffi-dev \
        git \
        libgdbm-dev \
        libncurses5-dev \
        libtool \
        autoconf \
        pkg-config \
        bison \
        libgmp-dev \
        ruby-full && \
    gem install bundler rake && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Comprobar versiones instaladas
RUN ruby -v && gem -v && bundler -v && rake --version

# Volver al usuario Jenkins
USER jenkins
```

- Parte de la imagen oficial 'jenkins/jenkins:lts'.
- Instala Ruby y herramientas de desarrollo necesarias para compilar gemas.
- Añade 'bundler' y 'rake', esenciales para entornos Ruby.
- Vuelve al usuario 'jenkins' para mayor seguridad al ejecutar procesos.

---

## Creación del contenedor Jenkins personalizado

### 1. Construir la imagen

```bash
docker build -t my-jenkins-image .
```

### 2. Crear y ejecutar el contenedor mediante Terraform

Mediante un main.tf el cual incluirá todo lo necesario para poder proceder con la instalación de los contenedores de Jenkins, podremos tener los contenedores funcionales y operativos colocando los comandos de **Terraform Init** y **Terraform Apply**

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

Una vez ejecutado, ahora ya tendremos los contenedores de Jenkins funcionales, ahora procederemos a explicar el JenkinsFile escogido para el lanzamiento de la aplicación mediante un pipeline.


## Jenkinsfile para CI/CD en Ruby

```groovy
pipeline {
    agent any

    stages {
        stage('Preparar entorno') {
            steps {
                echo 'Preparando entorno Ruby...'
                // Si necesitas instalar dependencias, lo puedes agregar aquí
            }
        }

        stage('Ejecutar pruebas') {
            steps {
                echo 'Ejecutando pruebas con Rake...'
                dir('temas/delegacion/ruby-01') {
                    sh 'rake'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado.'
        }
        failure {
            echo 'La ejecución falló.'
        }
        success {
            echo 'La ejecución fue exitosa.'
        }
    }
}
```

'Preparar entorno' Imprime un mensaje. Puedes agregar 'bundle install' aquí si fuera necesario. 
'Ejecutar pruebas' Entra a la ruta del proyecto ('temas/delegacion/ruby-01') y ejecuta 'rake' para lanzar pruebas. 
'post'  Define acciones según el resultado: éxito, fallo o siempre. 

Este setup permite:

- Ejecutar Jenkins en Docker con Ruby preinstalado.
- Correr pruebas automatizadas de Ruby usando `rake`.
- Integrar pruebas a pipelines CI/CD de forma simple y portable.

---

