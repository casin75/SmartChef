import { HOY } from '../datos/hoy';

const MESES = ['ene', 'feb', 'mar', 'abr', 'may', 'jun', 'jul', 'ago', 'sep', 'oct', 'nov', 'dic'];
const DIAS = ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'];

/** '2026-09-28' → '28 sep' */
export function fechaCorta(iso: string): string {
  const d = new Date(iso.slice(0, 10) + 'T12:00');
  return `${d.getDate()} ${MESES[d.getMonth()]}`;
}

/** '2026-09-28' → 'Lun' */
export function diaSemana(iso: string): string {
  return DIAS[new Date(iso.slice(0, 10) + 'T12:00').getDay()];
}

/** Días que faltan desde HOY hasta la fecha (negativo si ya pasó) */
export function diasHasta(iso: string): number {
  const ms = new Date(iso.slice(0, 10) + 'T12:00').getTime() - new Date(HOY + 'T12:00').getTime();
  return Math.round(ms / 86_400_000);
}
