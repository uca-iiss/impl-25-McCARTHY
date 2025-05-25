add_library(service Service.cpp)

add_executable(app main.cpp)
target_link_libraries(app service)
