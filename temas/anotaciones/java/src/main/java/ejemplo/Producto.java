package ejemplo;

import java.util.List;
import java.util.Objects;

/**
 * Clase que representa un producto con filtros inyectados
 */
public class Producto {
    private final String nombre;
    private final double precio;
    private final String categoria;
    
    @FiltrarPorNombre
    private Filtro filtroNombre;
    
    @FiltrarPorPrecio
    private Filtro filtroPrecio;
    
    public Producto(String nombre, double precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getCategoria() { return categoria; }
    public Filtro getFiltroNombre() { return filtroNombre; }
    public Filtro getFiltroPrecio() { return filtroPrecio; }
    
    /**
     * Filtra una lista de productos por nombre usando el filtro inyectado
     */
    public List<Producto> filtrarPorNombre(List<Producto> productos, String texto) {
        filtroNombre.configurar(texto);
        return filtroNombre.filtrar(productos);
    }
    
    /**
     * Filtra una lista de productos por precio usando el filtro inyectado
     */
    public List<Producto> filtrarPorPrecio(List<Producto> productos, double precioMax) {
        filtroPrecio.configurar(precioMax);
        return filtroPrecio.filtrar(productos);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Double.compare(producto.precio, precio) == 0 &&
                Objects.equals(nombre, producto.nombre) &&
                Objects.equals(categoria, producto.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, precio, categoria);
    }
    
    @Override
    public String toString() {
        return String.format("%s - $%.2f (%s)", nombre, precio, categoria);
    }
}