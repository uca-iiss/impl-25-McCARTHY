require_relative 'notificador'

class Push < Notificador
  def preparar
    puts "[Push] Activando canal push seguro..."
  end

  def enviar(mensaje)
    puts "[Push] Notificación enviada: #{mensaje}"
  end
end
