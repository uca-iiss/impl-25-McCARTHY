require_relative 'notificador'

class SMS < Notificador
  def preparar
    puts "[SMS] Verificando línea GSM..."
  end

  def enviar(mensaje)
    puts "[SMS] Mensaje enviado: #{mensaje}"
  end
end
