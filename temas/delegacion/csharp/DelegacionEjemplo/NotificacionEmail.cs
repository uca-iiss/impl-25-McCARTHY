using System;

public class NotificacionEmail : INotificacion
{
    public void enviar(string mensaje)
    {
        Console.WriteLine($"[Email] Enviando mensaje: {mensaje}");
    }
}