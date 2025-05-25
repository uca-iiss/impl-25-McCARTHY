#pragma once
#include "Logger.hpp"

// Macro para marcar campos para inyección (no hace nada en sí, solo indicativo)
#define INJECT

class MyComponent {
public:
    INJECT Logger* logger = nullptr;  // campo para inyectar

    int getValue() {
        // Simula una operación que retorna un valor
        return 42;
    }
    void doSomething() {
        if (logger) logger->log("MyComponent está haciendo algo.");
        else std::cout << "Logger no está inyectado." << std::endl;
    }
};
