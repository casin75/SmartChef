import { Component, input } from '@angular/core';
import { IonIcon } from '@ionic/angular';

// Lo que se ve cuando una lista no tiene nada. El texto y los botones los pone cada página.
@Component({
  selector: 'app-estado-vacio',
  templateUrl: './estado-vacio.component.html',
  styleUrls: ['./estado-vacio.component.scss'],
  imports: [IonIcon],
})
export class EstadoVacioComponent {
  icono = input('restaurant-outline');
  titulo = input.required<string>();
}
