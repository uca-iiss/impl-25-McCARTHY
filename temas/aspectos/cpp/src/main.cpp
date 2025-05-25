#include "AspectLogger.hpp"
#include <iostream>

void saludar() {
    std::cout << "Hola, mundo!" << std::endl;
}

void calcular() {
    int suma = 5 + 3;
    std::cout << "Resultado: " << suma << std::endl;
}

int main() {
    withLogging("saludar", saludar);
    withLogging("calcular", calcular);
    return 0;
}
