package com.example.service;

import com.example.annotations.Inject;

public class MyComponent {

    @Inject
    private Logger logger;

    public void run() {
        logger.log("¡Inyección completada con éxito!");
    }
}
