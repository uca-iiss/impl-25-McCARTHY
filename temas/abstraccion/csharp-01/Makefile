# Definir el nombre del proyecto y la carpeta de pruebas
PROJECT_NAME = Bancoapp
TEST_PROJECT = Bancoapp.Tests
BUILD_DIR = bin/Debug/net8.0

# Comandos
.PHONY: all build clean test

all: clean build test

build:
	dotnet restore $(PROJECT_NAME)
	dotnet build $(PROJECT_NAME)

test:
	dotnet test $(TEST_PROJECT) --no-build --verbosity normal

clean:
	dotnet clean $(PROJECT_NAME)
