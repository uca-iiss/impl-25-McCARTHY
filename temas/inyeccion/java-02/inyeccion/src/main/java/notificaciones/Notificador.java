package notificaciones; // Define el paquete al que pertenece esta interfaz

// Interfaz que define el contrato para cualquier clase que quiera actuar como notificador
public interface Notificador {
    
    // Método que debe implementar cualquier clase que envíe notificaciones
    // Recibe como parámetro un mensaje de tipo String
    void enviar(String mensaje);
}
