# Sistema de Filtrado de Productos en Java

Este proyecto implementa un sistema de filtrado de productos en Java utilizando anotaciones personalizadas

## Estructura del Código

### Anotaciones Personalizadas

#### `@FiltrarPorNombre`
Anotación para marcar campos que deben recibir un filtro por nombre. Permite inyectar una estrategia de filtrado que busca coincidencias en el nombre de los productos.

#### `@FiltrarPorPrecio`
Anotación para marcar campos que deben recibir un filtro por precio. Permite inyectar una estrategia que filtra productos por debajo de un precio máximo.

### Interfaz `Filtro`

Define el contrato que deben implementar todas las estrategias de filtrado:
- Método `filtrar(List<Producto>)`: aplica el criterio de filtrado.
- Método `configurar(Object parametro)`: permite actualizar el parámetro de filtrado (opcional).

### Implementaciones

#### 1. `FiltroNombre`
- **Propiedades**:
  - `textoBusqueda` (criterio de búsqueda en el nombre)
- **Métodos**:
  - `filtrar()`: devuelve productos cuyo nombre contiene el texto buscado (ignora mayúsculas/minúsculas).
  - `configurar()`: actualiza el texto de búsqueda.

#### 2. `FiltroPrecio`
- **Propiedades**:
  - `precioMaximo` (límite máximo permitido)
- **Métodos**:
  - `filtrar()`: devuelve productos cuyo precio es menor o igual al máximo.
  - `configurar()`: actualiza el valor del precio máximo.

### Clase `InyectorFiltros`

Clase auxiliar que inyecta instancias de filtros en los campos anotados de una clase:
- Usa `Reflection` para identificar anotaciones y asignar implementaciones por defecto.
- Soporta inyección de:
  - `FiltroNombre` (valor por defecto: `""`)
  - `FiltroPrecio` (valor por defecto: `1000.0`)

### Clase `Producto`

Representa un producto con nombre, precio y categoría, e incluye filtros inyectables:
- **Campos**:
  - `nombre`, `precio`, `categoria`
  - `filtroNombre`, `filtroPrecio` (inyectados automáticamente)
- **Métodos**:
  - `filtrarPorNombre()`: filtra una lista usando `filtroNombre`
  - `filtrarPorPrecio()`: filtra una lista usando `filtroPrecio`

### Pruebas Unitarias

Las pruebas validan:
1. La correcta inyección de los filtros.
2. El correcto funcionamiento de cada filtro por separado.
3. Casos de uso esperados y excepciones.

Ejemplo de pruebas realizadas:
- Filtro por nombre (coincidencia parcial, sin distinguir mayúsculas).
- Filtro por precio (comparación con distintos valores máximos).
- Comparación de productos (`equals()` y `hashCode()`).
- Manejo de errores cuando los filtros no han sido inyectados.

## Despliegue para pruebas

### 1. Iniciar los contenedores

Desde la carpeta `temas/anotaciones/java`

```bash
docker-compose -f anotaciones.java-RICHIE.yml up -d
```

### 2. Accede a Jenkins
Abre http://localhost:8080 en tu navegador.

### 3. Desbloquea Jenkins
Ejecuta el siguiente comando para obtener la contraseña inicial:

```
docker exec java-jenkins-1 cat /var/jenkins_home/secrets/initialAdminPassword
```

Instalar plugins recomendados

#### 3.1 Configurar Herramientas de Jenkins

Ve a `Administrar Jenkins > Tools`. Una vez alli, añade:

JDK:
- Nombre: JDK17
- Marca  "Install automatically".

Maven:
- Nombre: Maven
- Marca  "Install automatically".

### 4. Crear el Pipeline
En el panel de Jenkins, haz clic en “New Item”.

Introduce un nombre para el proyecto (por ejemplo, Anotaciones-Java).

Selecciona “Pipeline” como tipo de proyecto y haz clic en OK.

En el menú lateral, selecciona Pipeline y luego:

En “Definition”, selecciona Pipeline script from SCM

En “SCM”, elige Git

En “Repository URL”, introduce la URL de tu repositorio

En archivo JenkinsFile debes poner la siguiente ruta:
```bash
temas/anotaciones/java/anotaciones.java-RITCHIE.Jenkinsfile
```

Haz clic en Save

### 5. Eliminar todo (limpieza)
```bash
docker stop java-jenkins-1
docker rm java-jenkins-1
docker volume rm java_jenkins_home
docker rmi java-jenkins
```