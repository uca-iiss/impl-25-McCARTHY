# Figuras Geométricas en C#

Este proyecto implementa un sistema de figuras geométricas en C# que demuestra los principios de `Abstracción`.

## Estructura del Código

### Interfaz `IFigura`
Define el contrato básico que deben cumplir todas las figuras geométricas:
- Propiedad `Nombre` (solo lectura)
- Método `CalcularArea()` para calcular el área
- Método `ObtenerDescripcion()` para obtener una descripción detallada

### Implementaciones

#### 1. `Circulo`
- **Propiedades**:
  - `Radio` (setter privado para encapsulamiento)
- **Métodos**:
  - `CalcularArea()`: Calcula el área usando π*r²
  - `ObtenerDescripcion()`: Devuelve una cadena con nombre, radio y área

#### 2. `Rectangulo`
- **Propiedades**:
  - `Ancho` y `Alto` (setters privados)
- **Métodos**:
  - `CalcularArea()`: Calcula área como ancho*alto
  - `ObtenerDescripcion()`: Devuelve cadena con nombre, dimensiones y área

#### 3. `Triangulo`
- **Propiedades**:
  - `Base` y `Altura` (setters privados)
- **Métodos**:
  - `CalcularArea()`: Calcula área como (base*altura)/2
  - `ObtenerDescripcion()`: Devuelve cadena con nombre, base, altura y área

## Pruebas Unitarias


Las Pruebas verifican:
1. Cálculo correcto del área para cada figura
2. Formato correcto de las descripciones

### Despliegue para pruebas

#### 1. Iniciar los contenedores ####
```bash
    docker-compose -f docker-compose.yml up -d
```
#### 2. Accede a Jenkins

Abre [http://localhost:8080](http://localhost:8080) en tu navegador.

#### 3. Desbloquea Jenkins
Ejecuta el siguiente comando para obtener la contraseña inicial:

```bash
docker exec csharp-jenkins-dotnet-1 cat /var/jenkins_home/secrets initialAdminPassword
```

- Instalar plugins recomendados

- Continuar como administrador

#### 4. Creación del Pipeline

1. En el panel de Jenkins, haz clic en “New Item”.
2. Introduce un nombre para el proyecto (por ejemplo, `Abstraccion-csharp`).
3. Selecciona “Pipeline” como tipo de proyecto y haz clic en OK.
4. En el menú lateral, selecciona **Pipeline** y luego:

    - En “Definition” selecciona **Pipeline script from SCM**
    - En “SCM” elige **Git**
    - En “Repository URL” introduce la URL de tu repositorio
    - Haz clic en **Save**.

---

 #### 4. Eliminar todo (limpieza)
```bash
docker stop csharp-jenkins-dotnet-1
docker rm csharp-jenkins-dotnet-1
docker volume rm csharp-jenkins-home
docker rmi csharp-jenkins-dotnet
```