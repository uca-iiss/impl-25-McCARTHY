// database.js - Base de datos simulada con información de álbumes y pistas

import { Album, Track } from './model.js';

// Base de datos simulada de álbumes
const albumDatabase = {
  "Thriller": new Album("Thriller", "Michael Jackson", 1982),
  "The Dark Side of the Moon": new Album("The Dark Side of the Moon", "Pink Floyd", 1973),
  "Abbey Road": new Album("Abbey Road", "The Beatles", 1969),
  "Back in Black": new Album("Back in Black", "AC/DC", 1980)
};

// Base de datos simulada de pistas para cada álbum
const trackDatabase = {
  "Thriller": [
    new Track("Wanna Be Startin' Somethin'", 6.03),
    new Track("Baby Be Mine", 4.20),
    new Track("The Girl Is Mine", 3.42),
    new Track("Thriller", 5.57),
    new Track("Beat It", 4.18),
    new Track("Billie Jean", 4.54),
    new Track("Human Nature", 4.06),
    new Track("P.Y.T. (Pretty Young Thing)", 3.58),
    new Track("The Lady in My Life", 4.59)
  ],
  "The Dark Side of the Moon": [
    new Track("Speak to Me", 1.13),
    new Track("Breathe", 2.43),
    new Track("On the Run", 3.30),
    new Track("Time", 7.06),
    new Track("The Great Gig in the Sky", 4.44),
    new Track("Money", 6.22),
    new Track("Us and Them", 7.49),
    new Track("Any Colour You Like", 3.26),
    new Track("Brain Damage", 3.46),
    new Track("Eclipse", 2.12)
  ],
  "Abbey Road": [
    new Track("Come Together", 4.19),
    new Track("Something", 3.01),
    new Track("Maxwell's Silver Hammer", 3.27),
    new Track("Oh! Darling", 3.27),
    new Track("Octopus's Garden", 2.51),
    new Track("I Want You (She's So Heavy)", 7.47),
    new Track("Here Comes the Sun", 3.05),
    new Track("Because", 2.45),
    new Track("You Never Give Me Your Money", 4.02),
    new Track("Sun King", 2.26)
  ]
  // Nota: No hay tracks para "Back in Black" a propósito para mostrar el manejo de nulls
};

// Función para obtener un álbum por nombre
function getAlbum(name) {
  return albumDatabase[name];
}

// Función para obtener las pistas de un álbum por nombre
function getAlbumTracks(albumName) {
  return trackDatabase[albumName];
}

// Función para calcular la duración total de las pistas
function getTracksDuration(tracks) {
  return tracks.reduce((total, track) => total + track.getDuration(), 0);
}

export { 
  albumDatabase, 
  trackDatabase, 
  getAlbum, 
  getAlbumTracks, 
  getTracksDuration 
};