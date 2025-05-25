add_library(service service.cpp service.hpp)

add_executable(app main.cpp)
target_link_libraries(app service)
