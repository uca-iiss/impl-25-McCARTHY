local SMSNotifier = {}

function SMSNotifier:new()
  local obj = {}
  setmetatable(obj, self)
  self.__index = self
  return obj
end

function SMSNotifier:send(message)
  return "[SMS] Enviado: " .. message
end

return SMSNotifier
