# Uso de la Abstracción en el Programa

## Concepto de Abstracción

La **abstracción** es un principio fundamental de la programación orientada a objetos que nos permite **ocultar los detalles de implementación complejos** y mostrar **solo la funcionalidad esencial** al usuario.

---

## Implementación en el Código

### 1. Interfaz `IFormaGeometrica`

```csharp
public interface IFormaGeometrica
{
    double CalcularArea();
    double CalcularPerimetro();
}
```

- Define como deben implementqarse todas las formas geometricas.
- Establece qué operaciones son posibles (calcular área y perímetro) sin especificar cómo  
- Permite tratar diferentes formas de manera uniforme  

---

### 2. Clases Concretas

```csharp
public class Circulo : IFormaGeometrica { ... }
public class Rectangulo : IFormaGeometrica { ... }
```

- Cada clase implementa los métodos a su manera:
  - **Circulo** usa fórmulas basadas en el radio (`πr²` y `2πr`)
  - **Rectangulo** usa fórmulas basadas en base y altura (`base * altura` y `2 * (base + altura)`)
- Incluyen validaciones específicas para cada forma

---

### 3. Uso Polimórfico

```csharp
IFormaGeometrica[] formas = {
    new Circulo(5),
    new Rectangulo(4, 6)
};

foreach (var forma in formas)
{
    Console.WriteLine($"Área: {forma.CalcularArea():F2}");
    // ...
}
```

- Tratamos todas las formas igual a través de la interfaz  
- El código cliente no necesita saber qué forma concreta está usando  
- Fácil de extender: añadir una nueva forma solo requiere implementar la interfaz  

---

### Beneficios Clave

- **Extensibilidad**: Añadir una nueva forma (ej. Triángulo) no requiere cambios en código existente  
- **Mantenimiento**: Cambios en una forma no afectan a otras  
- **Consistencia**: Todas las formas tienen la misma interfaz  
- **Testabilidad**: Fácil de probar mediante interfaces mock  

---

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

# Explicación del Jenkins Pipeline

## Estructura del Pipeline

```groovy
pipeline {
    agent {
        docker {
            image 'mcr.microsoft.com/dotnet/sdk:9.0'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    // ...
}
```

- Usa contenedor con SDK de .NET 9.0  
- Comparte socket Docker para construir imágenes  

---

## Etapas Principales

### 1. Limpieza del Workspace

```groovy
stage('Clean workspace') {
    steps { cleanWs() }
}
```

- Elimina archivos de ejecuciones anteriores  

---

### 2. Checkout del Código

```groovy
stage('Checkout') {
    steps { checkout scm }
}
```

- Obtiene código fuente del repositorio  

---

### 3. Restauración de Dependencias

```groovy
dir(env.PROJECT_DIR) {
    sh 'dotnet restore'
}
```

- Descarga paquetes NuGet necesarios  

---

### 4. Compilación

```groovy
sh 'dotnet build --no-restore -c Release'
```

- Compila en modo Release  
- `--no-restore` optimiza el proceso  

---

### 5. Pruebas Unitarias

```groovy
sh '''
    mkdir -p ../TestResults
    dotnet test --no-build -c Release --logger "trx;LogFileName=../TestResults/results.trx"
'''
```

- Ejecuta pruebas y genera reporte en formato TRX  
- `--no-build` asume que ya está compilado  

---

### 6. Empaquetado

```groovy
sh 'dotnet publish --no-build -c Release -o ./publish'
archiveArtifacts artifacts: 'publish/**/*', fingerprint: true
```

- Genera artefactos listos para despliegue  
- Archiva resultados para descarga posterior  

---

## Post-Ejecución

```groovy
post {
    always {
        junit '**/TestResults/*.trx'
        echo "Resultado: ${currentBuild.currentResult}"
    }
    failure {
        archiveArtifacts artifacts: '**/*.log'
    }
}
```

- **Siempre**: Publica resultados de pruebas y estado  
- **En fallo**: Archiva logs para diagnóstico  

---

## Flujo Completo

1. Inicia contenedor temporal con .NET SDK  
2. Obtiene código fuente  
3. Restaura dependencias  
4. Compila en modo Release  
5. Ejecuta pruebas unitarias  
6. Genera artefactos de publicación  
7. Reporta resultados  

---

