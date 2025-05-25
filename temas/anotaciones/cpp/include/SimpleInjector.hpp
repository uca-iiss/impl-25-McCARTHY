#pragma once
#include "MyComponent.hpp"
#include "Logger.hpp"

class SimpleInjector {
public:
    static void inject(MyComponent& component) {
        static ConsoleLogger consoleLogger;
        component.logger = &consoleLogger;
    }
};
