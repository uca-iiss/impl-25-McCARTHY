require 'rspec'
require_relative '../lib/usuario'
require_relative '../lib/email_notificador'
require_relative '../lib/sms_notificador'

RSpec.describe Usuario do
  it "delegar notificaciones al notificador de correo" do
    usuario = Usuario.new("Ana", EmailNotificador.new)
    expect(usuario.enviar_notificacion("Hola!")).to eq("Enviando correo: Hola!")
  end

  it "delegar notificaciones al notificador de SMS" do
    usuario = Usuario.new("Luis", SMSNotificador.new)
    expect(usuario.enviar_notificacion("Hola!")).to eq("Enviando SMS: Hola!")
  end
end
