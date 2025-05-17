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