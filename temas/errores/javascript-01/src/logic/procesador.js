export function limpiarPedido(pedido) {
  if (!pedido?.cliente?.nombre || pedido?.total == null) {
    console.warn(`Pedido ${pedido?.id ?? 'desconocido'} inválido`);
    return null;
  }

  const descuento = pedido.descuento ?? 0;
  return {
    id: pedido.id,
    nombreCliente: pedido.cliente.nombre,
    vip: pedido.cliente.vip ?? false,
    total: pedido.total - descuento
  };
}

export function procesarPedidos(pedidos) {
  const pedidosValidos = pedidos
    .filter(p => p.pagado === true)
    .map(limpiarPedido)
    .filter(Boolean);

  const total = pedidosValidos.reduce((acc, p) => acc + p.total, 0);
  const vip = pedidosValidos.filter(p => p.vip).reduce((acc, p) => acc + p.total, 0);

  return { pedidosValidos, total, vip };
}
