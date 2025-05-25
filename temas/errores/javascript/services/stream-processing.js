// stream-processing.js - Ejemplos de procesamiento funcional con arrays

import { albumDatabase, trackDatabase } from '../album-handling-js/database.js';
import { getDurationOfAlbumWithNameModernCompact } from './modern-service.js';

/**
 * Muestra álbumes posteriores a un año determinado
 * @param {number} year - El año a partir del cual filtrar
 */
function showAlbumsAfterYear(year) {
  const albums = Object.values(albumDatabase);
  
  console.log(`Álbumes posteriores a ${year}:`);
  albums
    .filter(album => album.getYear() > year)
    .map(album => `${album.getName()} (${album.getArtist()}, ${album.getYear()})`)
    .sort()
    .forEach(album => console.log(album));
}

/**
 * Encuentra el álbum más antiguo de la colección
 * @returns {Object} El álbum más antiguo con su información
 */
function findOldestAlbum() {
  const albums = Object.values(albumDatabase);
  
  if (albums.length === 0) {
    return null;
  }
  
  const oldestAlbum = albums.reduce(
    (oldest, album) => album.getYear() < oldest.getYear() ? album : oldest, 
    albums[0]
  );
  
  return {
    name: oldestAlbum.getName(),
    artist: oldestAlbum.getArtist(),
    year: oldestAlbum.getYear()
  };
}

/**
 * Calcula la duración total de todos los álbumes
 * @returns {number} La duración total en minutos
 */
function calculateTotalDuration() {
  const albums = Object.values(albumDatabase);
  
  return albums
    .map(album => getDurationOfAlbumWithNameModernCompact(album.getName()))
    .reduce((total, duration) => total + duration, 0);
}

/**
 * Encuentra la pista más larga de toda la colección
 * @returns {Object} La pista más larga con su información
 */
function findLongestTrack() {
  const allTracks = Object.values(trackDatabase)
    .flatMap(tracks => tracks); // Aplanar el array de arrays de pistas
  
  if (allTracks.length === 0) {
    return null;
  }
  
  const longestTrack = allTracks.reduce(
    (longest, track) => track.getDuration() > longest.getDuration() ? track : longest,
    allTracks[0]
  );
  
  return {
    title: longestTrack.getTitle(),
    duration: longestTrack.getDuration()
  };
}

export {
  showAlbumsAfterYear,
  findOldestAlbum,
  calculateTotalDuration,
  findLongestTrack
};