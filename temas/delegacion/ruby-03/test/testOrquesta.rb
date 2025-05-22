require 'minitest/autorun'
require_relative '../lib/orquesta'

class OrquestaTest < Minitest::Test
  # Clase de prueba que implementa el módulo Instrumento
  class GuitarraPrueba
    include Instrumento

    attr_reader :afinada, :tocada

    def initialize
      @afinada = false
      @tocada = false
    end

    def afinar
      @afinada = true
    end

    def tocar
      @tocada = true
    end
  end

  def setup
    @orquesta = Orquesta.new
    @guitarra = GuitarraPrueba.new
  end

  def test_orquesta_inicialmente_vacia
    assert @orquesta.empty?
    assert_equal 0, @orquesta.size
  end

  def test_agregar_instrumento
    @orquesta.add_instrumento(@guitarra)
    assert_includes @orquesta.to_a, @guitarra
    assert_equal 1, @orquesta.size
    refute @orquesta.empty?
  end

  def test_agregar_objeto_invalido
    assert_raises(ArgumentError) do
      @orquesta.add_instrumento("no es un instrumento")
    end
  end

  def test_tocar_orquesta
    @orquesta.add_instrumento(@guitarra)
    @orquesta.tocar
    assert @guitarra.tocada
  end

  def test_afinar_orquesta
    @orquesta.add_instrumento(@guitarra)
    @orquesta.afinar
    assert @guitarra.afinada
    assert @guitarra.tocada
  end

  def test_remover_instrumento
    @orquesta.add_instrumento(@guitarra)
    @orquesta.remove_instrumento(@guitarra)
    refute_includes @orquesta.to_a, @guitarra
    assert @orquesta.empty?
  end

  def test_iteracion_con_each
    guitarra2 = GuitarraPrueba.new
    @orquesta.add_instrumento(@guitarra)
    @orquesta.add_instrumento(guitarra2)
    
    instrumentos_iterados = []
    @orquesta.each { |instrumento| instrumentos_iterados << instrumento }
    
    assert_equal 2, instrumentos_iterados.size
    assert_includes instrumentos_iterados, @guitarra
    assert_includes instrumentos_iterados, guitarra2
  end

  def test_metodo_encadenado
    resultado = @orquesta.add_instrumento(@guitarra)
    assert_same @orquesta, resultado
  end
end