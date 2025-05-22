require_relative '../instrumento'

# Instrumento de cuerda que hereda de Instrumento
class Cuerda
  include Instrumento
  
  def tocar
    rasgar
  end
  
  def afinar
    puts "Afinando instrumento de cuerda: ajustando tensión de las cuerdas"
  end
  
  private
  
  def rasgar
    puts "♪ Rasgando - vibración de cuerdas ♪"
  end
end