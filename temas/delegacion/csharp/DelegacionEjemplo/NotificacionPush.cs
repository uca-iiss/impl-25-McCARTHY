using System;

public class NotificacionPush : INotificacion
{
    public void enviar(string mensaje)
    {
        Console.WriteLine($"[Push] Enviando mensaje: {mensaje}");
    }
}
