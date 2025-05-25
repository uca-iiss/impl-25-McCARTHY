#pragma once
#include <string>
#include <iostream>

class Logger {
public:
    virtual void log(const std::string& msg) = 0;
    virtual ~Logger() = default;
};

class ConsoleLogger : public Logger {
public:
    void log(const std::string& msg) override {
        std::cout << "[LOG]: " << msg << std::endl;
    }
};
