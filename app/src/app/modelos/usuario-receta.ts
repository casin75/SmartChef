// Igual que la entidad UsuarioReceta del backend: el historial de lo cocinado
export interface UsuarioReceta {
  id: number;
  receta: number; // id de la receta
  fecha: string; // fecha y hora ISO
  tipo: number; // 0 cocinada · 1 vista
}
