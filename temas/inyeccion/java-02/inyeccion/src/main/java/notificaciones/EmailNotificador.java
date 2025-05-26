package notificaciones;

public class EmailNotificador implements Notificador {
    public void enviar(String mensaje) {
        System.out.println("[EMAIL] " + mensaje);
    }
}
