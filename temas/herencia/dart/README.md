# Herencia en Dart

Vamos ha exponer el tema de herencia y polimorfismo usando como ejemplo un pequeño programa de un juego de rol en Dart

## Implementación

El ejemplo consta de 3 archivos:

* Personaje.dart
* Contenedor.dart
* test.dart

### `Personaje.dart`

Este es el corazón del porgrama, aquí se define la clase Personaje y sus métodos

```dart

```

Nuestra clase base es:
```dart
sealed class Personaje
```
Con la palabra reservada (sealed) declaramos una clase abstracta que obliga que todas sus clases derivadas estén declaras en su mismo archivo.
Las clases Elfo, Orco y Humano heredan de Personaje con:
```dart
class Elfo extends Personaje ...
class Orco extends Personaje ...
class Humano extends Personaje ...
```
Mediante el uso de (extends) realizamos una herencia de interfaz + implementación. Esto significa que todas estas clases comparten los atributos de Personaje como _nombre, _ataque, _vida y el método describir() que es abstracto en la clase padre.

En Dart, no se puede heredar de más de una clase, pero podemos usar mixins para reutilizar código entre múltiples clases (es como una alternativa a la herencia múltiple). Con ellos podemos incluirles comportamiento a nuestras clases mediante (with):
 ```dart
class Elfo extends Personaje with Nadador, Terrestre, Volador
```
Con esto, Elfo hereda de Personaje y mezcla los comportamientos definidos en los mixins. Los mixins exigen implementar ciertos métodos dentro de las clases ya que es así como está implementado en el ejemplo.

POLIMORFISMO
El polimorfismo en Dart se manifiesta en dos formas principales en tu ejemplo: polimorfismo por herencia y por interfaces (subtipado).

1. Polimorfismo por herencia (override de métodos):
Cada clase (Elfo, Orco, Humano) implementa su propia versión de:

```dart
@override
String describir()
```

2. Polimorfismo a través de interfaces (métodos que aceptan mixins):
Estas funciones:

```dart
void hacerNadar(Nadador nadador) => print(nadador.nadar());
void hacerCaminar(Terrestre terrestre) => print(terrestre.caminar());
void hacerVolar(Volador volador) => print(volador.volar());
```
Pueden aceptar cualquier objeto que implemente el mixin correspondiente. Esto es polimorfismo por subtipado: no importa la clase concreta, mientras implemente la interfaz del mixin.

Por ejemplo:

```dart
hacerNadar(Elfo(...));    // Aceptado porque Elfo usa Nadador
hacerCaminar(Humano(...)); // Aceptado porque Humano usa Terrestre
```
Esto permite que las funciones trabajen con múltiples tipos de objetos de manera flexible, sin saber sus detalles internos.

3. Polimorfismo en switch sobre clases sealed:
Dart permite usar switch con clases sealed para manejar subtipos de manera segura en tiempo de compilación:

```dart
String infoPoderDelPersonaje(Personaje p) {
  return switch (p) {
    Elfo e => "Poder mágico de: ${e.getPoder}",
    Orco o => "Poder físico de: ${o.getPoder}",
    Humano h => "Poder físico de ${h.getPoderFisico} y mágico de ${h.getPoderMagico}"
  };
}
```
Aquí se usa polimorfismo para actuar diferente dependiendo del tipo concreto de Personaje, accediendo a atributos únicos de cada subclase.

### `Contenedor.dart`

Define la lógica para almacenar y procesar personajes de forma genérica y extensible.

```dart
import 'Personaje.dart';
export 'Personaje.dart';

class Contenedor<T extends Personaje> 
{
  final List<T> _personajes = [];

  void add(T personaje) => _personajes.add(personaje);

  void mostrarDetalles() 
  {
    for (var p in _personajes) 
    {
      print("> ${p.describir()}");
      print("INFO: ${infoPoderDelPersonaje(p)}");

      if(p is Nadador) hacerNadar(p as Nadador);
      if(p is Terrestre) hacerCaminar(p as Terrestre);
      if(p is Volador) hacerVolar(p as Volador);

      print("═" * 40);
    }
  }

  int totalPersonajes() => _personajes.length;
}
```

1. Genéricos con restricción (T extends Personaje)
dart
Copiar
Editar
class Contenedor<T extends Personaje>
¿Qué significa?
El tipo genérico T solo puede ser una subclase de Personaje (Elfo, Orco, Humano, etc.).

Esto permite que Contenedor pueda trabajar con una lista de cualquier tipo específico de personaje, manteniendo el tipo seguro en tiempo de compilación.

 Esto es polimorfismo genérico o parametrización de tipos, una forma de reutilización de código con seguridad de tipos.

 2. Almacenamiento y operaciones genéricas
dart
Copiar
Editar
final List<T> _personajes = [];
Aquí guardas cualquier subtipo de Personaje (como List<Elfo> o List<Orco>) según cómo se instancie la clase. Por ejemplo:

dart
Copiar
Editar
var contenedorElfos = Contenedor<Elfo>();
var contenedorHumanos = Contenedor<Humano>();
Esto también es una forma de polimorfismo: estás tratando con diferentes tipos, pero bajo una interfaz común (Personaje).

 3. Polimorfismo dinámico con describir() y infoPoderDelPersonaje()
dart
Copiar
Editar
print("> ${p.describir()}");
print("INFO: ${infoPoderDelPersonaje(p)}");
Aquí estás invocando el método describir() que cada subclase sobrescribe.

Esto es polimorfismo dinámico puro: el método llamado depende del tipo real del objeto en tiempo de ejecución, no del tipo declarado (T o Personaje).

 4. Uso de is para comprobación de interfaces (mixins)
dart
Copiar
Editar
if (p is Nadador) hacerNadar(p as Nadador);
if (p is Terrestre) hacerCaminar(p as Terrestre);
if (p is Volador) hacerVolar(p as Volador);
Este bloque hace uso de:

is: para comprobar si un objeto implementa una interfaz (en este caso, un mixin).

as: para hacer cast al tipo correspondiente y llamar al método especializado.

Esto es polimorfismo estructural: no importa la clase exacta, importa si implementa cierta capacidad (caminar, nadar, volar).

Y, como hacerNadar(), hacerCaminar(), etc. son funciones que trabajan con interfaces (Nadador, Terrestre, etc.), es también polimorfismo por interfaces.

### `test.dart`

Es el fichero donde están implementadas las pruebas unitarias.

## Implantación