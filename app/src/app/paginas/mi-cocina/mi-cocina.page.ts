import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IonContent, IonSegment, IonSegmentButton, IonLabel, IonIcon, IonButton } from '@ionic/angular';
import { DESPENSA } from '../../datos/despensa';
import { Despensa, Ubicacion } from '../../modelos/despensa';
import { diasHasta } from '../../utilidades/fechas';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { FilaProductoComponent } from '../../componentes/fila-producto/fila-producto.component';
import { EstadoVacioComponent } from '../../componentes/estado-vacio/estado-vacio.component';

@Component({
  selector: 'app-mi-cocina',
  templateUrl: './mi-cocina.page.html',
  styleUrls: ['./mi-cocina.page.scss'],
  imports: [RouterLink, IonContent, IonSegment, IonSegmentButton, IonLabel, IonIcon, IonButton,
    CabeceraComponent, FilaProductoComponent, EstadoVacioComponent],
})
export class MiCocinaPage {
  despensa = DESPENSA;
  ubicacion: Ubicacion = 'FRIGORIFICO';

  ubicaciones: { valor: Ubicacion; texto: string; icono: string }[] = [
    { valor: 'FRIGORIFICO', texto: 'Frigorífico', icono: '🧊' },
    { valor: 'DESPENSA', texto: 'Despensa', icono: '🏠' },
    { valor: 'CONGELADOR', texto: 'Congelador', icono: '❄️' },
  ];

  // Los productos de la ubicación elegida, los que caducan antes primero
  get productosVisibles(): Despensa[] {
    return this.despensa
      .filter(p => p.ubicacion === this.ubicacion)
      .sort((a, b) => (a.caducidad ?? '9999').localeCompare(b.caducidad ?? '9999'));
  }

  // Los que caducan en 2 días o menos
  get caducanPronto(): Despensa[] {
    return this.despensa.filter(p => p.caducidad && diasHasta(p.caducidad) <= 2);
  }
}
