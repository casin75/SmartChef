// Igual que la entidad Notificacion del backend
export interface Notificacion {
  id: number;
  tipo: number; // 0 caducidad · 1 receta · 2 lista de la compra · 3 ahorro
  texto: string;
  hace?: string; // solo en la maqueta: "hace 2 h"
}
