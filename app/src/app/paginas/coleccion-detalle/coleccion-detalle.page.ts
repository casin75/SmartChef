import { Component, input } from '@angular/core';
import { IonHeader, IonToolbar, IonTitle, IonContent, IonButtons, IonBackButton } from '@ionic/angular';

@Component({
  selector: 'app-coleccion-detalle',
  templateUrl: './coleccion-detalle.page.html',
  styleUrls: ['./coleccion-detalle.page.scss'],
  imports: [IonHeader, IonToolbar, IonTitle, IonContent, IonButtons, IonBackButton],
})
export class ColeccionDetallePage {
  id = input<string>(); // recibe el ":id" de la dirección
}
