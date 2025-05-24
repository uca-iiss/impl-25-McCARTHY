# Ejemplo de Lambdas en Ruby con Jenkins

Este ejemplo demuestra:

- Uso de `lambda`
- Uso de `proc`
- Paso de bloques con `yield`

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

✅ Con esto, cubres todos los requisitos académicos del tema **Lambdas** en Ruby.

¿Quieres que te pase ahora todos los ficheros separados para copiar y pegar manualmente, como hicimos antes?
