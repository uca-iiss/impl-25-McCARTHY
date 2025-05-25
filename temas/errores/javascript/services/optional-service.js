// optional-service.js - Implementación usando la clase Optional

import { getAlbum, getAlbumTracks, getTracksDuration } from '../album-handling-js/database.js';
import { Optional } from './optional.js';

/**
 * Obtiene un álbum por nombre devolviendo un Optional
 * @param {string} name - El nombre del álbum
 * @returns {Optional} Un Optional que contiene el álbum o está vacío si no existe
 */
function getAlbumOptional(name) {
  return Optional.ofNullable(getAlbum(name));
}

/**
 * Obtiene las pistas de un álbum por nombre devolviendo un Optional
 * @param {string} albumName - El nombre del álbum
 * @returns {Optional} Un Optional que contiene las pistas o está vacío si no existen
 */
function getAlbumTracksOptional(albumName) {
  return Optional.ofNullable(getAlbumTracks(albumName));
}

/**
 * Calcula la duración de un álbum usando Optional para manejo de valores nulos
 * @param {string} name - El nombre del álbum
 * @returns {number} La duración total del álbum o 0 si no existe o no tiene pistas
 */
function getDurationOfAlbumWithNameOptional(name) {
  return getAlbumOptional(name)
    .flatMap(album => getAlbumTracksOptional(album.getName()))
    .map(tracks => getTracksDuration(tracks))
    .orElse(0);
}

export { 
  getAlbumOptional, 
  getAlbumTracksOptional, 
  getDurationOfAlbumWithNameOptional 
};