const pedidos = require('./data/pedidos');
const { procesarPedidos } = require('./logic/procesador');

const resultado = procesarPedidos(pedidos);

console.log("Pedidos válidos:", resultado.pedidosValidos);
console.log("Ingresos totales:", resultado.total, "€");
console.log("Ingresos VIP:", resultado.vip, "€");
