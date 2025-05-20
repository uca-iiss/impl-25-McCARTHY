# Lambdas en Ruby

## Implementación

### `GestorBD.rb`

```ruby
class GestorBD
  attr_reader :archivo

  def initialize(archivo)
    @archivo = archivo
    @tabla = []
    cargar
  end

  def cargar
    return unless File.exist?(@archivo)

    File.readlines(@archivo, chomp: true).each do |linea|
      nombre, edad, ciudad, dinero = linea.split(',').map(&:strip)
      @tabla << {
        nombre: nombre,
        edad: edad.to_i,
        ciudad: ciudad,
        dinero: dinero.to_f
      }
    end
  end

  def guardar
    File.open(@archivo, 'w') do |f|
      @tabla.each do |fila|
        f.puts [fila[:nombre], fila[:edad], fila[:ciudad], fila[:dinero]].join(',')
      end
    end
  end

  def insertar(fila)
    @tabla << fila
    guardar
  end

  def buscar(filtro = nil, transformador = nil)
    resultado = filtro ? @tabla.select(&filtro) : @tabla
    transformador ? resultado.map(&transformador) : resultado
  end

  def actualizar(filtro, transformador)
    modificadas = false
    @tabla.each do |fila|
      if filtro.call(fila)
        transformador.call(fila)
        modificadas = true
      end
    end
    guardar if modificadas
  end

  def eliminar(filtro)
    eliminados = @tabla.select(&filtro)
    eliminados.each do |fila|
      @tabla.delete(fila)
    end
    guardar unless eliminados.empty?
  end
end
```

En este archivo podemos observar el uso de:

1. **bloques (do ... end, {})**

En la clase `GestorBD`, los bloques se emplean ampliamente para realizar operaciones sobre colecciones, como la lectura y escritura de datos. Por ejemplo:
* En el método **cargar**, se utiliza `.each do |linea| ... end` para iterar sobre cada línea del archivo y procesarla.
* En el método **guardar**, se usa `@tabla.each do |fila| ... end` para recorrer las filas y escribirlas en el archivo
* También se usa la forma de bloque con llaves `{}` cuando se aplica .map para transformar valores.

2. **Llamadas a lambdas (.call)**

Métodos como `actualizar` y `buscar` reciben lambdas como argumentos y los ejecutan mediante `.call`, permitiendo lógica personalizada sin modificar la clase.

3. **Paso de bloques como parámetros (&bloque)**

La clase también permite pasar lambdas como bloques de forma implícita gracias al uso del operador &, el cual convierte un *Proc* o *lambda* en un bloque de código que puede ser consumido por métodos como `.select` y `.map`.

Esto se observa, por ejemplo, en el método `buscar`:
```ruby
resultado = filtro ? @tabla.select(&filtro) : @tabla
transformador ? resultado.map(&transformador) : resultado
```

Aquí:
* **filtro** y **transformador** son lambdas pasadas como argumentos.
* El operador & permite que estos lambdas sean utilizados como bloques por los métodos select y map, que son parte de la API de Enumerable de Ruby. Esto permite a métodos como `.select` y `.map` aplicar lógica dinámica.


### `Main.rb`

```ruby
require_relative 'GestorBD'

# Inicialización
bd_path = File.join(__dir__, 'BD.txt')
File.delete(bd_path) if File.exist?(bd_path)
bd = GestorBD.new(bd_path)

puts "Añadimos a más clientes..."
bd.insertar({ nombre: "Ana", edad: 45, ciudad: "Madrid", dinero: 1500.0 })
bd.insertar({ nombre: "Luis", edad: 28, ciudad: "Valencia", dinero: 700.0 })
bd.insertar({ nombre: "Marta", edad: 33, ciudad: "Bilbao", dinero: 1100.0 })
bd.insertar({ nombre: "Carlos", edad: 39, ciudad: "Sevilla", dinero: 800.0 })
puts "✔ Insertados.\n\n"

# buscar
puts "Clientes con más de 1000€:"
resultado = bd.buscar(->(f) { f[:dinero] > 1000 })
puts resultado

# actualizar
puts "\nAumentando 200€ a los clientes de Madrid:"
bd.actualizar(->(f) { f[:ciudad] == "Madrid" }, ->(f) { f[:dinero] += 200 })

# buscar sin filtro (mostrar todos)
puts "\nTodos los clientes actuales:"
bd.buscar.each { |f| puts f }

# eliminar
puts "\nEliminando a Luis:"
bd.eliminar(->(f) { f[:nombre] == "Luis" })

# mostrar a todos después de eliminar
puts "\nTodos los clientes actuales:"
bd.buscar.each { |f| puts f }

# stream
puts "\nTotal de dinero de clientes mayores de 30 años (ordenados por ciudad):"
total_dinero = bd.buscar
  .select { |f| f[:edad] > 30 }
  .sort_by { |f| f[:ciudad] }
  .tap { |clientes|
    puts "\n> Lista ordenada:"
    clientes.each { |f| puts f }
  }
  .map { |f| f[:dinero] }
  .reduce(0.0, :+)

puts "\n➡ Total acumulado: #{total_dinero.round(2)} €"
```

