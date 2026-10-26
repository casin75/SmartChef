import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IonContent, IonInput, IonInputPasswordToggle, IonButton, IonFooter,
  IonRouterLink } from '@ionic/angular';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  imports: [IonContent, IonInput, IonInputPasswordToggle, IonButton, IonFooter, RouterLink, IonRouterLink,
    CabeceraComponent],
})
export class LoginPage {}
