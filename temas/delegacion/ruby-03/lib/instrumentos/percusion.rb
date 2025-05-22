require_relative '../instrumento'

# Instrumento de percusión que hereda de Instrumento
class Percusion < Instrumento
  def tocar
    golpear
  end
  
  def afinar
    puts "Afinando instrumento de percusión: ajustando tensión del parche"
  end
  
  private
  
  def golpear
    puts "♪ Golpeando - sonido por percusión ♪"
  end
end