#include "MyComponent.hpp"
#include "SimpleInjector.hpp"

int main() {
    MyComponent comp;
    SimpleInjector::inject(comp);
    comp.doSomething();
    return 0;
}
