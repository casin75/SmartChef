import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IonContent, IonButton, IonRouterLink, IonList, IonItem, IonIcon, IonToggle, IonLabel, IonAccordionGroup,
  IonAccordion, ToggleCustomEvent } from '@ionic/angular';
import { USUARIO } from '../../datos/usuario';
import { DESPENSA } from '../../datos/despensa';
import { HISTORIAL } from '../../datos/historial';
import { FAVORITAS_RECIENTES } from '../../datos/colecciones';
import { RECETAS } from '../../datos/recetas';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { TarjetaDatoComponent } from '../../componentes/tarjeta-dato/tarjeta-dato.component';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.page.html',
  styleUrls: ['./perfil.page.scss'],
  imports: [RouterLink, IonRouterLink, IonContent, IonButton, IonList, IonItem, IonIcon, IonToggle, IonLabel,
    IonAccordionGroup, IonAccordion, CabeceraComponent, TarjetaDatoComponent],
})
export class PerfilPage {
  usuario = USUARIO;
  ingredientes = DESPENSA.length;
  cocinadas = HISTORIAL.filter(h => h.tipo === 0).length;
  favoritas = FAVORITAS_RECIENTES.length;

  // Para "Acerca de y créditos": de qué fichero viene cada foto
  fotos = RECETAS.map(r => r.fotos[0].url.split('/').pop());

  oscuro = document.documentElement.classList.contains('ion-palette-dark');

  cambiarTema(evento: ToggleCustomEvent) {
    const activado = evento.detail.checked; // true si el interruptor está encendido
    document.documentElement.classList.toggle('ion-palette-dark', activado);
  }
}
