# Figuras Geométricas en C# :triangular_ruler:

Implementación de figuras geométricas utilizando abstracción mediante interfaces en C#.

## Estructura del Proyecto :file_folder:

### Código Principal
- [`Circulo.cs`](./Circulo.cs) - Implementación de círculo
- [`IFigura.cs`](./IFigura.cs) - Interfaz base
- [`Rectangulo.cs`](./Rectangulo.cs) - Implementación de rectángulo  
- [`Triangulo.cs`](./Triangulo.cs) - Implementación de triángulo

### Pruebas Unitarias
- [`PruebasFiguras.cs`](./PruebasFiguras.cs) - Tests con XUnit

### Configuración
- [`Dockerfile`](./Dockerfile) - Entorno Jenkins + .NET
- [`Jenkinsfile`](./Jenkinsfile) - Pipeline CI/CD
- [`docker-compose.yml`](./docker-compose.yml) - Orquestación

## Requisitos :warning:
- .NET 8.0 SDK
- Docker (opcional)
- Jenkins (opcional)

## Comandos :computer:

```bash
# Restaurar dependencias
dotnet restore

# Compilar proyecto
dotnet build

# Ejecutar pruebas
dotnet test

# Ejecutar con Docker
docker-compose up -d
Pipeline :gear:
groovy
pipeline {
    stages {
        stage('Build') {
            steps {
                sh 'dotnet build'
            }
        }
        stage('Test') {
            steps {
                sh 'dotnet test'
            }
        }
    }
}
Diagrama de Clases :art:
+-------------------+
|     IFigura       |
+-------------------+
| +Nombre: string   |
| +CalcularArea()   |
| +ObtenerDescripcion()|
+-------------------+
        ^
        |
+-------+--------+---------+
|       |        |         |
|  Circulo  Rectangulo  Triangulo |
+-------+--------+---------+