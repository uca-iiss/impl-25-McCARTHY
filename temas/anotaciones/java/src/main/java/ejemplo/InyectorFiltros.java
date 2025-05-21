package ejemplo;

import java.lang.reflect.Field;

/**
 * Clase responsable de inyectar las implementaciones de filtros
 */
public class InyectorFiltros {
    // Valores por defecto para los filtros
    private static final String TEXTO_BUSQUEDA_DEFAULT = "";
    private static final double PRECIO_MAXIMO_DEFAULT = 1000.0;
    
    /**
     * Inyecta las dependencias de filtrado en un objeto
     */
    public static void inyectarFiltros(Object objeto) {
        Class<?> clazz = objeto.getClass();
        
        for (Field campo : clazz.getDeclaredFields()) {
            if (campo.isAnnotationPresent(FiltrarPorNombre.class)) {
                inyectarFiltroNombre(objeto, campo);
            } else if (campo.isAnnotationPresent(FiltrarPorPrecio.class)) {
                inyectarFiltroPrecio(objeto, campo);
            }
        }
    }
    
    private static void inyectarFiltroNombre(Object objeto, Field campo) {
        try {
            campo.setAccessible(true);
            Filtro filtro = new FiltroNombre(TEXTO_BUSQUEDA_DEFAULT);
            campo.set(objeto, filtro);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error al inyectar FiltroNombre", e);
        }
    }
    
    private static void inyectarFiltroPrecio(Object objeto, Field campo) {
        try {
            campo.setAccessible(true);
            Filtro filtro = new FiltroPrecio(PRECIO_MAXIMO_DEFAULT);
            campo.set(objeto, filtro);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error al inyectar FiltroPrecio", e);
        }
    }
}