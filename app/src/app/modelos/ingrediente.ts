// Igual que la entidad Ingrediente del backend
export interface Ingrediente {
  id: number;
  nombre: string;
  unidadMedida: number; // en el backend es un Integer: ver UNIDADES
  kcal?: number; // por cada 100 g / 100 ml / unidad
  precio?: number; // € por la misma cantidad que kcal
  icono?: string; // solo en la maqueta: emoji para pintar la fila
}

// Qué significa cada número de unidadMedida
export const UNIDADES: Record<number, string> = {
  0: 'g',
  1: 'ml',
  2: 'uds',
  3: 'kg',
  4: 'l',
};
