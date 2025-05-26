# Ejemplo de Errores en JavaScript

El objetivo de este ejemplo es mostrar cómo manejar errores y datos incompletos en flujos de procesamiento, evitando fallos como accesos a propiedades `undefined`, valores nulos o fallos silenciosos.

Este patrón es especialmente útil en aplicaciones reales donde la entrada de datos no está garantizada, como APIs externas o formularios de usuario mal completados.

---

## Fundamento Teórico

### Problema

JavaScript permite acceder a propiedades anidadas sin protección, lo cual puede lanzar errores si alguna parte del objeto es `null` o `undefined`.

**Ejemplo peligroso:**

```js
pedido.cliente.nombre  // puede fallar si cliente es undefined
```

###  Solución Aplicada

- Encadenamiento opcional: `pedido?.cliente?.nombre` para evitar errores si alguna parte intermedia es `undefined`.
- Operador de fusión nula: `??` para dar valores por defecto.
- Validación explícita: `if (!pedido?.cliente?.nombre || pedido.total == null)`

Estas prácticas evitan el uso abusivo de `null` y garantizan un flujo de ejecución predecible y robusto.

---

##  Descripción de Componentes

### `src/data/pedidos.js`

Define un array con pedidos simulados, incluyendo algunos mal formateados (sin cliente o sin total), para probar la robustez del sistema.

```js
export const pedidos = [
  { id: 1, cliente: { nombre: 'Ana', vip: true }, total: 120, pagado: true, descuento: 10 },
  { id: 2, cliente: { nombre: 'Luis' }, total: null, pagado: true },
  { id: 3, cliente: { nombre: 'Marta' }, pagado: true },
  { id: 4, cliente: null, total: 80, pagado: true },
  { id: 5, cliente: { nombre: 'Carlos', vip: false }, total: 95, pagado: true }
];
```

### `src/logic/procesador.js`

Contiene las funciones principales:

- `limpiarPedido(pedido)`: valida el pedido y devuelve `null` si está mal formado. Aplica descuentos.
- `procesarPedidos(pedidos)`: filtra los pedidos válidos y pagados, limpiando los incorrectos.

```js
export function limpiarPedido(pedido) {
  if (!pedido?.cliente?.nombre || pedido?.total == null) {
    console.warn(\`Pedido \${pedido?.id ?? 'desconocido'} inválido\');
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
```

Usa `?.`, `??` y validaciones explícitas para garantizar seguridad en tiempo de ejecución.

### `tests/procesador.test.js`

Contiene tests con Jest para:

- Verificar que se descartan los pedidos mal formados.
- Comprobar que se aplican correctamente los descuentos.
- Validar que el resultado final incluye solo los pedidos válidos y pagados.

```js
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
```

### `package.json`

Define el proyecto como módulo Node.js, con dependencias para:

- `jest`: ejecución de tests.
- Estructura de scripts para facilitar los comandos `npm install` y `npm test`.

```json
{
  "name": "errores",
  "version": "1.0.0",
  "description": "Ejemplo de manejo de errores en JavaScript con streams",
  "type": "module",
  "main": "errores/app.js",
  "scripts": {
    "test": "jest"
  },
  "keywords": [],
  "author": "",
  "license": "ISC",
  "devDependencies": {
    "@babel/core": "^7.27.1",
    "@babel/preset-env": "^7.27.2",
    "babel-jest": "^29.7.0",
    "jest": "^29.7.0"
  },
  "jest": {
    "testMatch": ["**/tests/**/*.test.js"],
    "transform": {
      "^.+\.js$": "babel-jest"
    }
  }
}
```

---

## ▶ Ejecución Local

### Instalar dependencias

```bash
npm install
```

### Ejecutar tests

```bash
npm test
```

---

##  Jenkinsfile

Define un pipeline simple de CI/CD con:

- Etapa de instalación (`npm install`)
- Etapa de test (`npm test`)

Usa el agente Docker con la imagen oficial `node:18` para asegurar compatibilidad sin necesidad de instalación previa de Node.js en el entorno Jenkins.

```groovy
pipeline {
    agent {
        docker {
            image 'node:18'
        }
    }

    stages {
        stage('Node Version'){
            steps{
                sh 'node --version'
            }
        }

        stage('Instalar dependencias') {
            steps {
                sh 'npm i'
            }
        }

        stage('Ejecutar tests') {
            steps {
                sh 'npm test'
            }
        }
    }

    post {
        always {
            script {
                echo 'Terminado'
            }
        }
    }
}
```

---

##  Resultados Esperados

Durante la ejecución de los tests, se imprimen advertencias para los pedidos inválidos, pero no provocan errores. El resultado final muestra:

- Tests superados
- Consola informando los problemas detectados (como "Pedido 1 inválido")

---

##  Conclusión

Este proyecto muestra cómo implementar un mejor trato de errores en JavaScript, sin recurrir a excepciones ni permitir que valores `undefined` interrumpan el flujo del programa.

Aplicando buenas prácticas como validaciones estrictas y estructuras seguras (`?.`, `??`), se mejora la fiabilidad del código y se previenen errores típicos en entornos productivos.