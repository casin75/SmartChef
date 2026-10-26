import { Component } from '@angular/core';
import { IonContent, IonList, IonItem, IonItemSliding, IonItemOptions, IonItemOption, IonIcon, IonLabel,
  IonNote } from '@ionic/angular';
import { NOTIFICACIONES } from '../../datos/notificaciones';
import { Notificacion } from '../../modelos/notificacion';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { EstadoVacioComponent } from '../../componentes/estado-vacio/estado-vacio.component';

@Component({
  selector: 'app-notificaciones',
  templateUrl: './notificaciones.page.html',
  styleUrls: ['./notificaciones.page.scss'],
  imports: [IonContent, IonList, IonItem, IonItemSliding, IonItemOptions, IonItemOption, IonIcon, IonLabel,
    IonNote, CabeceraComponent, EstadoVacioComponent],
})
export class NotificacionesPage {
  notificaciones = [...NOTIFICACIONES];

  borrar(n: Notificacion) {
    this.notificaciones = this.notificaciones.filter(x => x !== n);
  }

  borrarTodas() {
    this.notificaciones = [];
  }
}
