# test/test_coche.rb

require "minitest/autorun"
require_relative "../coche"

class TestCoche < Minitest::Test
  def setup
    @coche = Coche.new
  end

  def test_motor_arranca_correctamente
    resultado = @coche.arrancar
    assert_equal "Motor arrancado.", resultado
    assert_equal "encendido", @coche.estado[:motor]
  end

  def test_radio_no_funciona_sin_motor
    resultado = @coche.usar_radio
    assert_equal "No se puede usar la radio con el motor apagado.", resultado
    assert_equal "apagada", @coche.estado[:radio]
  end

  def test_radio_funciona_con_motor
    @coche.arrancar
    resultado = @coche.usar_radio
    assert_equal "Radio encendida.", resultado
    assert_equal "encendida", @coche.estado[:radio]
  end

  def test_detener_motor
    @coche.arrancar
    resultado = @coche.detener
    assert_equal "Motor detenido.", resultado
    assert_equal "apagado", @coche.estado[:motor]
  end
end