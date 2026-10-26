import { Component, computed, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IonCard, IonIcon } from '@ionic/angular';
import { Receta } from '../../modelos/receta';
import { ChipDificultadComponent } from '../chip-dificultad/chip-dificultad.component';
import { ChipEtiquetaComponent } from '../chip-etiqueta/chip-etiqueta.component';

@Component({
  selector: 'app-tarjeta-receta',
  templateUrl: './tarjeta-receta.component.html',
  styleUrls: ['./tarjeta-receta.component.scss'],
  imports: [IonCard, IonIcon, RouterLink, ChipDificultadComponent, ChipEtiquetaComponent],
})
export class TarjetaRecetaComponent {
  receta = input.required<Receta>(); // la receta que tiene que pintar
  mini = input(false); // versión pequeña para el inicio: solo foto, nombre y tiempo
  seccion = input<'recetas' | 'inicio' | 'favoritos'>('recetas'); // la pestaña desde la que se abre

  // La ficha se abre dentro de la pestaña en la que estamos, para que "atrás" vuelva aquí
  enlace = computed(() => this.seccion() === 'recetas'
    ? ['/app/recetas', this.receta().id]
    : ['/app', this.seccion(), 'receta', this.receta().id]);
}
