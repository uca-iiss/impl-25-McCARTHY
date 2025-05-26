import { ButtonComponent } from '../ButtonComponent';

describe('ButtonComponent', () => {
    test('render devuelve el texto correcto', () => {
        const button = new ButtonComponent('btn1', 'Botón de prueba');
        expect(button.render()).toBe('<button id="btn1">Botón de prueba</button>');
    });
});
