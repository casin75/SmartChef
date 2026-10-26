import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonContent, IonSearchbar, IonChip, IonButton, IonIcon } from '@ionic/angular';
import { RECETAS } from '../../datos/recetas';
import { Etiqueta, Receta } from '../../modelos/receta';
import { faltan } from '../../utilidades/despensa';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { TarjetaRecetaComponent } from '../../componentes/tarjeta-receta/tarjeta-receta.component';
import { EstadoVacioComponent } from '../../componentes/estado-vacio/estado-vacio.component';

@Component({
  selector: 'app-recetas',
  templateUrl: './recetas.page.html',
  styleUrls: ['./recetas.page.scss'],
  imports: [FormsModule, IonContent, IonSearchbar, IonChip, IonButton, IonIcon, CabeceraComponent,
    TarjetaRecetaComponent, EstadoVacioComponent],
})
export class RecetasPage {
  recetas = RECETAS;
  busqueda = ''; // lo que escribe el usuario
  filtro: 'TODAS' | Etiqueta = 'TODAS'; // la píldora elegida
  conMisIngredientes = true; // los dos botones de la cabecera

  filtros: { valor: 'TODAS' | Etiqueta; texto: string }[] = [
    { valor: 'TODAS', texto: 'Todas' },
    { valor: 'VEGETARIANA', texto: 'Vegetariano' },
    { valor: 'RAPIDA', texto: 'Rápidas' },
    { valor: 'SIN_GLUTEN', texto: 'Sin gluten' },
    { valor: 'PROTEINAS', texto: 'Proteínas' },
  ];

  // Se recalcula solo cada vez que la pantalla se redibuja
  get recetasVisibles(): Receta[] {
    const texto = this.busqueda.toLowerCase();
    return this.recetas.filter(r =>
      (this.filtro === 'TODAS' || r.etiquetas.includes(this.filtro)) &&
      (!this.conMisIngredientes || faltan(r) <= 1) && // como mucho, que falte un ingrediente
      r.nombre.toLowerCase().includes(texto)
    );
  }

  limpiar() {
    this.busqueda = '';
    this.filtro = 'TODAS';
    this.conMisIngredientes = false;
  }
}
