require_relative 'lib/usuario'
require_relative 'lib/email'
require_relative 'lib/sms'
require_relative 'lib/push'

usuario = Usuario.new("Lucía")
usuario.agregar(Email.new)
usuario.agregar(SMS.new)
usuario.agregar(Push.new)

usuario.notificar_a_todos("Recordatorio: revisión del sistema a las 17:00.")
