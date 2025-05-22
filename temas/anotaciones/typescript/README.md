# Ejemplo de uso de Anotaciones con typescript

Este documento describe:

- Cómo hemos implementado un ejemplo de anotaciones utilizando Typescript.
- Batería de pruebas y automatización con Jest.
- Cómo se construye una imagen personalizada de Jenkins con typescript y se crean los contenedores.
- Ejecución de pruebas automatizadas usando un `Jenkinsfile`.

---

## Análisis de anotaciones con typescript

En programación, una **Anotación** es una forma de añadir metadatos al código fuente que están disponibles para la aplicación en tiempo de ejecución o de compilación.
En el ejemplo que veremos, hemos implementado varias funciones para la utilización de anotaciones. Utilizaremos anotaciones para:

- Medir tiempo de ejecución.
- Validar distintos parámetros.
- Inicializar valores por defecto.

---

## Explicación del código en typescript

Este proyecto define varias funciones que utilizan anotaciones para verificar el correcto funcionamiento.

---

### 'MinLength'

```typescript
// Decorador de propiedad para validar longitud mínima
function MinLength(length: number) {
  return function (target: any, propertyKey: string) {
    let value: string;

    const getter = () => value;
    const setter = (newValue: string) => {
      if (newValue.length < length) {
        throw new Error(`La longitud mínima para '${propertyKey}' es ${length}`);
      }
      value = newValue;
    };

    Object.defineProperty(target, propertyKey, {
      get: getter,
      set: setter,
      enumerable: true,
      configurable: true,
    });
  };
}

```

Función utilizada para verificar que un nombre tenga un mínimo de longitud de 5 carácteres.

### 'LogExecutionTime'

```typescript
// Decorador de método para medir tiempo de ejecución
function LogExecutionTime(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
  const originalMethod = descriptor.value;

  descriptor.value = function (...args: any[]) {
    const start = Date.now();
    const result = originalMethod.apply(this, args);
    const end = Date.now();
    console.log(`El método ${propertyKey} se ejecutó en ${end - start}ms`);
    return result;
  };

  return descriptor;
}
```

Función que mide el tiempo de ejecución.

### 'NotEmpty'

```typescript
// Decorador para validar que los parámetros no estén vacíos
function NotEmpty(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
  const originalMethod = descriptor.value;

  descriptor.value = function (...args: any[]) {
    for (let i = 0; i < args.length; i++) {
      const paramValue = args[i];
      if (typeof paramValue === "string" && paramValue.trim() === "") {
        throw new Error(`El parámetro en la posición ${i} no puede estar vacío.`);
      }
    }
    return originalMethod.apply(this, args);
  };

  return descriptor;
}
```

Función que verifica que los parámetros no estén vacíos.

### 'Clase Usuario'

```typescript
@DefaultRole("usuario")
export class Usuario {
  @MinLength(5)
  nombre: string;

  role!: string;

  constructor(nombre: string) {
    this.nombre = nombre;
  }

  @LogExecutionTime
  @NotEmpty
  saludar(mensaje: string): string {
    return `Hola, ${this.nombre}! ${mensaje}`;
  }

  tienePermisos(): boolean {
    return this.role === "admin";
  }
}
```

Clase que contiene dos métodos y utiliza los decoradores para llamar a las funciones anteriores de una forma más elegante.

## Ventajas de utilizar anotaciones

1. **Facilita las Pruebas Unitarias:** Puedes aislar y probar la lógica central del método sin preocuparte por las validaciones repetitivas.

2. **Mejora la legibilidad del código:** Las anotaciones permiten agregar lógica de forma declarativa, haciendo que el código sea más fácil de leer y entender

3. **Inyección de Dependencias (DI):** Son la base para utilizar inyección de dependencias.

---

Ahora pasaremos a ver el programa de pruebas implementada en el directorio de [tests](tests) con el uso de Jest.

---

## Casos de prueba

### 1. Comprobación de nombre muy corto

```typescript
it("debería lanzar error si el nombre es demasiado corto", () => {
    expect(() => new Usuario("Ana")).toThrow("La longitud mínima para 'nombre' es 5");
  });
```

- Se crea un nuevo usuario llamado Ana
- Se comprueba que debería lanzar un error de que el nombre es muy corto
---

### 2. Comprobación de que un mensaje está vacío

