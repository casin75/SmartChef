import { Component, computed, input } from '@angular/core';
import { Despensa } from '../../modelos/despensa';
import { UNIDADES } from '../../modelos/ingrediente';
import { diasHasta, fechaCorta } from '../../utilidades/fechas';

// Una fila de "Mi Cocina": icono, nombre, cantidad y cuándo caduca
@Component({
  selector: 'app-fila-producto',
  templateUrl: './fila-producto.component.html',
  styleUrls: ['./fila-producto.component.scss'],
})
export class FilaProductoComponent {
  producto = input.required<Despensa>();

  unidad = computed(() => UNIDADES[this.producto().ingrediente.unidadMedida]);

  // Cuántos días le quedan (o null si no caduca)
  dias = computed(() => {
    const caducidad = this.producto().caducidad;
    return caducidad ? diasHasta(caducidad) : null;
  });

  fecha = computed(() => {
    const caducidad = this.producto().caducidad;
    return caducidad ? fechaCorta(caducidad) : '';
  });
}
