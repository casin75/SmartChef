import { Component } from '@angular/core';
import { IonContent } from '@ionic/angular';
import { AHORRO_SEMANA } from '../../datos/historial';
import { diasCocinados } from '../../utilidades/historial';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { TarjetaDatoComponent } from '../../componentes/tarjeta-dato/tarjeta-dato.component';
import { FilaImagenComponent } from '../../componentes/fila-imagen/fila-imagen.component';
import { EstadoVacioComponent } from '../../componentes/estado-vacio/estado-vacio.component';

@Component({
  selector: 'app-historial',
  templateUrl: './historial.page.html',
  styleUrls: ['./historial.page.scss'],
  imports: [IonContent, CabeceraComponent, TarjetaDatoComponent, FilaImagenComponent, EstadoVacioComponent],
})
export class HistorialPage {
  dias = diasCocinados();
  platos = this.dias.reduce((total, d) => total + d.recetas.length, 0);
  kcalMedia = this.dias.length ? Math.round(this.dias.reduce((t, d) => t + d.kcal, 0) / this.dias.length) : 0;
  ahorro = AHORRO_SEMANA;
}
