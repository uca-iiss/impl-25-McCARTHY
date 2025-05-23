package ejemplo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación para marcar campos que deben recibir un filtro por precio
 * Se usará para inyectar la estrategia de filtrado por precio
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FiltrarPorPrecio {
}