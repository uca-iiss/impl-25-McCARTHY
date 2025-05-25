# Módulo base para todos los instrumentos musicales
# Implementa el patrón Template Method definiendo la interfaz común
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