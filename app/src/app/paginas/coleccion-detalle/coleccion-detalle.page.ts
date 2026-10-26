import { Component, computed, input } from '@angular/core';
import { IonContent } from '@ionic/angular';
import { COLECCIONES } from '../../datos/colecciones';
import { RECETAS } from '../../datos/recetas';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { TarjetaRecetaComponent } from '../../componentes/tarjeta-receta/tarjeta-receta.component';
import { EstadoVacioComponent } from '../../componentes/estado-vacio/estado-vacio.component';

@Component({
  selector: 'app-coleccion-detalle',
  templateUrl: './coleccion-detalle.page.html',
  styleUrls: ['./coleccion-detalle.page.scss'],
  imports: [IonContent, CabeceraComponent, TarjetaRecetaComponent, EstadoVacioComponent],
})
export class ColeccionDetallePage {
  id = input<string>(); // recibe el ":id" de la dirección

  coleccion = computed(() => COLECCIONES.find(c => c.id === Number(this.id())));
  recetas = computed(() => RECETAS.filter(r => this.coleccion()?.recetas.includes(r.id)));
}
