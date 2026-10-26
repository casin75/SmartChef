import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IonContent, IonButton, IonRouterLink } from '@ionic/angular';

@Component({
  selector: 'app-no-encontrada',
  templateUrl: './no-encontrada.page.html',
  styleUrls: ['./no-encontrada.page.scss'],
  imports: [IonContent, IonButton, RouterLink, IonRouterLink],
})
export class NoEncontradaPage {}
