#pragma once
#include <string>

class Service {
public:
    virtual std::string process() const = 0;
    virtual ~Service() = default;
};

class RealService : public Service {
public:
    std::string process() const override {
        return "RealService processed!";
    }
};
