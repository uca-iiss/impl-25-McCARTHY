#include <gtest/gtest.h>
#include "MyComponent.hpp"

// Mock simple para Logger (solo para test)
class MockLogger : public Logger {
public:
    std::string lastMessage;
    void log(const std::string& msg) override {
        lastMessage = msg;
    }
};

TEST(MyComponentTest, GetValueReturns42) {
    MyComponent comp;
    EXPECT_EQ(comp.getValue(), 42);
}

TEST(MyComponentTest, DoSomethingLogsMessage) {
    MyComponent comp;
    MockLogger mockLogger;
    comp.logger = &mockLogger;

    comp.doSomething();

    EXPECT_EQ(mockLogger.lastMessage, "MyComponent está haciendo algo.");
}

TEST(MyComponentTest, DoSomethingWithoutLoggerPrintsMessage) {
    MyComponent comp;
    comp.logger = nullptr;
    comp.doSomething();

    SUCCEED();  // Simplemente pasa el test si no hay crash
}


int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
