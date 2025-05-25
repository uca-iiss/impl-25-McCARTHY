#include <gtest/gtest.h>
#include "../src/service.hpp"

TEST(ServiceTest, RealServiceProcess) {
    RealService service;
    EXPECT_EQ(service.process(), "RealService processed!");
}
