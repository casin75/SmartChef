import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'entrada', pathMatch: 'full' },

  // ── Fuera del menú ───────────────────────────────────────────
  { path: 'entrada',
    loadComponent: () => import('./paginas/entrada/entrada.page').then(m => m.EntradaPage) },
  { path: 'login',
    loadComponent: () => import('./paginas/login/login.page').then(m => m.LoginPage) },
  { path: 'registro',
    loadComponent: () => import('./paginas/registro/registro.page').then(m => m.RegistroPage) },

  // ── Dentro del menú ──────────────────────────────────────────
  { path: 'app',
    loadComponent: () => import('./paginas/tabs/tabs.page').then(m => m.TabsPage),
    children: [
      { path: 'inicio',
        loadComponent: () => import('./paginas/inicio/inicio.page').then(m => m.InicioPage) },
      { path: 'inicio/perfil',
        loadComponent: () => import('./paginas/perfil/perfil.page').then(m => m.PerfilPage) },
      { path: 'mi-cocina',
        loadComponent: () => import('./paginas/mi-cocina/mi-cocina.page').then(m => m.MiCocinaPage) },
      { path: 'recetas',
        loadComponent: () => import('./paginas/recetas/recetas.page').then(m => m.RecetasPage) },
      { path: 'lista',
        loadComponent: () => import('./paginas/lista/lista.page').then(m => m.ListaPage) },
      { path: 'favoritos',
        loadComponent: () => import('./paginas/favoritos/favoritos.page').then(m => m.FavoritosPage) },
      { path: '', redirectTo: 'inicio', pathMatch: 'full' },
    ],
  },
];
