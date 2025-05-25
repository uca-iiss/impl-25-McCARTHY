cmake_minimum_required(VERSION 3.10)
project(InyeccionCPP)

set(CMAKE_CXX_STANDARD 17)

add_library(service src/service.cpp)
add_executable(app src/main.cpp)
target_link_libraries(app service)

add_subdirectory(tests)
