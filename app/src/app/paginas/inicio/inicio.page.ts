import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IonContent, IonIcon, IonBadge } from '@ionic/angular';
import { USUARIO } from '../../datos/usuario';
import { DESPENSA } from '../../datos/despensa';
import { RECETAS, RECETAS_DEL_DIA } from '../../datos/recetas';
import { NOTIFICACIONES } from '../../datos/notificaciones';
import { HOY } from '../../datos/hoy';
import { diasHasta, diaSemana } from '../../utilidades/fechas';
import { diasCocinados } from '../../utilidades/historial';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { TarjetaRecetaComponent } from '../../componentes/tarjeta-receta/tarjeta-receta.component';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.page.html',
  styleUrls: ['./inicio.page.scss'],
  imports: [RouterLink, IonContent, IonIcon, IonBadge, CabeceraComponent, TarjetaRecetaComponent],
})
export class InicioPage {
  usuario = USUARIO;
  ingredientes = DESPENSA.length;
  caducanPronto = DESPENSA.filter(p => p.caducidad && diasHasta(p.caducidad) <= 2).length;
  notificaciones = NOTIFICACIONES.length;
  recetasDelDia = RECETAS.filter(r => RECETAS_DEL_DIA.includes(r.id));

  // "Sáb 19" para el antetítulo
  hoy = `${diaSemana(HOY)} ${Number(HOY.slice(8))}`;

  // La semana del historial, de lunes a domingo, con la foto del primer plato de cada día
  semana = ['2026-09-14', '2026-09-15', '2026-09-16', '2026-09-17', '2026-09-18', '2026-09-19', '2026-09-20']
    .map(fecha => ({
      dia: diaSemana(fecha),
      cocinado: diasCocinados().find(d => d.fecha === fecha),
      esHoy: fecha === HOY,
    }));

  diasCocinando = diasCocinados().length;
}
