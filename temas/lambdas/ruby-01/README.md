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
- `Gemfile`:
- `test_main.rb`: Test 

## Ejecución manual

```bash
bundle install
ruby test_main.rb
ruby main.rb

docker build -t lambdas-ruby .
docker run --rm lambdas-ruby

---

## Jenkins
El Jenkinsfile define un pipeline que:
1. Clona el repositorio.
2. Instala dependencias ( bundle install ).
3. Ejecuta las pruebas ( ruby test_main.rb ).
4. Ejecuta la aplicación ( ruby main.rb ).

