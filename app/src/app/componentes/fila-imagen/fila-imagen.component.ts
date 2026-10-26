import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

// Una fila con foto a la izquierda. La usan las colecciones, las favoritas y el historial.
// Lo que va debajo del título lo pone quien la usa (ng-content).
@Component({
  selector: 'app-fila-imagen',
  templateUrl: './fila-imagen.component.html',
  styleUrls: ['./fila-imagen.component.scss'],
  imports: [RouterLink],
})
export class FilaImagenComponent {
  imagen = input.required<string>();
  titulo = input.required<string>();
  enlace = input<(string | number)[]>(); // a dónde lleva al tocarla
  insignia = input<number>(); // número en un círculo naranja sobre la foto
  grande = input(false); // foto rectangular grande en vez de redonda
  flecha = input(false); // pinta la flecha aunque no haya enlace (cuando la fila abre una ventana)
}
