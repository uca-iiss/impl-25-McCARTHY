package notificaciones; // Define el paquete al que pertenece esta clase

// Clase concreta que implementa la interfaz Notificador
// Esta clase simula el envío de un mensaje por correo electrónico
public class EmailNotificador implements Notificador {

    // Implementación del método 'enviar' definido en la interfaz Notificador
    // En este caso, simplemente imprime el mensaje por consola con un prefijo
    public void enviar(String mensaje) {
        System.out.println("[EMAIL] " + mensaje); // Simula el envío del mensaje por email
    }
}
