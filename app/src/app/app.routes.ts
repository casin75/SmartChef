import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'inicio', // al abrir la app, vamos a inicio
    pathMatch: 'full',
  },
  {
    path: 'inicio',
    loadComponent: () => import('./paginas/inicio/inicio.page').then((m) => m.InicioPage),
  },
  {
    path: 'mi-cocina',
    loadComponent: () => import('./paginas/mi-cocina/mi-cocina.page').then((m) => m.MiCocinaPage),
  },
  {
    path: 'recetas',
    loadComponent: () => import('./paginas/recetas/recetas.page').then((m) => m.RecetasPage),
  },
  {
    path: 'lista',
    loadComponent: () => import('./paginas/lista/lista.page').then((m) => m.ListaPage),
  },
  {
    path: 'favoritos',
    loadComponent: () => import('./paginas/favoritos/favoritos.page').then((m) => m.FavoritosPage),
  },
];
