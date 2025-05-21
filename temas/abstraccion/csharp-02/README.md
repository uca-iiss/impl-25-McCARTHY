# Tema A.1 - Abstracción en C#

Este ejemplo demuestra el uso del principio de abstracción en programación orientada a objetos usando C#.

## Código

- [Programa principal (Program.cs)](src/Program.cs)
- [Pruebas unitarias (AbstraccionTests.cs)](tests/AbstraccionTests.cs)

## Archivos de construcción

- [Proyecto C# (.csproj)](../abstraccion/Abstraccion.csproj)

## CI/CD y Aprovisionamiento

- [Pipeline Jenkins (Jenkinsfile)](Jenkinsfile)
- [Dockerfile para construcción](Dockerfile)

## Construcción local

```bash
docker build -t abstraccion-csharp .
docker run abstraccion-csharp