1. **Expresiones lambda**

Se emplean expresiones lambda como `->(f) { f[:dinero] > 1000 }` para definir funciones anónimas de filtrado o transformación de datos, que se pasan directamente a los métodos de `GestorBD`.

2. **Streams** 

Hacia el final de `Main.rb`, se emplea un estilo de programación funcional encadenada al procesar los datos con una secuencia de operaciones tipo stream. Este patrón facilita operaciones como filtrado, ordenación, transformación y agregación de datos:

```ruby
total_dinero = bd.buscar
  .select { |f| f[:edad] > 30 }
  .sort_by { |f| f[:ciudad] }
  .tap { |clientes|
    puts "\n> Lista ordenada:"
    clientes.each { |f| puts f }
  }
  .map { |f| f[:dinero] }
  .reduce(0.0, :+)
```

Este bloque realiza:
- `select`: Filtra los registros según la edad.
- `sort_by`: Ordena por ciudad.
- `tap`: Permite imprimir los valores intermedios sin romper la cadena.
- `map`: Extrae el campo `dinero`.
- `reduce`: Acumula el total de dinero.

Este encadenamiento de métodos (streams) es característico de Ruby cuando se trabaja con colecciones y facilita un estilo claro, legible y declarativo de programación.


### `TestGestorBD.rb`

```ruby
require 'minitest/autorun'
require_relative 'GestorBD'

class TestGestorBD < Minitest::Test
  def setup # Se insertan los mismos datos siempre antes de cada test
    @archivo = "BD_test.txt"
    File.delete(@archivo) if File.exist?(@archivo)

    @bd = GestorBD.new(@archivo)
    @bd.insertar({ nombre: "Ana", edad: 45, ciudad: "Madrid", dinero: 1500.0 })
    @bd.insertar({ nombre: "Luis", edad: 28, ciudad: "Valencia", dinero: 700.0 })
    @bd.insertar({ nombre: "Marta", edad: 33, ciudad: "Bilbao", dinero: 1100.0 })
    @bd.insertar({ nombre: "Carlos", edad: 39, ciudad: "Sevilla", dinero: 800.0 })
  end

  def test_insertar
    assert_equal 4, @bd.buscar.size
  end

  def test_buscar_por_dinero
    ricos = @bd.buscar(->(f) { f[:dinero] > 1000 })
    assert_equal 2, ricos.size
    assert ricos.all? { |f| f[:dinero] > 1000 }
  end

  def test_actualizar_dinero
    @bd.actualizar(->(f) { f[:ciudad] == "Madrid" }, ->(f) { f[:dinero] += 200 })
    madrid = @bd.buscar(->(f) { f[:ciudad] == "Madrid" }).first
    assert_equal 1700.0, madrid[:dinero]
  end

  def test_eliminar
    @bd.eliminar(->(f) { f[:nombre] == "Luis" })
    todos = @bd.buscar
    assert_equal 3, todos.size
    refute todos.any? { |f| f[:nombre] == "Luis" }
  end

  def test_total_dinero_mayores_30
    # Se calcula el total de dinero de las personas mayores de 30 años (sin ordenar)
    total = @bd.buscar
      .select { |f| f[:edad] > 30 }
      .map { |f| f[:dinero] }
      .reduce(0.0, :+)
  
    totalEsperado = 1500.0 + 1100.0 + 800.0 
    assert_in_delta totalEsperado, total, 0.01
  end

  def test_ordenar_por_ciudad
    ordenadas = @bd.buscar
      .sort_by { |f| f[:ciudad] }
      .map { |f| f[:ciudad] }
    ordenEsperado = ["Bilbao", "Madrid", "Sevilla", "Valencia"]
    assert_equal ordenEsperado, ordenadas
  end
end
```

Estas pruebas en Ruby han sido realizadas siguiendo las prácticas estándar de testing unitario con la biblioteca `minitest`,
que es un framework de pruebas incluido por defecto en Ruby.


## Implantación

1. Clona el repositorio si no lo has hecho antes

2. Pon los siguientes comandos estando en el directorio `lambdas/ruby`:
```bash
terraform init
terraform apply
```
3. Accede a `localhost:8080`. En caso de que te pregunte por la constrseña inicial, introducir en la terminal:
```bash
docker exec -it jenkins_ruby cat /var/jenkins_home/secrets/initialAdminPassword
```

4. **Crear el Pipeline**
    1. En el panel de Jenkins, haz clic en “New Item”.
    2. Introduce un nombre para el proyecto.
    3. Selecciona “Pipeline” como tipo de proyecto y haz clic en OK.
    4. En “Definition” selecciona Pipeline script from SCM
    5. En “SCM” elige Git
    6. En “Repository URL” introduce: `https://github.com/uca-iiss/RITCHIE-impl-25`
    7. En rama poner la que corresponda
    8. En Jenkinsfile, poner `temas/lambdas/ruby/Jenkinsfile`
    9. Haz clic en Save

5. **Destruir todo (limpieza)**

Ejecuta:
```bash
terraform destroy
```
Luego, para eliminar todos los archivos generador por **Terraform**:
```bash
rm -rf .terraform terraform.tfstate terraform.tfstate.backup
```