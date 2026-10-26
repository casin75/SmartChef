import { Ingrediente } from './ingrediente';

export type Ubicacion = 'FRIGORIFICO' | 'DESPENSA' | 'CONGELADOR';

// Igual que la entidad Despensa del backend: un ingrediente que tiene el usuario en casa
export interface Despensa {
  ingrediente: Ingrediente;
  cantidad: number;
  caducidad?: string; // fecha ISO: '2026-09-28'
  ubicacion: Ubicacion; // solo en la maqueta: el backend todavía no lo guarda
}
