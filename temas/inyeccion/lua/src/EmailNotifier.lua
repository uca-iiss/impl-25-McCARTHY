local EmailNotifier = {}

function EmailNotifier:new()
  local obj = {}
  setmetatable(obj, self)
  self.__index = self
  return obj
end

function EmailNotifier:send(message)
  return "[Email] Enviado: " .. message
end

return EmailNotifier
