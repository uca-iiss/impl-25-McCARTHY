# spec/usuario_spec.rb
require_relative '../lib/usuario'
require_relative '../lib/email'

RSpec.describe Usuario do
  it 'envía notificaciones a través de todos los canales' do
    email = Email.new
    usuario = Usuario.new("Ana")
    usuario.agregar(email)

    expect {
      usuario.notificar_a_todos("Prueba")
    }.to output(/Enviando mensaje/).to_stdout
  end
end

