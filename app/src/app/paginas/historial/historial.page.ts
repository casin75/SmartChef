import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { IonContent, IonModal, IonHeader, IonToolbar, IonTitle, IonButtons, IonButton } from '@ionic/angular';
import { AHORRO_SEMANA } from '../../datos/historial';
import { DiaCocinado, diasCocinados } from '../../utilidades/historial';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { TarjetaDatoComponent } from '../../componentes/tarjeta-dato/tarjeta-dato.component';
import { FilaImagenComponent } from '../../componentes/fila-imagen/fila-imagen.component';
import { EstadoVacioComponent } from '../../componentes/estado-vacio/estado-vacio.component';

@Component({
  selector: 'app-historial',
  templateUrl: './historial.page.html',
  styleUrls: ['./historial.page.scss'],
  imports: [IonContent, IonModal, IonHeader, IonToolbar, IonTitle, IonButtons, IonButton, CabeceraComponent,
    TarjetaDatoComponent, FilaImagenComponent, EstadoVacioComponent],
})
export class HistorialPage {
  dias = diasCocinados();
  platos = this.dias.reduce((total, d) => total + d.recetas.length, 0);
  kcalMedia = this.dias.length ? Math.round(this.dias.reduce((t, d) => t + d.kcal, 0) / this.dias.length) : 0;
  ahorro = AHORRO_SEMANA;

  // El día que se ha tocado: si hay uno, la ventana de abajo está abierta
  diaElegido: DiaCocinado | null = null;

  abrir(dia: DiaCocinado) {
    this.diaElegido = dia;
  }

  cerrar() {
    this.diaElegido = null;
  }

  // Primero cerramos la ventana y, cuando ya se ha ido, abrimos la ficha dentro de la pestaña Inicio
  private router = inject(Router);
  private recetaPendiente: number | null = null;

  verReceta(id: number) {
    this.recetaPendiente = id;
    this.cerrar();
  }

  alCerrarVentana() {
    this.cerrar();
    if (this.recetaPendiente !== null) {
      this.router.navigate(['/app/inicio/receta', this.recetaPendiente]);
      this.recetaPendiente = null;
    }
  }
}
