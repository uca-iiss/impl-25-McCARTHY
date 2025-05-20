import { Usuario } from "../src/Usuario";

describe("Usuario", () => {

  it("debería lanzar error si el nombre es demasiado corto", () => {
    expect(() => new Usuario("Ana")).toThrow("La longitud mínima para 'nombre' es 5");
  });

  it("debería lanzar error si el mensaje está vacío", () => {
    const usuario = new Usuario("Carlos");
    expect(() => usuario.saludar("")).toThrow("El parámetro en la posición 0 no puede estar vacío.");
  });

  it("debería inicializar con el rol correcto", () => {
    const usuario = new Usuario("Carlos");
    expect(usuario.role).toBe("usuario");
  });

  it("debería devolver false para permisos si el rol es usuario", () => {
    const usuario = new Usuario("Carlos");
    expect(usuario.tienePermisos()).toBe(false);
  });
});
