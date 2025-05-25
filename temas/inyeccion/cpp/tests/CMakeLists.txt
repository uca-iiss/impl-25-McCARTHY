find_package(GTest REQUIRED)
include(GoogleTest)

add_executable(service_test service_test.cpp)
target_link_libraries(service_test service GTest::gtest GTest::gtest_main pthread)

gtest_discover_tests(service_test)
