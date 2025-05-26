CXX = g++
CXXFLAGS = -Wall -std=c++17

SRC = src/main.cpp
OBJ = $(SRC:.cpp=.o)
EXE = main

all: $(EXE)

$(EXE): $(OBJ)
	$(CXX) $(CXXFLAGS) -o $@ $^

%.o: %.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@

clean:
	rm -f $(OBJ) $(EXE)

test: all
	./tests/run_tests.sh
