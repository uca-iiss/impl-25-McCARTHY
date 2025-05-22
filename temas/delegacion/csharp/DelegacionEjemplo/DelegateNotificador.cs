public class DelegateNotificador
{
    public delegate void MetodoEnvio(string mensaje);

    private MetodoEnvio _metodo;

    public DelegateNotificador(MetodoEnvio metodo)
    {
        _metodo = metodo;
    }

    public void CambiarMetodo(MetodoEnvio nuevoMetodo)
    {
        _metodo = nuevoMetodo;
    }

    public void Enviar(string mensaje)
    {
        _metodo(mensaje);
    }
}
