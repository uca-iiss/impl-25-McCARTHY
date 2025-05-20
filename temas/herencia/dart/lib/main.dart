import 'Contenedor.dart';

void main() 
{
  print('===== PERSONAJES =====');
  final elrond = Elfo('Elrond', 60, 120, 85.5);
  final thrall = Orco('Thrall', 80, 200, 110.0);
  final arthur = Humano('Arthur', 70, 150, 50.0, 65.0);
  final contenedor = Contenedor<Personaje>();
  contenedor
    ..add(elrond)
    ..add(thrall)
    ..add(arthur);

  print('\n===== DETALLES DE PERSONAJES =====');
  contenedor.mostrarDetalles();

  print('TOTAL DE PERSONAJES: ${contenedor.totalPersonajes()}');
}
