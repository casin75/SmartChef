import { DESPENSA } from '../datos/despensa';
import { Receta } from '../modelos/receta';

/** ¿Tengo este ingrediente en casa? */
export function loTengo(idIngrediente: number): boolean {
  return DESPENSA.some(p => p.ingrediente.id === idIngrediente);
}

/** Cuántos ingredientes de la receta me faltan */
export function faltan(receta: Receta): number {
  return receta.ingredientes.filter(ri => !loTengo(ri.ingrediente.id)).length;
}
