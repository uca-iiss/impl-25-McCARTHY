# test_main.rb
require 'minitest/autorun'

class TestMain < Minitest::Test
  def test_lambda
    l = ->(n) { n * 2 }
    assert_equal 10, l.call(5)
  end

  def test_proc
    p = Proc.new { |n| n * 3 }
    assert_equal 15, p.call(5)
  end

  def test_yield
    result = nil
    def ejecutar
      yield 5
    end
    ejecutar { |n| result = n * 4 }
    assert_equal 20, result
  end
end
