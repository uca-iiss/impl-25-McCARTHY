add_library(service STATIC service.cpp)
add_executable(main main.cpp)
target_link_libraries(main service)
