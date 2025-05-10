package ejemplo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación concreta de filtrado por precio máximo
 */
public class FiltroPrecio implements Filtro {
    private double precioMaximo;
    
    public FiltroPrecio(double precioMaximo) {
        this.precioMaximo = precioMaximo;
    }
    
    @Override
    public void configurar(Object parametro) {
        if (parametro instanceof Double precio) {
            this.precioMaximo = precio;
        } else {
            throw new IllegalArgumentException("Se esperaba un valor double");
        }
    }

    @Override
    public List<Producto> filtrar(List<Producto> productos) {
        return productos.stream()
                .filter(p -> p.getPrecio() <= precioMaximo)
                .collect(Collectors.toList());
    }
}