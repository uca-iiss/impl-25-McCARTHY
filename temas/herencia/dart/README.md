# Herencia en Dart

Vamos a exponer el tema de **herencia** y **polimorfismo** usando como ejemplo un pequeño programa de un juego de rol escrito en **Dart**.

---

## Estructura del proyecto

```
dart/
├── lib/
│   ├── Personaje.dart      # Clases base, subclases y mixins
│   └── Contenedor.dart     # Genérico que almacena Personaje
├── bin/
│   └── main.dart           # Punto de entrada: crea y muestra personajes
├── test/
│   └── personaje_test.dart # Pruebas unitarias de los modelos
├── pubspec.yaml            # Dependencias y metadatos del paquete
└── Jenkinsfile             # Pipeline CI (instala deps, analiza, testea, corre main)
...
```
---

## Implementación

### `lib/Personaje.dart`

*(Corazón del programa: clases, subclases, mixins …)*

```dart
mixin Nadador 
{
  String nadar() => throw UnimplementedError("Debe implementar el método en la clase que use este mixin");
}

mixin Terrestre 
{
  String caminar() => throw UnimplementedError("Debe implementar el método en la clase que use este mixin");
}

mixin Volador 
{
  String volar() => throw UnimplementedError("Debe implementar el método en la clase que use este mixin");
}

sealed class Personaje
{
  String _nombre;
  int _ataque;
  int _vida;

  Personaje(this._nombre, this._ataque, this._vida);

  get getNombre => _nombre;
  get getVida => _vida;
  get getAtaque => _ataque;
  String describir();
}

class Elfo extends Personaje with Nadador, Terrestre, Volador 
{
  double _poderMagico;

  Elfo(String nombre, int ataque, int vida, this._poderMagico): super(nombre, ataque, vida);

  get getPoder => _poderMagico;

  @override
  String describir() => "Elfo $_nombre: $_ataque de ataque, $_vida de vida, daño mágico de $_poderMagico.";

  @override
  String nadar() => "$_nombre está nadando (Elfo)";

  @override
  String caminar() => "$_nombre está caminando (Elfo)";

  @override
  String volar() => "$_nombre está volando (Elfo)";
}

class Orco extends Personaje with Terrestre 
{
  double _poderFisico;

  get getPoder => _poderFisico;

  Orco(String nombre, int ataque, int vida, this._poderFisico): super(nombre, ataque, vida);

  @override
  String describir() => "Orco $_nombre: $_ataque de ataque, $_vida de vida, daño físico de $_poderFisico.";

  @override
  String caminar() => "$_nombre está caminando (Orco)";
}

class Humano extends Personaje with Terrestre, Volador 
{
  double _poderFisico;
  double _poderMagico;

  get getPoderFisico => _poderFisico;
  get getPoderMagico => _poderMagico;

  Humano(String nombre, int ataque, int vida, this._poderMagico, this._poderFisico): super(nombre, ataque, vida);

  @override
  String describir() => "Humano $_nombre: $_ataque de ataque, $_vida de vida, daño mágico de $_poderMagico, daño físico de $_poderFisico.";

  @override
  String caminar() => "$_nombre está caminando (Humano)";

  @override
  String volar() => "$_nombre está volando (Humano)";
}

void hacerNadar(Nadador nadador) => print(nadador.nadar());
void hacerCaminar(Terrestre terrestre) => print(terrestre.caminar());
void hacerVolar(Volador volador) => print(volador.volar());

String infoPoderDelPersonaje(Personaje p) 
{
  return switch (p) {
    Elfo e => "Poder mágico de: ${e.getPoder}",
    Orco o => "Poder físico de: ${o.getPoder}",
    Humano h => "Poder físico de ${h.getPoderFisico} y mágico de ${h.getPoderMagico}"
  };
}
```

Este ejemplo aprovecha varias características del lenguaje Dart para ilustrar herencia, mixins y diferentes formas de polimorfismo:

* **Clase sealed**: La clase base `Personaje` está marcada como `sealed`, lo que significa que todas sus subclases deben estar definidas en el mismo archivo. Esto permite al compilador conocer todos los posibles subtipos, lo que habilita estructuras como switch exhaustivos sobre instancias de Personaje.

* **Herencia simple**: Las clases `Elfo`, `Orco` y `Humano` extienden de Personaje, heredando sus atributos como _nombre, _ataque y _vida, y obligándose a implementar el método abstracto describir().

