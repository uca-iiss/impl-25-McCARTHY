# Define la interfaz común
class Notificador
  def preparar
    # Por defecto no hace nada
  end

  def enviar(_mensaje)
    raise NotImplementedError, "Debe implementar #enviar"
  end
end

