all: main

main: src/main.cpp
	g++ -I./src -o main src/main.cpp

test: tests/test.cpp
	g++ -I./src -o test tests/test.cpp
	./test

clean:
	rm -f main test
