import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IonContent, IonBackButton, IonButton, IonIcon, IonFooter, IonRouterLink, IonSpinner } from '@ionic/angular';
import { DESPENSA } from '../../datos/despensa';
import { FilaProductoComponent } from '../../componentes/fila-producto/fila-producto.component';

// Pantalla completa, sin menú. De momento la cámara es de mentira: al pulsar el botón
// "lee" un ticket de ejemplo. La cámara de verdad llegará con Capacitor.
@Component({
  selector: 'app-escanear-ticket',
  templateUrl: './escanear-ticket.page.html',
  styleUrls: ['./escanear-ticket.page.scss'],
  imports: [RouterLink, IonRouterLink, IonContent, IonBackButton, IonButton, IonIcon, IonFooter, IonSpinner,
    FilaProductoComponent],
})
export class EscanearTicketPage {
  estado: 'camara' | 'leyendo' | 'leido' = 'camara';
  productosLeidos = DESPENSA.slice(0, 5);

  fotografiar() {
    this.estado = 'leyendo';
    setTimeout(() => (this.estado = 'leido'), 1200);
  }
}
