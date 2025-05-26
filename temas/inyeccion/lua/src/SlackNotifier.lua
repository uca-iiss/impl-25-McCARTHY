local SlackNotifier = {}

function SlackNotifier:new()
  local obj = {}
  setmetatable(obj, self)
  self.__index = self
  return obj
end

function SlackNotifier:send(message)
  return "[Slack] Enviado: " .. message
end

return SlackNotifier
