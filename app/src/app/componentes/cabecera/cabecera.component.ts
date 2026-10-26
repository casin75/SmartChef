import { Component, input } from '@angular/core';
import { IonBackButton } from '@ionic/angular';

@Component({
  selector: 'app-cabecera',
  templateUrl: './cabecera.component.html',
  styleUrls: ['./cabecera.component.scss'],
  imports: [IonBackButton],
})
export class CabeceraComponent {
  titulo = input.required<string>();
  subtitulo = input<string>(); // opcional: la línea pequeña debajo del título
  atras = input<string>(); // si llega, se pinta el botón atrás con esta dirección por defecto
}
