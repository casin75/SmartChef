import { Component, input } from '@angular/core';
import { Dificultad } from '../../modelos/receta';

@Component({
  selector: 'app-chip-dificultad',
  templateUrl: './chip-dificultad.component.html',
  styleUrls: ['./chip-dificultad.component.scss'],
})
export class ChipDificultadComponent {
  dificultad = input.required<Dificultad>();
}
