require 'forwardable'

class Usuario
  extend Forwardable

  def initialize(nombre, notificador)
    @nombre = nombre
    @notificador = notificador
  end

  def_delegator :@notificador, :enviar, :enviar_notificacion
end
