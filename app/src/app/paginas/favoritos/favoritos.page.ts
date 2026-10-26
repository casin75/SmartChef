import { Component } from '@angular/core';
import { IonContent, IonIcon } from '@ionic/angular';
import { COLECCIONES, FAVORITAS_RECIENTES } from '../../datos/colecciones';
import { RECETAS } from '../../datos/recetas';
import { Receta } from '../../modelos/receta';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { FilaImagenComponent } from '../../componentes/fila-imagen/fila-imagen.component';
import { EstadoVacioComponent } from '../../componentes/estado-vacio/estado-vacio.component';

@Component({
  selector: 'app-favoritos',
  templateUrl: './favoritos.page.html',
  styleUrls: ['./favoritos.page.scss'],
  imports: [IonContent, IonIcon, CabeceraComponent, FilaImagenComponent, EstadoVacioComponent],
})
export class FavoritosPage {
  colecciones = COLECCIONES;
  recientes: Receta[] = FAVORITAS_RECIENTES.map(id => RECETAS.find(r => r.id === id)!);

  // La foto de una colección es la de su primera receta (o la de la pasta si está vacía)
  fotoDe(recetas: number[]): string {
    return (RECETAS.find(r => r.id === recetas[0]) ?? RECETAS[0]).fotos[0].url;
  }
}
