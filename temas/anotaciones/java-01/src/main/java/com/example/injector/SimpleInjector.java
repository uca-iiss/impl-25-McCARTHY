package com.example.injector;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.example.annotations.Inject;

public class SimpleInjector {

    // Mapa para asociar interfaces a implementaciones concretas
    private static final Map<Class<?>, Class<?>> interfaceToImpl = new HashMap<>();

    static {
        // Aquí registra las implementaciones concretas
        interfaceToImpl.put(com.example.service.Logger.class, com.example.service.ConsoleLogger.class);
        // Puedes añadir más asociaciones si es necesario
    }

    public static void inject(Object target) {
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> fieldType = field.getType();
                try {
                    Class<?> implClass = fieldType;
                    // Si es interfaz, buscar la implementación
                    if (fieldType.isInterface()) {
                        implClass = interfaceToImpl.get(fieldType);
                        if (implClass == null) {
                            throw new RuntimeException("No implementation found for interface " + fieldType.getName());
                        }
                    }
                    Object dependency = implClass.getDeclaredConstructor().newInstance();
                    field.setAccessible(true);
                    field.set(target, dependency);
                } catch (Exception e) {
                    throw new RuntimeException("Error injecting " + fieldType.getName(), e);
                }
            }
        }
    }
}
