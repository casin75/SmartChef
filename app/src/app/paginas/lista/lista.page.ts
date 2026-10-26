import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonContent, IonCheckbox, IonFab, IonFabButton, IonIcon, IonModal, IonHeader, IonToolbar, IonTitle,
  IonButtons, IonButton, IonInput, IonSelect, IonSelectOption, IonItemSliding, IonItem, IonItemOptions,
  IonItemOption, IonList, IonProgressBar } from '@ionic/angular';
import { LISTA_COMPRA, CATEGORIAS } from '../../datos/lista-compra';
import { ItemLista } from '../../modelos/item-lista';
import { UNIDADES } from '../../modelos/ingrediente';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { EstadoVacioComponent } from '../../componentes/estado-vacio/estado-vacio.component';

@Component({
  selector: 'app-lista',
  templateUrl: './lista.page.html',
  styleUrls: ['./lista.page.scss'],
  imports: [FormsModule, IonContent, IonCheckbox, IonFab, IonFabButton, IonIcon, IonModal, IonHeader, IonToolbar,
    IonTitle, IonButtons, IonButton, IonInput, IonSelect, IonSelectOption, IonItemSliding, IonItem,
    IonItemOptions, IonItemOption, IonList, IonProgressBar, CabeceraComponent, EstadoVacioComponent],
})
export class ListaPage {
  items = [...LISTA_COMPRA]; // una copia: así podemos añadir y borrar sin tocar los datos originales
  categorias = CATEGORIAS;
  unidades = UNIDADES;

  // La ventana "Nuevo producto"
  modalAbierto = false;
  nuevo = { nombre: '', cantidad: 1, categoria: 'Verduras' };

  get marcados(): number {
    return this.items.filter(i => i.marcado).length;
  }

  // Los productos agrupados por categoría, en el orden de CATEGORIAS
  get grupos(): { categoria: string; items: ItemLista[] }[] {
    return this.categorias
      .map(categoria => ({ categoria, items: this.items.filter(i => i.categoria === categoria) }))
      .filter(g => g.items.length > 0);
  }

  borrar(item: ItemLista) {
    this.items = this.items.filter(i => i !== item);
  }

  anadir() {
    if (!this.nuevo.nombre.trim()) return;
    this.items = [...this.items, {
      ingrediente: { id: Date.now(), nombre: this.nuevo.nombre.trim(), unidadMedida: 2, icono: '🛒' },
      cantidad: this.nuevo.cantidad,
      categoria: this.nuevo.categoria,
      marcado: false,
    }];
    this.nuevo = { nombre: '', cantidad: 1, categoria: 'Verduras' };
    this.modalAbierto = false;
  }
}
