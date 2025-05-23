local NotificationService = require("src.NotificationService")
local EmailNotifier = require("src.EmailNotifier")
local SMSNotifier = require("src.SMSNotifier")
local SlackNotifier = require("src.SlackNotifier")

describe("NotificationService con inyección", function()

  it("envía notificación por Email", function()
    local service = NotificationService:new(EmailNotifier:new())
    assert.are.equal("[Email] Enviado: Hola", service:notify("Hola"))
  end)

  it("envía notificación por SMS", function()
    local service = NotificationService:new(SMSNotifier:new())
    assert.are.equal("[SMS] Enviado: Hola", service:notify("Hola"))
  end)

  it("envía notificación por Slack", function()
    local service = NotificationService:new(SlackNotifier:new())
    assert.are.equal("[Slack] Enviado: Hola", service:notify("Hola"))
  end)

  it("falla si no se inyecta un objeto válido", function()
    assert.has_error(function()
      NotificationService:new({})
    end, "Debe inyectar un objeto con método 'send'")
  end)

end)
