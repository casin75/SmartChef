import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IonContent, IonInput, IonSelect, IonSelectOption, IonDatetime, IonDatetimeButton, IonModal, IonIcon,
  IonToggle, IonFooter, IonButton, IonRouterLink } from '@ionic/angular';
import { UNIDADES } from '../../modelos/ingrediente';
import { Ubicacion } from '../../modelos/despensa';
import { HOY } from '../../datos/hoy';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';

@Component({
  selector: 'app-producto-nuevo',
  templateUrl: './producto-nuevo.page.html',
  styleUrls: ['./producto-nuevo.page.scss'],
  imports: [RouterLink, IonRouterLink, IonContent, IonInput, IonSelect, IonSelectOption, IonDatetime,
    IonDatetimeButton, IonModal, IonIcon, IonToggle, IonFooter, IonButton, CabeceraComponent],
})
export class ProductoNuevoPage {
  // Las unidades como lista [{ valor: 0, texto: 'g' }, …] para el selector
  unidades = Object.entries(UNIDADES).map(([valor, texto]) => ({ valor: Number(valor), texto }));

  ubicacion: Ubicacion = 'FRIGORIFICO';
  ubicaciones: { valor: Ubicacion; texto: string; icono: string }[] = [
    { valor: 'FRIGORIFICO', texto: 'Frigorífico', icono: '🧊' },
    { valor: 'DESPENSA', texto: 'Despensa', icono: '🏠' },
    { valor: 'CONGELADOR', texto: 'Congelador', icono: '❄️' },
  ];

  hoy = HOY;
}
