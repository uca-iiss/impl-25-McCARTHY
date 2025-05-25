cmake_minimum_required(VERSION 3.10)
project(DIExample)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Para que el compilador encuentre los headers
include_directories(include)

# Compilar la aplicación principal
add_executable(di_example src/main.cpp)

# GoogleTest
enable_testing()
find_package(GTest REQUIRED)
include_directories(${GTEST_INCLUDE_DIRS})

# Compilar los tests — sin src/MyComponent.cpp porque todo está en headers
add_executable(runTests test/MyComponentTest.cpp)
target_link_libraries(runTests ${GTEST_LIBRARIES} pthread)

add_test(NAME MyComponentTest COMMAND runTests)
