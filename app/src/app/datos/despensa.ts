import { Despensa } from '../modelos/despensa';
import { ing } from './ingredientes';

export const DESPENSA: Despensa[] = [
  { ingrediente: ing(1), cantidad: 6, caducidad: '2026-09-28', ubicacion: 'FRIGORIFICO' },
  { ingrediente: ing(2), cantidad: 800, caducidad: '2026-09-20', ubicacion: 'FRIGORIFICO' },
  { ingrediente: ing(3), cantidad: 200, caducidad: '2026-09-21', ubicacion: 'FRIGORIFICO' },
  { ingrediente: ing(4), cantidad: 3, caducidad: '2026-09-28', ubicacion: 'FRIGORIFICO' },
  { ingrediente: ing(5), cantidad: 300, caducidad: '2026-09-24', ubicacion: 'FRIGORIFICO' },
  { ingrediente: ing(6), cantidad: 1, caducidad: '2026-09-20', ubicacion: 'FRIGORIFICO' },
  { ingrediente: ing(7), cantidad: 500, caducidad: '2027-03-01', ubicacion: 'DESPENSA' },
  { ingrediente: ing(13), cantidad: 1000, caducidad: '2027-06-15', ubicacion: 'DESPENSA' },
  { ingrediente: ing(10), cantidad: 2, ubicacion: 'DESPENSA' },
  { ingrediente: ing(11), cantidad: 4, ubicacion: 'DESPENSA' },
  { ingrediente: ing(23), cantidad: 750, caducidad: '2027-01-10', ubicacion: 'DESPENSA' },
  { ingrediente: ing(24), cantidad: 450, caducidad: '2027-02-01', ubicacion: 'CONGELADOR' },
];
