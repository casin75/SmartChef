import { Component } from '@angular/core';

// El chef robot de la pantalla de entrada. Es un SVG con "currentColor":
// toma el color del texto, así que en modo oscuro se aclara solo.
@Component({
  selector: 'app-logo-chef',
  templateUrl: './logo-chef.component.html',
  styleUrls: ['./logo-chef.component.scss'],
})
export class LogoChefComponent {}
