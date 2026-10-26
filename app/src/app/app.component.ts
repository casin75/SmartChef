import { Component } from '@angular/core';
import { IonApp, IonRouterOutlet } from '@ionic/angular';
import { addIcons } from 'ionicons';
import {
  restaurant,
  homeOutline,
  fileTrayStackedOutline,
  restaurantOutline,
  readerOutline,
  heartOutline,
  home,
  fileTrayStacked,
  reader,
  heart,
  moonOutline,
} from 'ionicons/icons';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  imports: [IonApp, IonRouterOutlet],
})
export class AppComponent {
  constructor() {
    addIcons({
      restaurant, homeOutline, fileTrayStackedOutline, restaurantOutline, readerOutline, heartOutline,
      home, fileTrayStacked, reader, heart, moonOutline,
    });
  }
}
