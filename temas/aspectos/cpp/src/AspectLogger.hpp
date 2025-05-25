#ifndef ASPECT_LOGGER_HPP
#define ASPECT_LOGGER_HPP

#include <iostream>
#include <functional>

template<typename Func>
void withLogging(const std::string& name, Func f) {
    std::cout << "[LOG] Inicio de: " << name << std::endl;
    f();
    std::cout << "[LOG] Fin de: " << name << std::endl;
}

#endif // ASPECT_LOGGER_HPP
