import { Component, input } from '@angular/core';

// Un número destacado con su explicación: "5 días · Días cocinando"
@Component({
  selector: 'app-tarjeta-dato',
  templateUrl: './tarjeta-dato.component.html',
  styleUrls: ['./tarjeta-dato.component.scss'],
})
export class TarjetaDatoComponent {
  icono = input.required<string>(); // un emoji
  valor = input.required<string | number>();
  etiqueta = input.required<string>();
}
