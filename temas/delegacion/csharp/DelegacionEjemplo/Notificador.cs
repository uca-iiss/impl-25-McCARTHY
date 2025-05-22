public class Notificador
{
    private INotificacion estrategia;

    public Notificador(INotificacion estrategiaInicial)
    {
        estrategia = estrategiaInicial;
    }

    public void CambiarEstrategia(INotificacion nuevaEstrategia)
    {
        estrategia = nuevaEstrategia;
    }

    public void Notificar(string mensaje)
    {
        estrategia.enviar(mensaje);
    }
}
