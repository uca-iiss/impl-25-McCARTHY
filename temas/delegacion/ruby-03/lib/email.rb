require_relative 'notificador'

class Email < Notificador
  def preparar
    puts "[Email] Estableciendo conexión SMTP..."
  end

  def enviar(mensaje)
    puts "[Email] Enviando mensaje: #{mensaje}"
  end
end
