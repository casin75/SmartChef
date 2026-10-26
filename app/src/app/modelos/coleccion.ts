// Igual que la entidad Coleccion del backend. Guardamos los ids de sus recetas.
export interface Coleccion {
  id: number;
  nombre: string;
  icono: string; // solo en la maqueta: emoji que acompaña al nombre
  recetas: number[];
}
