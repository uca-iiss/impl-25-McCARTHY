require_relative 'canales'
require 'forwardable'

class Usuario
  extend Forwardable
  include Enumerable

  def initialize(nombre)
    @nombre = nombre
    @canales = Canales.new
  end

  # Delegamos la iteración y gestión de canales al objeto @canales
  def_delegators :@canales, :agregar, :quitar, :each

  def notificar_a_todos(mensaje)
    each do |canal|
      canal.preparar
      canal.enviar(mensaje)
    end
  end
end

