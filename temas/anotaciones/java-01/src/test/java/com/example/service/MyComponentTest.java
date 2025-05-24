package com.example.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.example.injector.SimpleInjector;

public class MyComponentTest {

    @Test
    void testInjectionAndRun() {
        MyComponent component = new MyComponent();
        SimpleInjector.inject(component);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        component.run();

        assertTrue(out.toString().contains("¡Inyección completada con éxito!"));
    }
}
