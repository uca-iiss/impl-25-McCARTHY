"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const ButtonComponent_1 = require("./ButtonComponent");
const btn = new ButtonComponent_1.ButtonComponent('btn1', 'Haz clic aquí');
console.log(btn.render()); // Muestra el botón
btn.hide(); // Oculta el botón
console.log(btn.render()); // No muestra nada
