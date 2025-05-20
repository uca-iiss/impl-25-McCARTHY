package ejemplo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación concreta de filtrado por coincidencia en el nombre
 */
public class FiltroNombre implements Filtro {
    private String textoBusqueda;
    
    public FiltroNombre(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda.toLowerCase();
    }

    @Override
    public void configurar(Object parametro) {
        this.textoBusqueda = ((String) parametro).toLowerCase();
    }
    
    @Override
    public List<Producto> filtrar(List<Producto> productos) {
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(textoBusqueda))
                .collect(Collectors.toList());
    }
}