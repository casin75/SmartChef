import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IonContent, IonInput, IonInputPasswordToggle, IonButton, IonFooter, IonRouterLink, IonIcon,
  IonDatetime, IonDatetimeButton, IonModal } from '@ionic/angular';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.page.html',
  styleUrls: ['./registro.page.scss'],
  imports: [IonContent, IonInput, IonInputPasswordToggle, IonButton, IonFooter, RouterLink, IonRouterLink,
    IonIcon, IonDatetime, IonDatetimeButton, IonModal, CabeceraComponent],
})
export class RegistroPage {}
