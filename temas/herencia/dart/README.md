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

1. Clona el repositorio si no lo has hecho antes

2. Pon los siguientes comandos estando en el directorio `herencia/dart`:
```bash
terraform init
terraform apply
```
3. Accede a `localhost:8080`. En caso de que te pregunte por la constrseña inicial, introducir en la terminal:
```bash
docker exec -it jenkins_herencia cat /var/jenkins_home/secrets/initialAdminPassword
```

4. **Crear el Pipeline**
    1. En el panel de Jenkins, haz clic en “New Item”.
    2. Introduce un nombre para el proyecto.
    3. Selecciona “Pipeline” como tipo de proyecto y haz clic en OK.
    4. En “Definition” selecciona Pipeline script from SCM
    5. En “SCM” elige Git
    6. En “Repository URL” introduce: `https://github.com/uca-iiss/RITCHIE-impl-25`
    7. En rama poner la que corresponda
    8. En Jenkinsfile, poner `temas/herencia/dart/Jenkinsfile`
    9. Haz clic en Save

5. **Destruir todo (limpieza)**

Ejecuta:
```bash
terraform destroy
```
Luego, para eliminar todos los archivos generador por **Terraform**:
```bash
rm -rf .terraform terraform.tfstate terraform.tfstate.backup
```