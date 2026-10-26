import { Component } from '@angular/core';
import { IonTabs, IonTabBar, IonTabButton, IonIcon, IonLabel } from '@ionic/angular';

@Component({
  selector: 'app-tabs',
  templateUrl: './tabs.page.html',
  styleUrls: ['./tabs.page.scss'],
  imports: [IonTabs, IonTabBar, IonTabButton, IonIcon, IonLabel],
})
export class TabsPage {
  // El "tab" tiene que coincidir con el "path" de app.routes.ts
  pestanas = [
    { tab: 'inicio', texto: 'Inicio', icono: 'home' },
    { tab: 'mi-cocina', texto: 'Mi Cocina', icono: 'file-tray-stacked' },
    { tab: 'recetas', texto: 'Recetas', icono: 'restaurant' },
    { tab: 'lista', texto: 'Lista', icono: 'reader' },
    { tab: 'favoritos', texto: 'Favoritos', icono: 'heart' },
  ];

  pestanaActiva = 'inicio';

  cambiarPestana(evento: { tab: string }) {
    this.pestanaActiva = evento.tab;
  }
}
