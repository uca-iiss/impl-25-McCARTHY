# Lambda que saluda
saludo_lambda = ->(nombre) { puts "Hola desde lambda, #{nombre}!" }

# Proc que saluda
saludo_proc = Proc.new { |nombre| puts "Hola desde proc, #{nombre}!" }

# Método que usa yield para bloques
def ejecutar_bloque
  puts "Antes del bloque"
  yield
  puts "Después del bloque"
end

# Procesamiento de stream de datos usando lambdas
numeros = [1, 2, 3, 4, 5]
doblar = ->(n) { n * 2 }
resultado = numeros.map(&doblar)
puts "Doblados con lambda: #{resultado}"

# Ejecutar lambda y proc
saludo_lambda.call("Jesús")
saludo_proc.call("David")

# Ejecutar bloque con yield
ejecutar_bloque { puts "Este es el bloque pasado a yield" }
