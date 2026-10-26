import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonContent, IonHeader, IonTitle, IonToolbar } from '@ionic/angular';

@Component({
  selector: 'app-mi-cocina',
  templateUrl: './mi-cocina.page.html',
  styleUrls: ['./mi-cocina.page.scss'],
  imports: [IonContent, IonHeader, IonTitle, IonToolbar, CommonModule, FormsModule],
})
export class MiCocinaPage implements OnInit {
  constructor() {}

  ngOnInit() {}
}
