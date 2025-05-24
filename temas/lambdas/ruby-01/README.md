# Ejemplo de Lambdas en Ruby con Jenkins

Este ejemplo demuestra:

- Uso de `lambda`
- Uso de `proc`
- Paso de bloques con `yield`
- Procesamiento de streams de datos usando `.map` con lambdas

## Estructura

- `main.rb`: Script principal con ejemplos funcionales
- `Dockerfile`: Permite ejecutar el proyecto en contenedor Ruby
- `Jenkinsfile`: Automatiza la ejecución desde Jenkins

## Ejecución manual

```bash
ruby main.rb
docker build -t lambdas-ruby .
docker run --rm lambdas-ruby

---
