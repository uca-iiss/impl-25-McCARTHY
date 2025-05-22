// optional.js - Implementación de la clase Optional similar a Java

/**
 * Implementación de la clase Optional para JavaScript
 * Permite un manejo más elegante de valores potencialmente nulos o indefinidos
 */
class Optional {
  constructor(value) {
    this._value = value;
  }

  /**
   * Crea un Optional con un valor no nulo
   * @param {*} value - El valor a envolver (no puede ser null ni undefined)
   * @returns {Optional} Un nuevo Optional conteniendo el valor
   * @throws {Error} Si el valor es null o undefined
   */
  static of(value) {
    if (value === null || value === undefined) {
      throw new Error("Value cannot be null or undefined");
    }
    return new Optional(value);
  }

  /**
   * Crea un Optional que puede contener un valor nulo
   * @param {*} value - El valor a envolver (puede ser null o undefined)
   * @returns {Optional} Un nuevo Optional conteniendo el valor
   */
  static ofNullable(value) {
    return new Optional(value);
  }

  /**
   * Crea un Optional vacío
   * @returns {Optional} Un Optional vacío
   */
  static empty() {
    return new Optional(null);
  }

  /**
   * Comprueba si el Optional contiene un valor
   * @returns {boolean} true si contiene un valor, false en caso contrario
   */
  isPresent() {
    return this._value !== null && this._value !== undefined;
  }

  /**
   * Obtiene el valor contenido en el Optional
   * @returns {*} El valor contenido
   * @throws {Error} Si el Optional está vacío
   */
  get() {
    if (!this.isPresent()) {
      throw new Error("No value present");
    }
    return this._value;
  }

  /**
   * Devuelve el valor si está presente, o un valor alternativo si no
   * @param {*} other - El valor alternativo a devolver si el Optional está vacío
   * @returns {*} El valor contenido o el valor alternativo
   */
  orElse(other) {
    return this.isPresent() ? this._value : other;
  }

  /**
   * Aplica una función al valor contenido si está presente
   * @param {Function} mapper - La función a aplicar al valor
   * @returns {Optional} Un nuevo Optional con el resultado de aplicar la función
   */
  map(mapper) {
    if (!this.isPresent()) {
      return Optional.empty();
    }
    const result = mapper(this._value);
    return Optional.ofNullable(result);
  }

  /**
   * Aplica una función que devuelve un Optional al valor contenido si está presente
   * @param {Function} mapper - La función a aplicar al valor que devuelve un Optional
   * @returns {Optional} El Optional resultante de aplicar la función
   */
  flatMap(mapper) {
    if (!this.isPresent()) {
      return Optional.empty();
    }
    return mapper(this._value);
  }
}

export { Optional };