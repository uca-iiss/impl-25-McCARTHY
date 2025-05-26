require_relative 'notificador'

class EmailNotificador < Notificador
  def enviar(mensaje)
    "Enviando correo: #{mensaje}"
  end
end
