require_relative 'notificador'

class SMSNotificador < Notificador
  def enviar(mensaje)
    "Enviando SMS: #{mensaje}"
  end
end
