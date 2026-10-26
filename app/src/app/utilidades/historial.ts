import { HISTORIAL } from '../datos/historial';
import { RECETAS } from '../datos/recetas';
import { Receta } from '../modelos/receta';
import { diaSemana, fechaCorta } from './fechas';

export interface DiaCocinado {
  fecha: string; // '2026-09-16'
  dia: string; // 'Lun'
  fechaCorta: string; // '16 sep'
  recetas: Receta[];
  kcal: number;
}

/** Agrupa el historial por días (solo lo cocinado, tipo 0) */
export function diasCocinados(): DiaCocinado[] {
  const dias = new Map<string, Receta[]>();
  for (const h of HISTORIAL.filter(h => h.tipo === 0)) {
    const fecha = h.fecha.slice(0, 10);
    const receta = RECETAS.find(r => r.id === h.receta);
    if (receta) dias.set(fecha, [...(dias.get(fecha) ?? []), receta]);
  }
  return [...dias.entries()].map(([fecha, recetas]) => ({
    fecha,
    dia: diaSemana(fecha),
    fechaCorta: fechaCorta(fecha),
    recetas,
    kcal: recetas.reduce((total, r) => total + r.kcal, 0),
  }));
}
