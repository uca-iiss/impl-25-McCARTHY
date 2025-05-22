# Clase base abstracta para todos los instrumentos musicales
# Implementa el patrón Template Method definiendo la interfaz común
require_relative './cuerda'
require_relative './percusion'
require_relative './viento'

module Instrumento
  def tocar
    raise NotImplementedError, "El método 'tocar' debe implementarse en las subclases"
  end
  
  def afinar
    raise NotImplementedError, "El método 'afinar' debe implementarse en las subclases"
  end
  
  def tipo
    self.class.name
  end
  
  def to_s
    "#{tipo} (#{object_id})"
  end
end

class Orquesta
  include Enumerable

  def initialize
    @orquesta = []
  end

  def add_instrumento(instrumento)
    @orquesta << instrumento
  end

  def remove_instrumento(instrumento)
    @orquesta.delete(instrumento)
  end

  def each(&block)
    @orquesta.each(&block)
  end

  def tocar
    @orquesta.each { |instrumento| instrumento.tocar }
  end

  def afinar
    @orquesta.each do |instrumento|
      instrumento.afinar
      instrumento.tocar
    end
  end
end