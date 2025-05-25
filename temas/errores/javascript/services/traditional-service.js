// traditional-service.js - Implementación tradicional con comprobaciones de null explícitas

import { getAlbum, getAlbumTracks } from '../album-handling-js/database.js';

/**
 * Calcula la duración de un álbum usando un enfoque tradicional con comprobaciones explícitas
 * @param {string} name - El nombre del álbum
 * @returns {number} La duración total del álbum o 0 si no existe o no tiene pistas
 */
function getDurationOfAlbumWithNameTraditional(name) {
  const album = getAlbum(name);
  if (!album) {
    return 0; // Devolvemos 0 si el álbum no existe
  }
  
  const tracks = getAlbumTracks(album.getName());
  if (!tracks) {
    return 0; // Devolvemos 0 si el álbum no tiene pistas
  }
  
  let duration = 0;
  for (const track of tracks) {
    duration += track.getDuration();
  }
  
  return duration;
}

export { getDurationOfAlbumWithNameTraditional };