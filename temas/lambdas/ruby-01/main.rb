# Lambda
saludo_lambda = ->(nombre) { puts "Hola desde lambda, #{nombre}!" }

# Proc
saludo_proc = Proc.new { |nombre| puts "Hola desde proc, #{nombre}!" }

# Método que usa yield
def ejecutar_bloque
  puts "Antes del bloque"
  yield
  puts "Después del bloque"
end

# Ejecutar lambda
saludo_lambda.call("Jesús")

# Ejecutar proc
saludo_proc.call("David")

# Ejecutar bloque con yield
ejecutar_bloque { puts "Este es el bloque pasado a yield" }
