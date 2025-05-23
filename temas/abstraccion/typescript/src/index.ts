import { ButtonComponent } from './ButtonComponent';

const btn = new ButtonComponent('btn1', 'Haz clic aquí');

console.log(btn.render());  // Muestra el botón
btn.hide();                 // Oculta el botón
console.log(btn.render());  // No muestra nada
