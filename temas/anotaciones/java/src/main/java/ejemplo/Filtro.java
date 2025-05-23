package ejemplo;

import java.util.List;

/**
 * Interfaz para las estrategias de filtrado de productos
 */
public interface Filtro {
    List<Producto> filtrar(List<Producto> productos);

    // Nuevo método para configuración, vacia por defecto
    default void configurar(Object parametro) {}
}