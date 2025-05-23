local NotificationService = {}

function NotificationService:new(notifier)
  assert(notifier and notifier.send, "Debe inyectar un objeto con método 'send'")
  local obj = { notifier = notifier }
  setmetatable(obj, self)
  self.__index = self
  return obj
end

function NotificationService:notify(message)
  return self.notifier:send(message)
end

return NotificationService
