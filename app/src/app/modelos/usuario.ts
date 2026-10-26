// La parte de Usuario que se ve en la app (la contraseña nunca llega al móvil)
export interface Usuario {
  id: number;
  nombre: string;
  rol: number; // 0 usuario · 1 administrador
}
