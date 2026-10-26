import { Component, computed, input } from '@angular/core';
import { IonContent, IonBackButton, IonSegment, IonSegmentButton, IonLabel, IonIcon, IonButton,
  IonFooter } from '@ionic/angular';
import { RECETAS } from '../../datos/recetas';
import { FAVORITAS_RECIENTES } from '../../datos/colecciones';
import { UNIDADES } from '../../modelos/ingrediente';
import { loTengo } from '../../utilidades/despensa';
import { ChipDificultadComponent } from '../../componentes/chip-dificultad/chip-dificultad.component';
import { ChipEtiquetaComponent } from '../../componentes/chip-etiqueta/chip-etiqueta.component';
import { EstadoVacioComponent } from '../../componentes/estado-vacio/estado-vacio.component';

@Component({
  selector: 'app-receta-detalle',
  templateUrl: './receta-detalle.page.html',
  styleUrls: ['./receta-detalle.page.scss'],
  imports: [IonContent, IonBackButton, IonSegment, IonSegmentButton, IonLabel, IonIcon, IonButton, IonFooter,
    ChipDificultadComponent, ChipEtiquetaComponent, EstadoVacioComponent],
})
export class RecetaDetallePage {
  id = input<string>(); // recibe el ":id" de la dirección

  // Buscamos la receta cada vez que cambia el id. En la dirección todo es texto: lo pasamos a número.
  receta = computed(() => RECETAS.find(r => r.id === Number(this.id())));

  seccion: 'ingredientes' | 'pasos' = 'ingredientes';
  favorita = false;
  cocinada = false;

  unidades = UNIDADES;
  loTengo = loTengo;

  faltantes = computed(() => this.receta()?.ingredientes.filter(ri => !loTengo(ri.ingrediente.id)).length ?? 0);

  ngOnInit() {
    this.favorita = FAVORITAS_RECIENTES.includes(Number(this.id()));
  }
}
