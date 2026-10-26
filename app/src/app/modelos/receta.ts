import { Ingrediente } from './ingrediente';

// Igual que la entidad Paso del backend (sin la referencia a la receta)
export interface Paso {
  id: number;
  numero: number;
  descripcion: string;
}

// Igual que la entidad Foto del backend
export interface Foto {
  id: number;
  url: string;
}

// La tabla intermedia receta_ingrediente: qué lleva y cuánto
export interface RecetaIngrediente {
  ingrediente: Ingrediente;
  cantidad: number;
}

export type Dificultad = 1 | 2 | 3; // 1 fácil · 2 media · 3 difícil

// Igual que la entidad Receta del backend
export interface Receta {
  id: number;
  nombre: string;
  descripcion?: string;
  dificultad: Dificultad;
  tiempo: number; // minutos
  raciones: number;
  pasos: Paso[];
  ingredientes: RecetaIngrediente[];
  fotos: Foto[];
  // ── Solo en la maqueta: el backend todavía no tiene estos campos ──
  kcal: number; // por ración
  valoracion?: number; // de 0 a 5
  etiquetas: Etiqueta[];
}

export type Etiqueta = 'VEGETARIANA' | 'RAPIDA' | 'TEMPORADA' | 'PROTEINAS' | 'SIN_GLUTEN';
