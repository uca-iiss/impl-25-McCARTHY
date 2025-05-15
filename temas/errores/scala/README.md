# Procesamiento de Pedidos en Scala

Este proyecto implementa un sistema de procesamiento de pedidos en Scala, con un enfoque funcional y manejo explícito de errores mediante `Option`, `Either` y `Try`.

## Descripción

El sistema permite:
- Calcular el total de un pedido de forma segura (`Option`)
- Procesar un flujo de pedidos, manteniendo los errores sin interrumpir el resto (`Either`)
- Parsear pedidos desde datos crudos con captura de excepciones (`Try`)

### Principales conceptos implementados

#### 1. Modelo de datos
- `ItemPedido`: Representa un ítem del pedido (id, nombre, precio, cantidad).
- `Pedido`: Representa un pedido con su id, cliente, lista de ítems y dirección opcional.

#### 2. Lógica de procesamiento
- `calcularTotal(pedido: Pedido): Option[Double]`: 
  - Devuelve el total del pedido si los ítems son válidos.
  - Usa `foldLeft` y `for-comprehension` para acumular valores evitando `null`.
- `procesarStreamPedidos(pedidos: LazyList[Pedido]): LazyList[Either[String, (String, Double)]]`: 
  - Procesa una secuencia de pedidos y reporta errores sin detener el stream.
- `parsearPedido(datos: Map[String, Any]): Try[Pedido]`: 
  - Parsea los datos crudos de entrada capturando errores con `Try`.

  
### Despliegue para pruebas

#### 1. Iniciar los contenedores ####

Desde la carpeta temas/errores/scala

```bash
    docker-compose -f errores.scala-RITCHIE.yml up -d
```
#### 2. Accede a Jenkins

Abre [http://localhost:8080](http://localhost:8080) en tu navegador.

#### 3. Desbloquea Jenkins
Ejecuta el siguiente comando para obtener la contraseña inicial:

```bash
docker exec scala-jenkins-1 cat /var/jenkins_home/secrets initialAdminPassword
```

- Instalar plugins recomendados

- Continuar como administrador

#### 4. Creación del Pipeline

1. En el panel de Jenkins, haz clic en “New Item”.
2. Introduce un nombre para el proyecto (por ejemplo, `Errores-scala`).
3. Selecciona “Pipeline” como tipo de proyecto y haz clic en OK.
4. En el menú lateral, selecciona **Pipeline** y luego:

    - En “Definition” selecciona **Pipeline script from SCM**
    - En “SCM” elige **Git**
    - En “Repository URL” introduce la URL de tu repositorio
    - En Jenkinsfile, poner `temas/errores/scala/errores.scala-RITCHIE.Jenkinsfile`
    - Haz clic en **Save**.

---

 #### 4. Eliminar todo (limpieza)
```bash
docker stop scala-jenkins-1
docker rm cshscala-jenkins-1
docker volume rm scala_jenkins_data
docker rmi scala-jenkin
```
