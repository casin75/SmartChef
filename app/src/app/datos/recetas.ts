import { Receta } from '../modelos/receta';
import { ing } from './ingredientes';

export const RECETAS: Receta[] = [
  {
    id: 1, nombre: 'Pasta al pesto con tomates cherry', dificultad: 1, tiempo: 20, raciones: 2,
    kcal: 540, valoracion: 4.9, etiquetas: ['VEGETARIANA', 'RAPIDA', 'TEMPORADA'],
    descripcion: 'Un clásico de diario: pasta al dente, pesto casero y tomates cherry asados para darle un punto dulce.',
    fotos: [{ id: 1, url: 'assets/fotos/pasta-pesto.jpg' }],
    ingredientes: [
      { ingrediente: ing(7), cantidad: 200 }, { ingrediente: ing(9), cantidad: 60 },
      { ingrediente: ing(8), cantidad: 150 }, { ingrediente: ing(26), cantidad: 20 },
    ],
    pasos: [
      { id: 1, numero: 1, descripcion: 'Cuece la pasta en agua con sal el tiempo que indique el paquete.' },
      { id: 2, numero: 2, descripcion: 'Mientras, saltea los tomates cherry partidos por la mitad con un poco de aceite.' },
      { id: 3, numero: 3, descripcion: 'Escurre la pasta guardando medio vaso del agua de cocción.' },
      { id: 4, numero: 4, descripcion: 'Mezcla la pasta con el pesto y un chorrito del agua reservada. Añade los tomates y el parmesano.' },
    ],
  },
  {
    id: 2, nombre: 'Bowl de frutas y verduras tropicales', dificultad: 1, tiempo: 15, raciones: 1,
    kcal: 380, valoracion: 4.8, etiquetas: ['VEGETARIANA', 'RAPIDA', 'SIN_GLUTEN'],
    descripcion: 'Fresco, colorido y listo en un cuarto de hora. Perfecto para aprovechar la fruta madura.',
    fotos: [{ id: 2, url: 'assets/fotos/bowl-frutas.jpg' }],
    ingredientes: [
      { ingrediente: ing(19), cantidad: 1 }, { ingrediente: ing(20), cantidad: 120 },
      { ingrediente: ing(4), cantidad: 1 }, { ingrediente: ing(24), cantidad: 40 },
      { ingrediente: ing(21), cantidad: 1 },
    ],
    pasos: [
      { id: 5, numero: 1, descripcion: 'Pela y corta en dados el mango, la piña y el aguacate.' },
      { id: 6, numero: 2, descripcion: 'Pon una base de espinacas en un bol y coloca la fruta por encima.' },
      { id: 7, numero: 3, descripcion: 'Aliña con zumo de limón y un hilo de aceite de oliva.' },
    ],
  },
  {
    id: 3, nombre: 'Ensalada mixta', dificultad: 1, tiempo: 10, raciones: 2,
    kcal: 210, valoracion: 4.5, etiquetas: ['RAPIDA', 'SIN_GLUTEN'],
    fotos: [{ id: 3, url: 'assets/fotos/ensalada-mixta.jpg' }],
    ingredientes: [
      { ingrediente: ing(17), cantidad: 200 }, { ingrediente: ing(8), cantidad: 100 },
      { ingrediente: ing(16), cantidad: 80 }, { ingrediente: ing(1), cantidad: 2 },
      { ingrediente: ing(11), cantidad: 1 },
    ],
    pasos: [
      { id: 8, numero: 1, descripcion: 'Cuece los huevos 10 minutos y enfríalos en agua.' },
      { id: 9, numero: 2, descripcion: 'Lava y corta la lechuga, los tomates, la zanahoria y la cebolla.' },
      { id: 10, numero: 3, descripcion: 'Añade los huevos en cuartos y aliña al gusto.' },
    ],
  },
  {
    id: 4, nombre: 'Tortilla de patatas', dificultad: 2, tiempo: 40, raciones: 4,
    kcal: 320, valoracion: 4.7, etiquetas: ['VEGETARIANA', 'SIN_GLUTEN'],
    descripcion: 'Jugosa por dentro y dorada por fuera. Con o sin cebolla, el eterno debate.',
    fotos: [{ id: 4, url: 'assets/fotos/tortilla-patatas.jpg' }],
    ingredientes: [
      { ingrediente: ing(10), cantidad: 0.6 }, { ingrediente: ing(1), cantidad: 6 },
      { ingrediente: ing(11), cantidad: 1 }, { ingrediente: ing(23), cantidad: 250 },
    ],
    pasos: [
      { id: 11, numero: 1, descripcion: 'Pela y corta las patatas en láminas finas y la cebolla en juliana.' },
      { id: 12, numero: 2, descripcion: 'Confítalas en abundante aceite a fuego medio unos 20 minutos.' },
      { id: 13, numero: 3, descripcion: 'Bate los huevos, escurre las patatas y mézclalo todo con sal.' },
      { id: 14, numero: 4, descripcion: 'Cuaja la tortilla en una sartén caliente y dale la vuelta con un plato.' },
    ],
  },
  {
    id: 5, nombre: 'Salmón al horno con limón', dificultad: 2, tiempo: 25, raciones: 2,
    kcal: 460, valoracion: 4.6, etiquetas: ['PROTEINAS', 'SIN_GLUTEN'],
    fotos: [{ id: 5, url: 'assets/fotos/salmon-horno.jpg' }],
    ingredientes: [
      { ingrediente: ing(3), cantidad: 200 }, { ingrediente: ing(21), cantidad: 1 },
      { ingrediente: ing(10), cantidad: 0.3 }, { ingrediente: ing(23), cantidad: 20 },
    ],
    pasos: [
      { id: 15, numero: 1, descripcion: 'Precalienta el horno a 200 °C.' },
      { id: 16, numero: 2, descripcion: 'Coloca las patatas en rodajas en la bandeja y hornéalas 10 minutos.' },
      { id: 17, numero: 3, descripcion: 'Añade el salmón con rodajas de limón encima y hornea 12 minutos más.' },
    ],
  },
  {
    id: 6, nombre: 'Crema de calabaza', dificultad: 1, tiempo: 35, raciones: 4,
    kcal: 180, etiquetas: ['VEGETARIANA', 'TEMPORADA', 'SIN_GLUTEN'],
    descripcion: 'Suave y reconfortante. Aguanta perfectamente tres días en la nevera.',
    fotos: [{ id: 6, url: 'assets/fotos/crema-calabaza.jpg' }],
    ingredientes: [
      { ingrediente: ing(12), cantidad: 800 }, { ingrediente: ing(11), cantidad: 1 },
      { ingrediente: ing(10), cantidad: 0.2 }, { ingrediente: ing(2), cantidad: 200 },
    ],
    pasos: [
      { id: 18, numero: 1, descripcion: 'Pocha la cebolla en una olla con un poco de aceite.' },
      { id: 19, numero: 2, descripcion: 'Añade la calabaza y la patata en dados, cubre con agua y cuece 20 minutos.' },
      { id: 20, numero: 3, descripcion: 'Tritura con la leche hasta que quede fina y rectifica de sal.' },
    ],
  },
  {
    id: 7, nombre: 'Pollo al curry con arroz basmati y leche de coco', dificultad: 3, tiempo: 50, raciones: 3,
    kcal: 610, valoracion: 4.4, etiquetas: ['PROTEINAS'],
    descripcion: 'Un curry suave y cremoso. El nombre largo es a propósito: así comprobamos que las tarjetas no se rompen.',
    fotos: [{ id: 7, url: 'assets/fotos/pollo-curry.jpg' }],
    ingredientes: [
      { ingrediente: ing(6), cantidad: 0.5 }, { ingrediente: ing(14), cantidad: 400 },
      { ingrediente: ing(13), cantidad: 250 }, { ingrediente: ing(25), cantidad: 15 },
      { ingrediente: ing(11), cantidad: 1 },
    ],
    pasos: [
      { id: 21, numero: 1, descripcion: 'Corta el pollo en dados y dóralo en una cazuela.' },
      { id: 22, numero: 2, descripcion: 'Añade la cebolla picada y el curry, y rehoga 2 minutos.' },
      { id: 23, numero: 3, descripcion: 'Vierte la leche de coco y cuece a fuego lento 20 minutos.' },
      { id: 24, numero: 4, descripcion: 'Mientras, cuece el arroz basmati y sírvelo junto al curry.' },
    ],
  },
  {
    id: 8, nombre: 'Tostada de aguacate', dificultad: 1, tiempo: 5, raciones: 1,
    kcal: 350, valoracion: 4.2, etiquetas: ['VEGETARIANA', 'RAPIDA'],
    fotos: [{ id: 8, url: 'assets/fotos/tostada-aguacate.jpg' }],
    ingredientes: [
      { ingrediente: ing(18), cantidad: 1 }, { ingrediente: ing(4), cantidad: 1 },
      { ingrediente: ing(1), cantidad: 1 },
    ],
    pasos: [
      { id: 25, numero: 1, descripcion: 'Tuesta el pan y machaca el aguacate con sal y limón.' },
      { id: 26, numero: 2, descripcion: 'Unta el aguacate y corona con un huevo a la plancha.' },
    ],
  },
  {
    id: 9, nombre: 'Lentejas con verduras', dificultad: 2, tiempo: 45, raciones: 4,
    kcal: 390, etiquetas: ['VEGETARIANA', 'PROTEINAS', 'TEMPORADA'],
    fotos: [{ id: 9, url: 'assets/fotos/lentejas-verduras.jpg' }],
    ingredientes: [
      { ingrediente: ing(15), cantidad: 400 }, { ingrediente: ing(16), cantidad: 150 },
      { ingrediente: ing(10), cantidad: 0.3 }, { ingrediente: ing(11), cantidad: 1 },
    ],
    pasos: [
      { id: 27, numero: 1, descripcion: 'Sofríe la cebolla y la zanahoria picadas.' },
      { id: 28, numero: 2, descripcion: 'Añade la patata, las lentejas y caldo hasta cubrir. Cuece 25 minutos.' },
    ],
  },
];

// Las recetas "para ti hoy" que salen en el inicio
export const RECETAS_DEL_DIA = [1, 3];
