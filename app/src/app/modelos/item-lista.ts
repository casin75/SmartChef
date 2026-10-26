import { Ingrediente } from './ingrediente';

// Un producto de la lista de la compra (Usuario.listaCompra en el backend).
// La cantidad, la categoría y el marcado son de la maqueta: el backend solo guarda el ingrediente.
export interface ItemLista {
  ingrediente: Ingrediente;
  cantidad: number;
  categoria: string;
  marcado: boolean;
}
