import { ItemLista } from '../modelos/item-lista';
import { ing } from './ingredientes';

export const LISTA_COMPRA: ItemLista[] = [
  { ingrediente: ing(14), cantidad: 1, categoria: 'Conservas', marcado: true },
  { ingrediente: ing(15), cantidad: 500, categoria: 'Conservas', marcado: false },
  { ingrediente: ing(22), cantidad: 200, categoria: 'Salsas', marcado: false },
  { ingrediente: ing(9), cantidad: 500, categoria: 'Salsas', marcado: false },
  { ingrediente: ing(18), cantidad: 4, categoria: 'Panadería', marcado: true },
  { ingrediente: ing(17), cantidad: 500, categoria: 'Verduras', marcado: false },
  { ingrediente: ing(16), cantidad: 200, categoria: 'Verduras', marcado: false },
  { ingrediente: ing(6), cantidad: 0.5, categoria: 'Carne', marcado: false },
];

export const CATEGORIAS = ['Conservas', 'Salsas', 'Panadería', 'Verduras', 'Fruta', 'Carne', 'Pescado', 'Lácteos'];
