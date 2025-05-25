require 'forwardable'

class Canales
  extend Forwardable
  include Enumerable

  def initialize
    @canales = []
  end

  def agregar(canal)
    @canales << canal
  end

  def quitar(canal)
    @canales.delete(canal)
  end

  def each(&block)
    @canales.each(&block)
  end
end
