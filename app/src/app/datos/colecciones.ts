import { Coleccion } from '../modelos/coleccion';

export const COLECCIONES: Coleccion[] = [
  { id: 1, nombre: 'Rápidas entre semana', icono: '⚡', recetas: [1, 2, 3, 8, 5] },
  { id: 2, nombre: 'Fin de semana especial', icono: '🥂', recetas: [4, 7, 5] },
  { id: 3, nombre: 'Saludables', icono: '🥗', recetas: [2, 3, 6, 9, 5, 8, 1, 4] },
  { id: 4, nombre: 'Para probar algún día', icono: '📌', recetas: [] },
];

// Las últimas recetas guardadas en favoritos, de la más reciente a la más antigua
export const FAVORITAS_RECIENTES = [2, 1, 6, 4];
