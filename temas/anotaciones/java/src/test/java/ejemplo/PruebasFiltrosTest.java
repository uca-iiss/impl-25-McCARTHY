package ejemplo;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PruebasFiltrosTest {
    private Producto telefono;
    private Producto laptop;
    private Producto tablet;
    private List<Producto> productos;
    
    @BeforeEach
    void setUp() {
        // Inicializa los productos con sus atributos
        telefono = new Producto("iPhone 13", 999.99, "Telefonía");
        laptop = new Producto("MacBook Pro", 1999.99, "Computación");
        tablet = new Producto("iPad Air", 599.99, "Tablets");
        
        // Crea una lista de productos
        productos = Arrays.asList(telefono, laptop, tablet);
        
        // Inyecta los filtros en cada producto
        InyectorFiltros.inyectarFiltros(telefono);
        InyectorFiltros.inyectarFiltros(laptop);
        InyectorFiltros.inyectarFiltros(tablet);
    }
    
    @Test
    void testInyeccionFiltros() {
        // Verifica que los filtros se hayan inyectado correctamente
        assertNotNull(telefono.getFiltroNombre());
        assertNotNull(telefono.getFiltroPrecio());
        assertInstanceOf(FiltroNombre.class, telefono.getFiltroNombre());
        assertInstanceOf(FiltroPrecio.class, telefono.getFiltroPrecio());
    }
    
    @Test
    void testFiltroPorNombre() {
        // Filtra productos por nombre que contengan "Mac"
        List<Producto> resultados = telefono.filtrarPorNombre(productos, "Mac");
        assertEquals(1, resultados.size());
        assertTrue(resultados.contains(laptop));
        
        // Filtra productos por nombre que contengan "iP"
        resultados = telefono.filtrarPorNombre(productos, "iP");
        assertEquals(2, resultados.size());
        assertTrue(resultados.contains(telefono));
        assertTrue(resultados.contains(tablet));
    }
    
    @Test
    void testFiltroPorPrecio() {
        // Filtra productos con precio menor o igual a 1000.0
        List<Producto> resultados = telefono.filtrarPorPrecio(productos, 1000.0);
        assertEquals(2, resultados.size());
        assertTrue(resultados.contains(telefono));
        assertTrue(resultados.contains(tablet));
        assertFalse(resultados.contains(laptop));
        
        // Filtra productos con precio menor o igual a 600.0
        resultados = telefono.filtrarPorPrecio(productos, 600.0);
        assertEquals(1, resultados.size());
        assertTrue(resultados.contains(tablet));
    }
    
    @Test
    void testFiltroSinInyeccion() {
        // Crea un producto sin inyección de filtros
        Producto productoNoInyectado = new Producto("Apple Watch", 399.99, "Wearables");
        
        // Verifica que intentar usar un filtro sin inyección lanza una excepción
        assertThrows(NullPointerException.class, () -> {
            productoNoInyectado.filtrarPorNombre(productos, "Apple");
        });
    }
    
    @Test
    void testIgualdadProductos() {
        // Verifica la igualdad entre productos con los mismos atributos
        Producto mismoTelefono = new Producto("iPhone 13", 999.99, "Telefonía");
        assertEquals(telefono, mismoTelefono);
        
        // Verifica que productos diferentes no son iguales
        assertNotEquals(telefono, laptop);
    }
}