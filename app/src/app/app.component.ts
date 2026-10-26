import { Component } from '@angular/core';
import { IonApp, IonRouterOutlet } from '@ionic/angular';
import { addIcons } from 'ionicons';
import {
  add, alarmOutline, bookmarkOutline, calendarOutline, cameraOutline, cartOutline,
  checkmarkCircle, fileTrayOutline, fileTrayStacked, fileTrayStackedOutline, flameOutline, heart,
  heartOutline, home, homeOutline, informationCircleOutline, leafOutline, logOutOutline,
  moonOutline, notificationsOffOutline, notificationsOutline, personOutline, reader,
  readerOutline, receiptOutline, restaurant, restaurantOutline, sadOutline, searchOutline, star,
  timeOutline, trashOutline, walletOutline, warning,
} from 'ionicons/icons';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  imports: [IonApp, IonRouterOutlet],
})
export class AppComponent {
  constructor() {
    // Registramos aquí todos los iconos que usa la app
  addIcons({
      add, alarmOutline, bookmarkOutline, calendarOutline, cameraOutline, cartOutline,
      checkmarkCircle, fileTrayOutline, fileTrayStacked, fileTrayStackedOutline, flameOutline, heart,
      heartOutline, home, homeOutline, informationCircleOutline, leafOutline, logOutOutline,
      moonOutline, notificationsOffOutline, notificationsOutline, personOutline, reader,
      readerOutline, receiptOutline, restaurant, restaurantOutline, sadOutline, searchOutline, star,
      timeOutline, trashOutline, walletOutline, warning,
    });
  }
}
