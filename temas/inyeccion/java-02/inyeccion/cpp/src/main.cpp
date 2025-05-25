#include <iostream>
#include "service.hpp"

int main() {
    RealService service;
    std::cout << service.process() << std::endl;
    return 0;
}
