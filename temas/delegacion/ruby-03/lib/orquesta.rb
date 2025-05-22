require_relative 'instrumento'
require_relative 'instrumentos/cuerda'
require_relative 'instrumentos/percusion'
require_relative 'instrumentos/viento'

# Clase que representa una orquesta de instrumentos musicales
# Implementa el patrón Composite para manejar colecciones de instrumentos
class Orquesta
  include Enumerable

  def initialize
    @instrumentos = []
  end

  def add_instrumento(instrumento)
    raise ArgumentError, "El objeto debe implementar el módulo Instrumento" unless instrumento.is_a?(Instrumento)
    @instrumentos << instrumento
    self
  end

  def remove_instrumento(instrumento)
    @instrumentos.delete(instrumento)
    self
  end

  def each(&block)
    @instrumentos.each(&block)
  end

  def size
    @instrumentos.size
  end

  def empty?
    @instrumentos.empty?
  end

  def tocar
    # puts "La orquesta comienza a tocar:"
    @instrumentos.each_with_index do |instrumento, index|
      # puts "  [#{index + 1}] #{instrumento.tipo}:"
      instrumento.tocar
    end
    # puts "Fin de la interpretación\n\n"
  end

  def afinar
    # puts "Afinando la orquesta:"
    @instrumentos.each_with_index do |instrumento, index|
      # puts "  [#{index + 1}] #{instrumento.tipo}:"
      instrumento.afinar
      instrumento.tocar
      # puts
    end
    # puts "Afinación completada\n\n"
  end

  def to_a
    @instrumentos.dup
  end

  def to_s
    "Orquesta con #{size} instrumentos: #{@instrumentos.map(&:tipo).join(', ')}"
  end
end