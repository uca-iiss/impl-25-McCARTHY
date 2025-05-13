# coche.rb

require 'forwardable'

class Motor
  def initialize
    @encendido = false
  end

  def arrancar
    return "Motor ya estaba arrancado." if @encendido

    @encendido = true
    "Motor arrancado."
  end

  def detener
    return "Motor ya estaba detenido." unless @encendido

    @encendido = false
    "Motor detenido."
  end

  def encendido?
    @encendido
  end
end

class Radio
  def initialize
    @encendida = false
  end

  def encender
    return "Radio ya estaba encendida." if @encendida

    @encendida = true
    "Radio encendida."
  end

  def apagar
    return "Radio ya estaba apagada." unless @encendida

    @encendida = false
    "Radio apagada."
  end

  def encendida?
    @encendida
  end
end

class Coche
  extend Forwardable

  def initialize
    @motor = Motor.new
    @radio = Radio.new
  end

  # Delegación directa con mismos nombres
  def_delegators :@motor, :arrancar, :detener

  # Delegación con alias
  def_delegator :@radio, :encender, :encender_radio
  def_delegator :@radio, :apagar,   :apagar_radio

  # Comportamiento compuesto
  def usar_radio
    return "No se puede usar la radio con el motor apagado." unless @motor.encendido?
    encender_radio
  end

  def estado
    {
      motor: @motor.encendido? ? "encendido" : "apagado",
      radio: @radio.encendida? ? "encendida" : "apagada"
    }
  end
end