```typescript
it("debería lanzar error si el mensaje está vacío", () => {
    const usuario = new Usuario("Carlos");
    expect(() => usuario.saludar("")).toThrow("El parámetro en la posición 0 no puede estar vacío.");
  });
```

- Se crea un nuevo usuario llamado Carlos
- Comprobación de que el mensaje está vacío

---
### 3. Comprobación de rol correcto

```typescript
it("debería inicializar con el rol correcto", () => {
    const usuario = new Usuario("Carlos");
    expect(usuario.role).toBe("usuario");
  });

```

- Se crea un nuevo usuario llamado Carlos
- Comprobación de que su rol es 'usuario' (por defecto)

---
### 4. Comprobación de que un usuario con rol 'usuario' no tiene permisos

```typescript
it("debería devolver false para permisos si el rol es usuario", () => {
    const usuario = new Usuario("Carlos");
    expect(usuario.tienePermisos()).toBe(false);
  });
```

- Se crea un nuevo usuario llamado Carlos con rol 'usuario' (por defecto)
- Comprobación de que no tiene permisos (tiene que ser admin)

---

Este programa sirve como un banco de pruebas básico para:

- Confirmar el correcto uso de las anotaciones.
- Verificar comportamientos específicos de nuestro programa.

---

## Jest

Para la realización de pruebas automáticas en typescript, hemos utilizado Jest.
Simplemente necesitamos ejecutar npm test en el directorio typescript para realizar las pruebas.

## Dockerfile: Jenkins con typescript

```dockerfile
# Desde typescript
# docker build -t my-jenkins-image -f JenkinsDespliegue/Dockerfile .

FROM jenkins/jenkins:lts

# Cambiar a usuario root para instalar dependencias del sistema
USER root

# Instalar dependencias necesarias para Node.js y npm
RUN apt-get update && apt-get install -y curl \
 && curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
 && apt-get install -y nodejs \
 && npm install -g npm@latest \
 && apt-get clean

# Volver al usuario Jenkins
USER jenkins

# Cambiar a la carpeta de trabajo en el contenedor
WORKDIR /home/jenkins/agent/app

# Copiar archivos del proyecto
COPY --chown=jenkins:jenkins ../package*.json ./
COPY --chown=jenkins:jenkins ../tsconfig.json ./
COPY --chown=jenkins:jenkins ../jest.config.js ./
COPY --chown=jenkins:jenkins ../src ./src
COPY --chown=jenkins:jenkins ../tests ./tests

```

- Parte de la imagen oficial 'jenkins/jenkins:lts'.
- Instala node y herramientas de desarrollo necesarias.
- Cambiamos la carpeta de trabajo en el contenedor
- Copiamos los archivos del proyecto al contenedor

---

## Creación del contenedor Jenkins personalizado

### 1. Construir la imagen

Desde el directorio typescript:

```bash
docker build -t my-jenkins-image -f JenkinsDespliegue/Dockerfile .
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


## Jenkinsfile para CI/CD en typescript y Jest 

```groovy
pipeline {
    agent any

    stages {
        stage('Preparar entorno') {
            steps {
                echo 'Preparando entorno Typescript...'
                sh 'node -v'
                sh 'npm -v'
            }
        }

        stage('Instalar dependencias') {
            steps {
                echo 'Instalando dependencias...'
                dir('temas/anotaciones/typescript') {
                    sh 'npm install'
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Construyendo...'
                dir('temas/anotaciones/typescript') {
                    sh 'npm run build'
                }
            }
        }

        stage('Ejecutar pruebas') {
            steps {
                echo 'Ejecutando pruebas con Jest...'
                dir('temas/anotaciones/typescript') {
                    sh 'npm test'
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

'Preparar entorno' Se encarga de comprobar que está instalado, mirando las versiones (para depuración). 
'Instalar dependencias' Se encarga de instalar dependencias necesarias, ya que se han guardado en package.json, basta con ejecutar npm install.
'Build' Se utiliza un script definido en package.json donde se compila con tsc, transformando el código a Javascript.
'Ejecutar pruebas' Igual que la anterior se utiliza un script para ejecutar las pruebas. 
'post'  Define acciones según el resultado: éxito o fallo. 

Este setup permite:

- Ejecutar Jenkins en Docker con node y Jest preinstalado.
- Correr pruebas automatizadas de typescript usando `Jest`.
- Integrar pruebas a pipelines CI/CD de forma simple y portable.

---