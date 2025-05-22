using System;

public class NotificacionSMS : INotificacion
{
    public void enviar(string mensaje)
    {
        Console.WriteLine($"[SMS] Enviando mensaje: {mensaje}");
    }
}
