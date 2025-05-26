// modern-service.js - Implementación usando características modernas de JavaScript

import { getAlbum, getAlbumTracks } from '../album-handling-js/database.js';

/**
 * Calcula la duración de un álbum usando encadenamiento opcional y operador de coalescencia nula
 * @param {string} name - El nombre del álbum
 * @returns {number} La duración total del álbum o 0 si no existe o no tiene pistas
 */
function getDurationOfAlbumWithNameModern(name) {
  const album = getAlbum(name);
  const tracks = album ? getAlbumTracks(album.getName()) : null;
  
  // Usar operador de coalescencia nula para manejar casos donde tracks es null/undefined
  return tracks?.reduce((total, track) => total + track.getDuration(), 0) ?? 0;
}

/**
 * Versión más compacta que usa encadenamiento opcional
 * @param {string} name - El nombre del álbum
 * @returns {number} La duración total del álbum o 0 si no existe o no tiene pistas
 */
function getDurationOfAlbumWithNameModernCompact(name) {
  // El encadenamiento opcional (?.) evita errores cuando algún valor es null/undefined
  // El operador de coalescencia nula (??) proporciona un valor por defecto
  return getAlbumTracks(getAlbum(name)?.getName())?.reduce(
    (total, track) => total + track.getDuration(), 0
  ) ?? 0;
}

export { 
  getDurationOfAlbumWithNameModern, 
  getDurationOfAlbumWithNameModernCompact 
};