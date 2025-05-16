# Aspectos en Python: Calculadora Aumentada

Este proyecto implementa una calculadora básica en Python a la que se le aplican **aspectos** utilizando la librería `aspectlib`.

Se utilizan aspectos para:
- Medir el tiempo de ejecución de cada método
- Registrar eventos antes y después de cada operación
- Manejar errores como divisiones por cero

## Estructura del Código

### 1. Clase Calculadora
Implementa operaciones matemáticas básicas:
- `suma(a, b)`
- `resta(a, b)`
- `multiplicacion(a, b)`
- `division(a, b)` — Lanza una excepción si `b == 0`

### 2. AspectosCalculadora
Contiene aspectos reutilizables definidos con `@aspectlib.Aspect`:
- `log_antes_despues`: Muestra logs antes y después de cada llamada
- `manejo_errores`: Captura y registra excepciones
- `tiempo_ejecucion`: Mide el tiempo que tarda cada operación

### 3. aplicar_aspectos()
Función que aplica todos los aspectos a los métodos de `Calculadora` de forma automática.

### 4. demo_aspectos()
Función de demostración interactiva que ejecuta operaciones con la calculadora y muestra cómo actúan los aspectos.

## Pruebas Unitarias

Las pruebas verifican:
1. Correcto funcionamiento de las operaciones (`suma`, `resta`, `multiplicacion`, `division`)
2. Que al dividir por cero se lanza un `ValueError`

Las pruebas **no dependen de los aspectos**, por lo que se ejecutan directamente sobre la clase `Calculadora`.

### Despliegue de Pruebas

#### 1. Iniciar los contenedores ####
```bash
    docker-compose -f aspectos.python-RITCHIE.yml up -d
```
#### 2. Accede a Jenkins

Abre [http://localhost:8080](http://localhost:8080) en tu navegador.

#### 3. Desbloquea Jenkins
Ejecuta el siguiente comando para obtener la contraseña inicial:

```bash
docker exec python-jenkins-python-1 cat /var/jenkins_home/secrets/initialAdminPassword
```

- Instalar plugins recomendados

- Continuar como administrador

#### 4. Creación del Pipeline

1. En el panel de Jenkins, haz clic en “New Item”.
2. Introduce un nombre para el proyecto (por ejemplo, `Aspectos-python`).
3. Selecciona “Pipeline” como tipo de proyecto y haz clic en OK.
4. En el menú lateral, selecciona **Pipeline** y luego:

    - En “Definition” selecciona **Pipeline script from SCM**
    - En “SCM” elige **Git**
    - En “Repository URL” introduce la URL de tu repositorio
    - En Jenkinsfile, poner `temas/aspectos/python/aspectos.python-RITCHIE.Jenkinsfile`
    - Haz clic en **Save**.

---

 #### 4. Eliminar todo (limpieza)
```bash
docker stop python-jenkins-python-1
docker rm python-jenkins-python-1
docker volume rm python_jenkins_home
docker rmi python-jenkins-python
