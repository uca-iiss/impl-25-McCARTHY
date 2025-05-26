import { pedidos } from './data/pedidos.js';
import { procesarPedidos } from './logic/procesador.js';

procesarPedidos(pedidos);

const resultado = procesarPedidos(pedidos);

console.log("Pedidos válidos:", resultado.pedidosValidos);
console.log("Ingresos totales:", resultado.total, "€");
console.log("Ingresos VIP:", resultado.vip, "€");
