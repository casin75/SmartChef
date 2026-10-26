import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IonContent, IonButton } from '@ionic/angular';
import { LogoChefComponent } from '../../componentes/logo-chef/logo-chef.component';

@Component({
  selector: 'app-entrada',
  templateUrl: './entrada.page.html',
  styleUrls: ['./entrada.page.scss'],
  imports: [IonContent, IonButton, RouterLink, LogoChefComponent],
})
export class EntradaPage {}
