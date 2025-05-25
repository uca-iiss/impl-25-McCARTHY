
import { limpiarPedido, procesarPedidos } from '../src/logic/procesador.js';

describe('limpiarPedido()', () => {
  test('devuelve null si el cliente es inválido', () => {
    const pedido = { id: 1, cliente: null, total: 50, pagado: true };
    console.log(pedido);
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
  test('filtra correctamente los pedidos pagados y válidos', () => {
    const pedidos = [
      { id: 1, cliente: { nombre: 'Juan' }, total: 100, pagado: true },
      { id: 2, cliente: { nombre: 'María' }, total: 50, pagado: false },
      { id: 3, cliente: null, total: 80, pagado: true }
    ];
    const resultado = procesarPedidos(pedidos);
    expect(resultado.pedidosValidos.length).toBe(1);
    expect(resultado.total).toBe(100);
  });
});