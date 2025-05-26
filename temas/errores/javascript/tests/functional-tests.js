// test/functional-tests.js - Tests funcionales automatizados

import { getDurationOfAlbumWithNameTraditional } from '../services/traditional-service.js';
import { getDurationOfAlbumWithNameModernCompact } from '../services/modern-service.js';
import { getDurationOfAlbumWithNameOptional } from '../services/optional-service.js';
import { 
  findOldestAlbum, 
  calculateTotalDuration, 
  findLongestTrack 
} from '../services/stream-processing.js';

/**
 * Clase simple para ejecutar tests
 */
class TestRunner {
  constructor() {
    this.tests = [];
    this.passed = 0;
    this.failed = 0;
  }

  addTest(name, testFunction) {
    this.tests.push({ name, testFunction });
  }

  async runAll() {
    console.log('=== EJECUTANDO TESTS FUNCIONALES ===\n');
    
    for (const test of this.tests) {
      try {
        console.log(`Ejecutando: ${test.name}`);
        await test.testFunction();
        console.log(`PASÓ: ${test.name}\n`);
        this.passed++;
      } catch (error) {
        console.log(`FALLÓ: ${test.name}`);
        console.log(`   Error: ${error.message}\n`);
        this.failed++;
      }
    }

    this.printSummary();
    return this.failed === 0;
  }

  printSummary() {
    console.log('=== RESUMEN DE TESTS ===');
    console.log(`Tests ejecutados: ${this.tests.length}`);
    console.log(`Pasaron: ${this.passed}`);
    console.log(`Fallaron: ${this.failed}`);
    
    if (this.failed === 0) {
      console.log('TODOS LOS TESTS PASARON');
    } else {
      console.log('ALGUNOS TESTS FALLARON');
    }
  }

  assert(condition, message) {
    if (!condition) {
      throw new Error(message);
    }
  }

  assertEquals(actual, expected, message) {
    if (actual !== expected) {
      throw new Error(`${message}. Esperado: ${expected}, Actual: ${actual}`);
    }
  }

  assertApproximately(actual, expected, tolerance, message) {
    if (Math.abs(actual - expected) > tolerance) {
      throw new Error(`${message}. Esperado: ~${expected}, Actual: ${actual}`);
    }
  }
}

// Crear instancia del runner de tests
const testRunner = new TestRunner();

// Tests para el enfoque tradicional
testRunner.addTest('Traditional - Álbum existente con tracks', () => {
  const duration = getDurationOfAlbumWithNameTraditional("Thriller");
  testRunner.assertApproximately(duration, 40.17, 0.1, 'Duración de Thriller incorrecta');
});

testRunner.addTest('Traditional - Álbum existente sin tracks', () => {
  const duration = getDurationOfAlbumWithNameTraditional("Back in Black");
  testRunner.assertEquals(duration, 0, 'Debe devolver 0 para álbum sin tracks');
});

testRunner.addTest('Traditional - Álbum inexistente', () => {
  const duration = getDurationOfAlbumWithNameTraditional("Inexistent Album");
  testRunner.assertEquals(duration, 0, 'Debe devolver 0 para álbum inexistente');
});

// Tests para el enfoque moderno
testRunner.addTest('Modern - Álbum existente con tracks', () => {
  const duration = getDurationOfAlbumWithNameModernCompact("Thriller");
  testRunner.assertApproximately(duration, 40.17, 0.1, 'Duración de Thriller incorrecta');
});

testRunner.addTest('Modern - Álbum existente sin tracks', () => {
  const duration = getDurationOfAlbumWithNameModernCompact("Back in Black");
  testRunner.assertEquals(duration, 0, 'Debe devolver 0 para álbum sin tracks');
});

testRunner.addTest('Modern - Álbum inexistente', () => {
  const duration = getDurationOfAlbumWithNameModernCompact("Inexistent Album");
  testRunner.assertEquals(duration, 0, 'Debe devolver 0 para álbum inexistente');
});

// Tests para el enfoque Optional
testRunner.addTest('Optional - Álbum existente con tracks', () => {
  const duration = getDurationOfAlbumWithNameOptional("Thriller");
  testRunner.assertApproximately(duration, 40.17, 0.1, 'Duración de Thriller incorrecta');
});

testRunner.addTest('Optional - Álbum existente sin tracks', () => {
  const duration = getDurationOfAlbumWithNameOptional("Back in Black");
  testRunner.assertEquals(duration, 0, 'Debe devolver 0 para álbum sin tracks');
});

testRunner.addTest('Optional - Álbum inexistente', () => {
  const duration = getDurationOfAlbumWithNameOptional("Inexistent Album");
  testRunner.assertEquals(duration, 0, 'Debe devolver 0 para álbum inexistente');
});

// Tests para procesamiento de streams
testRunner.addTest('Stream Processing - Álbum más antiguo', () => {
  const oldestAlbum = findOldestAlbum();
  testRunner.assert(oldestAlbum !== null, 'Debe encontrar un álbum más antiguo');
  testRunner.assertEquals(oldestAlbum.name, "Abbey Road", 'El álbum más antiguo debe ser Abbey Road');
  testRunner.assertEquals(oldestAlbum.year, 1969, 'El año debe ser 1969');
});

testRunner.addTest('Stream Processing - Duración total', () => {
  const totalDuration = calculateTotalDuration();
  testRunner.assert(totalDuration > 0, 'La duración total debe ser mayor que 0');
  testRunner.assertApproximately(totalDuration, 116.58, 1.0, 'Duración total incorrecta');
});

testRunner.addTest('Stream Processing - Pista más larga', () => {
  const longestTrack = findLongestTrack();
  testRunner.assert(longestTrack !== null, 'Debe encontrar una pista más larga');
  testRunner.assert(longestTrack.title.length > 0, 'La pista debe tener título');
  testRunner.assert(longestTrack.duration > 0, 'La pista debe tener duración positiva');
});

// Ejecutar todos los tests
testRunner.runAll().then(success => {
  process.exit(success ? 0 : 1);
}).catch(error => {
  console.error('Error ejecutando tests:', error);
  process.exit(1);
});