// Decorador de propiedad para validar longitud mínima
function MinLength(length: number) {
  return function (target: any, propertyKey: string) {
    let value: string;

    const getter = () => value;
    const setter = (newValue: string) => {
      if (newValue.length < length) {
        throw new Error(`La longitud mínima para '${propertyKey}' es ${length}`);
      }
      value = newValue;
    };

    Object.defineProperty(target, propertyKey, {
      get: getter,
      set: setter,
      enumerable: true,
      configurable: true,
    });
  };
}

// Decorador de método para medir tiempo de ejecución
function LogExecutionTime(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
  const originalMethod = descriptor.value;

  descriptor.value = function (...args: any[]) {
    const start = Date.now();
    const result = originalMethod.apply(this, args);
    const end = Date.now();
    console.log(`El método ${propertyKey} se ejecutó en ${end - start}ms`);
    return result;
  };

  return descriptor;
}

// Decorador para validar que los parámetros no estén vacíos
function NotEmpty(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
  const originalMethod = descriptor.value;

  descriptor.value = function (...args: any[]) {
    for (let i = 0; i < args.length; i++) {
      const paramValue = args[i];
      if (typeof paramValue === "string" && paramValue.trim() === "") {
        throw new Error(`El parámetro en la posición ${i} no puede estar vacío.`);
      }
    }
    return originalMethod.apply(this, args);
  };

  return descriptor;
}



// Decorador de clase para inicializar valores por defecto
function DefaultRole(role: string) {
  return function (constructor: Function) {
    constructor.prototype.role = role;
  };
}

@DefaultRole("usuario")
export class Usuario {
  @MinLength(5)
  nombre: string;

  role!: string;

  constructor(nombre: string) {
    this.nombre = nombre;
  }

  @LogExecutionTime
  @NotEmpty
  saludar(mensaje: string): string {
    return `Hola, ${this.nombre}! ${mensaje}`;
  }

  tienePermisos(): boolean {
    return this.role === "admin";
  }
}
