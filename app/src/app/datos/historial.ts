import { UsuarioReceta } from '../modelos/usuario-receta';

// Lo cocinado en la semana del 16 al 22 de septiembre de 2026
export const HISTORIAL: UsuarioReceta[] = [
  { id: 1, receta: 8, fecha: '2026-09-16T08:30', tipo: 0 },
  { id: 2, receta: 4, fecha: '2026-09-16T21:00', tipo: 0 },
  { id: 3, receta: 2, fecha: '2026-09-17T13:30', tipo: 0 },
  { id: 4, receta: 7, fecha: '2026-09-17T21:15', tipo: 0 },
  { id: 5, receta: 1, fecha: '2026-09-18T14:00', tipo: 0 },
  { id: 6, receta: 3, fecha: '2026-09-19T13:45', tipo: 0 },
  { id: 7, receta: 5, fecha: '2026-09-19T21:30', tipo: 0 },
  { id: 8, receta: 6, fecha: '2026-09-20T14:10', tipo: 0 },
  { id: 9, receta: 9, fecha: '2026-09-20T21:00', tipo: 0 },
];

export const AHORRO_SEMANA = 38; // € ahorrados por no tirar comida (lo calculará el backend)
