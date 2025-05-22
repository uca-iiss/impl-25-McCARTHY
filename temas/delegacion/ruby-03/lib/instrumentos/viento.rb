require_relative '../instrumento'

# Instrumento de viento que delega de Instrumento
class Viento
  include Instrumento
  def tocar
    soplar
  end
  
  def afinar
    puts "Afinando instrumento de viento: ajustando embocadura y presión de aire"
  end
  
  private
  
  def soplar
    puts "♪ Soplando - produciendo sonido con aire ♪"
  end
end