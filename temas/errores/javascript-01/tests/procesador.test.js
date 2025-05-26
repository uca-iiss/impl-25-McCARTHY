
import { limpiarPedido, procesarPedidos } from '../src/logic/procesador.js';

describe('limpiarPedido()', () => {
  test('devuelve null si el cliente no tiene nombre', () => {
    const pedido = { id: 1, cliente: { vip: true }, total: 50 };
    expect(limpiarPedido(pedido)).toBeNull();
  });

  test('aplica descuento correctamente', () => {
    const pedido = { id: 2, cliente: { nombre: 'Ana' }, total: 100, descuento: 20 };
    const limpio = limpiarPedido(pedido);
    expect(limpio.total).toBe(80);
  });

  test('vip por defecto es false', () => {
    const pedido = { id: 3, cliente: { nombre: 'Luis' }, total: 60 };
    const limpio = limpiarPedido(pedido);
    expect(limpio.vip).toBe(false);
  });
});

describe('procesarPedidos()', () => {
  const pedidosTest = [
    { id: 1, cliente: { nombre: 'Ana' }, total: 100, pagado: true },
    { id: 2, cliente: { nombre: 'Luis' }, total: 200, pagado: false },
    { id: 3, cliente: { nombre: 'Carlos', vip: true }, total: 300, pagado: true, descuento: 50 },
    { id: 4, cliente: { }, total: 400, pagado: true }
  ];

  test('filtra correctamente los pedidos no pagados', () => {
    const resultado = procesarPedidos(pedidosTest);
    expect(resultado.pedidosValidos.length).toBe(2);
  });

  test('calcula correctamente el total general', () => {
    const resultado = procesarPedidos(pedidosTest);
    expect(resultado.total).toBe(350);
  });

});
