const { limpiarPedido, procesarPedidos } = require('../src/logic/procesador');

describe('limpiarPedido()', () => {
  test('devuelve null si el cliente no tiene nombre', () => {
    const pedido = { id: 1, cliente: { vip: true }, total: 50 };
    expect(limpiarPedido(pedido)).toBeNull();
  });

  test('devuelve null si el total es null', () => {
    const pedido = { id: 2, cliente: { nombre: 'Ana' }, total: null };
    expect(limpiarPedido(pedido)).toBeNull();
  });

  test('aplica descuento correctamente', () => {
    const pedido = { 
      id: 3, 
      cliente: { nombre: 'Luis' }, 
      total: 100, 
      descuento: 10 
    };
    const resultado = limpiarPedido(pedido);
    expect(resultado.total).toBe(90);
  });

  test('mantiene vip en false cuando no está definido', () => {
    const pedido = { 
      id: 4, 
      cliente: { nombre: 'Carlos' }, 
      total: 200 
    };
    const resultado = limpiarPedido(pedido);
    expect(resultado.vip).toBe(false);
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
    expect(resultado.total).toBe(350); // 100 (Ana) + 250 (Carlos con descuento)
  });

  test('calcula correctamente el total VIP', () => {
    const resultado = procesarPedidos(pedidosTest);
    expect(resultado.vip).toBe(250); // Solo Carlos es VIP (300 - 50 descuento)
  });
});