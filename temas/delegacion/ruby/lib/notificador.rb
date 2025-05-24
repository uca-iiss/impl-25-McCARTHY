# Interfaz común
class Notificador
  def enviar(mensaje)
    raise NotImplementedError, "Debes implementar este método en una subclase"
  end
end
