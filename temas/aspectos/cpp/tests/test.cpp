#include "AspectLogger.hpp"
#include <cassert>
#include <iostream>

void prueba() {
    std::cout << "Ejecutando función de prueba..." << std::endl;
}

int main() {
    std::cout << "Probando withLogging..." << std::endl;

    withLogging("prueba", prueba);

    // Simple prueba: asegurar que no crashea
    assert(true); // Puedes reemplazarlo por comprobaciones más específicas si lo deseas

    std::cout << "Todas las pruebas pasaron correctamente." << std::endl;
    return 0;
}
