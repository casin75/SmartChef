import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IonContent, IonButton, IonIcon } from '@ionic/angular';

@Component({
  selector: 'app-entrada',
  templateUrl: './entrada.page.html',
  styleUrls: ['./entrada.page.scss'],
  imports: [IonContent, IonButton, IonIcon, RouterLink],
})
export class EntradaPage {}
