# test_orquesta.rb
require 'minitest/autorun'
require_relative '../lib/orquesta'  # Ajusta la ruta si es necesario
require_relative '../lib/cuerda'
require_relative '../lib/percusion'
require_relative '../lib/viento'

class OrquestaTest < Minitest::Test
  class Guitarra
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
    @guitarra = Guitarra.new
    @orquesta.add_instrumento(@guitarra)
  end

  def test_agregar_instrumento
    assert_includes @orquesta.to_a, @guitarra
  end

  def test_tocar_instrumento
    @orquesta.tocar
    assert @guitarra.tocada
  end

  def test_afinar_instrumento
    @orquesta.afinar
    assert @guitarra.afinada
    assert @guitarra.tocada
  end

  def test_remover_instrumento
    @orquesta.remove_instrumento(@guitarra)
    refute_includes @orquesta.to_a, @guitarra
  end
end
