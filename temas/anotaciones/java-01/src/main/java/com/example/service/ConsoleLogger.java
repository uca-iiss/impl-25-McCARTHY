package com.example.service;

import com.example.service.Logger; // Este import debe apuntar a una interfaz

public class ConsoleLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}