* **Mixins**: Dart no permite herencia múltiple, pero permite mezclar comportamientos con mixins. Por ejemplo, la clase `Elfo` incluye comportamientos de `Nadador`, `Terrestre` y `Volador` mediante la sintaxis `with`. Esto le permite tener funcionalidades específicas sin heredar de varias clases.

* **Polimorfismo por herencia**: Cada subclase sobreescribe el método `describir()` usando `@override`. Esto permite que cada tipo de personaje personalice cómo se describe a sí mismo, aunque todos compartan la misma interfaz.

* **Polimorfismo por interfaz (mixins)**: Las funciones como `hacerNadar(Nadador n)`, `hacerCaminar(Terrestre t)` o `hacerVolar(Volador v)` pueden trabajar con cualquier objeto que implemente esos mixins, sin importar su clase concreta. Esto es polimorfismo basado en capacidades más que en jerarquía.

* **Uso de switch con clases sealed**: En la función `infoPoderDelPersonaje`, se usa un switch sobre la instancia de Personaje para determinar su tipo y acceder a información específica de cada subclase. Gracias a que la clase es sealed, Dart puede verificar en *tiempo de compilación* que todos los casos estén cubiertos.

### `lib/Contenedor.dart`

```dart
class Contenedor<T extends Personaje> { … }
```

La clase `Contenedor` incorpora los siguientes conceptos:

* **Genéricos con restricción**: Al declarar `Contenedor<T extends Personaje>`, se establece que solo se podrán usar tipos que hereden de Personaje. Esto garantiza que todos los objetos añadidos al contenedor compartan una interfaz común, lo que permite un tratamiento uniforme y seguro en tiempo de compilación.

* **Polimorfismo dinámico**: Dentro del método `mostrarDetalles`, la llamada a `p.describir()` se resuelve en **tiempo de ejecución** según el tipo real del objeto. Aunque el contenedor solo sabe que `p` es un `Personaje`, el método adecuado (de Elfo, Orco o Humano) es invocado automáticamente. Esto es un ejemplo claro de polimorfismo dinámico.

* **Comprobación de capacidades en tiempo de ejecución**: La combinación de `is` y `as` permite detectar si un objeto implementa un mixin específico. Por ejemplo, con `if (p is Nadador)`, se comprueba si el personaje puede nadar, y con `hacerNadar(p as Nadador)` se realiza un cast seguro para usar esa capacidad. Esto permite aplicar comportamientos según las capacidades reales del objeto, sin importar su clase exacta.

### `lib/main.dart`

Demuestra la creación de personajes, uso del contenedor y ejecución de mixins:

```dart
import '../lib/Contenedor.dart';

void main() {
  final contenedor = Contenedor<Personaje>()
    ..add(Elfo('Elrond', 60, 120, 85.5))
    ..add(Orco('Thrall', 80, 200, 110))
    ..add(Humano('Arthur', 70, 150, 50, 65));

  contenedor.mostrarDetalles();
  print('TOTAL: ${contenedor.totalPersonajes()}');
}
```

### `test/personaje_test.dart`

Contiene pruebas unitarias básicas con `package:test` que verifican getters y salidas de los métodos.

---

## Implantación

```bash
# 1. Clonar el repositorio
git clone <tu-fork-o-URL>

# 2. Ir a la carpteta herencia/dart/
cd dart

# 3. Desplegar vía Terraform
terraform init
terraform apply
```

4. Acceder a `http://localhost:8080/`
5. Instalar los pluggins recomendados y ejecuta el siguiente comando en el caso de que te pida la contraseña inicial:
```bash
docker exec jenkins_herencia cat /var/jenkins_home/secrets/initialAdminPassword
```

El repositorio incluye un `Jenkinsfile` que define las fases:

| Stage                | Comando                                  |
|----------------------|------------------------------------------|
| Preparar entorno     | `dart --version`                         |
| Instalar dependencias| `dart pub get`                           |
| Analizar código      | `dart analyze lib/`                      |
| Ejecutar tests       | `dart test test/personaje_test.dart`     |
|   Ejecutar main      | `dart run bin/main.dart`                 |
| Empaquetar artefacto | `tar -czvf personaje_package.tar.gz …`   |

Para que Jenkins lo ejecute:

1. Crea un **Pipeline** → **Pipeline script from SCM**.
2. URL del repo: `https://github.com/uca-iiss/RITCHIE-impl-25.git`
3. Branch: `main` (o el que corresponda).
4. Script Path: `temas/herencia/dart/Jenkinsfile`
5. Guarda y lanza el pipeline.  

---