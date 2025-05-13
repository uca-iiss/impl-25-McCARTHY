require "minitest/autorun"
require_relative "../coche"

class TestCocheSecuencial < Minitest::Test
  def setup
    @coche = Coche.new
  end

  def test_comportamiento_secuencial
    # Primera llamada: debe arrancar normalmente
    assert_equal "Motor arrancado.", @coche.arrancar

    # Segunda llamada: debe avisar que ya estaba arrancado
    assert_equal "Motor ya estaba arrancado.", @coche.arrancar

    assert_equal "encendido", @coche.estado[:motor]

    resultado_radio = @coche.usar_radio
    assert_equal "Radio encendida.", resultado_radio

    @coche.apagar_radio
    assert_equal "apagada", @coche.estado[:radio]

    @coche.usar_radio
    assert_equal "encendida", @coche.estado[:radio]

    # Apagar motor
    assert_equal "Motor detenido.", @coche.detener

    # Apagar por segunda vez
    assert_equal "Motor ya estaba detenido.", @coche.detener

    assert_equal "apagado", @coche.estado[:motor]
    assert_equal "encendida", @coche.estado[:radio] # la radio sigue encendida
  end
end