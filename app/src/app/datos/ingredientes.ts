import { Ingrediente } from '../modelos/ingrediente';

// unidadMedida: 0 g · 1 ml · 2 uds · 3 kg · 4 l
export const INGREDIENTES: Ingrediente[] = [
  { id: 1, nombre: 'Huevos', unidadMedida: 2, kcal: 78, precio: 0.25, icono: '🥚' },
  { id: 2, nombre: 'Leche entera', unidadMedida: 1, kcal: 65, precio: 0.1, icono: '🥛' },
  { id: 3, nombre: 'Salmón fresco', unidadMedida: 0, kcal: 208, precio: 2.4, icono: '🐟' },
  { id: 4, nombre: 'Aguacate', unidadMedida: 2, kcal: 240, precio: 0.9, icono: '🥑' },
  { id: 5, nombre: 'Fresas', unidadMedida: 0, kcal: 32, precio: 0.6, icono: '🍓' },
  { id: 6, nombre: 'Lomo de pollo', unidadMedida: 3, kcal: 165, precio: 0.8, icono: '🍗' },
  { id: 7, nombre: 'Pasta', unidadMedida: 0, kcal: 371, precio: 0.15, icono: '🍝' },
  { id: 8, nombre: 'Tomates cherry', unidadMedida: 0, kcal: 18, precio: 0.4, icono: '🍅' },
  { id: 9, nombre: 'Pesto', unidadMedida: 0, kcal: 418, precio: 1.2, icono: '🌿' },
  { id: 10, nombre: 'Patatas', unidadMedida: 3, kcal: 77, precio: 0.12, icono: '🥔' },
  { id: 11, nombre: 'Cebolla', unidadMedida: 2, kcal: 40, precio: 0.2, icono: '🧅' },
  { id: 12, nombre: 'Calabaza', unidadMedida: 0, kcal: 26, precio: 0.2, icono: '🎃' },
  { id: 13, nombre: 'Arroz', unidadMedida: 0, kcal: 130, precio: 0.1, icono: '🍚' },
  { id: 14, nombre: 'Leche de coco', unidadMedida: 1, kcal: 197, precio: 0.4, icono: '🥥' },
  { id: 15, nombre: 'Lentejas cocidas', unidadMedida: 0, kcal: 116, precio: 0.25, icono: '🫘' },
  { id: 16, nombre: 'Zanahorias', unidadMedida: 0, kcal: 41, precio: 0.15, icono: '🥕' },
  { id: 17, nombre: 'Lechuga', unidadMedida: 0, kcal: 15, precio: 0.3, icono: '🥬' },
  { id: 18, nombre: 'Pan de masa madre', unidadMedida: 2, kcal: 250, precio: 2.1, icono: '🍞' },
  { id: 19, nombre: 'Mango', unidadMedida: 2, kcal: 60, precio: 1.1, icono: '🥭' },
  { id: 20, nombre: 'Piña', unidadMedida: 0, kcal: 50, precio: 0.3, icono: '🍍' },
  { id: 21, nombre: 'Limón', unidadMedida: 2, kcal: 29, precio: 0.3, icono: '🍋' },
  { id: 22, nombre: 'Mayonesa', unidadMedida: 0, kcal: 680, precio: 0.6, icono: '🫙' },
  { id: 23, nombre: 'Aceite de oliva', unidadMedida: 1, kcal: 884, precio: 0.9, icono: '🫒' },
  { id: 24, nombre: 'Espinacas', unidadMedida: 0, kcal: 23, precio: 0.5, icono: '🥬' },
  { id: 25, nombre: 'Curry en polvo', unidadMedida: 0, kcal: 325, precio: 3, icono: '🌶️' },
  { id: 26, nombre: 'Queso parmesano', unidadMedida: 0, kcal: 431, precio: 2.2, icono: '🧀' },
];

// Para buscar un ingrediente por su id al escribir los datos
export const ing = (id: number) => INGREDIENTES.find(i => i.id === id)!;
