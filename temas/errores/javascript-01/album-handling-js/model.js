// model.js - Clases base para el modelo de datos

/**
 * Representa una pista de música
 */
class Track {
  constructor(title, duration) {
    this.title = title;
    this.duration = duration;
  }

  getTitle() {
    return this.title;
  }

  getDuration() {
    return this.duration;
  }
}

/**
 * Representa un álbum musical
 */
class Album {
  constructor(name, artist, year) {
    this.name = name;
    this.artist = artist;
    this.year = year;
  }

  getName() {
    return this.name;
  }

  getArtist() {
    return this.artist;
  }

  getYear() {
    return this.year;
  }
}

// Exportar las clases para poder usarlas en otros archivos
export { Track, Album };