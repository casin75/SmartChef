# SmartChef · App móvil

**Come mejor, gasta menos.** SmartChef es una app para organizar la comida de casa: sabe qué tienes en la
nevera y la despensa, te avisa antes de que algo caduque, te propone recetas con lo que ya tienes, prepara
tu lista de la compra y lleva un historial de lo que cocinas cada semana.

Proyecto integrado de 2º DAM (Desarrollo de Interfaces + Acceso a Datos). Esta carpeta contiene la interfaz
(Entrega 1): todas las pantallas con datos de maqueta, sin conectar todavía con la API de Spring Boot que
está en la raíz del repositorio.

**Tecnologías:** Angular 22 · Ionic 9 · Capacitor 8 · componentes standalone.

## Cómo arrancarla

```bash
cd app
npm install
ionic serve            # o npm start → http://localhost:4200
```

Para el móvil Android:

```bash
ionic build
npx cap add android    # solo la primera vez
npx cap sync
npx cap run android
```

## Capturas

Cada pantalla en modo claro y en modo oscuro.

| Entrada | Inicio de sesión | Registro | Inicio |
|---|---|---|---|
| <img src="docs/capturas/entrada.png" width="180"> <img src="docs/capturas/entrada-oscuro.png" width="180"> | <img src="docs/capturas/login.png" width="180"> <img src="docs/capturas/login-oscuro.png" width="180"> | <img src="docs/capturas/registro.png" width="180"> <img src="docs/capturas/registro-oscuro.png" width="180"> | <img src="docs/capturas/inicio.png" width="180"> <img src="docs/capturas/inicio-oscuro.png" width="180"> |

| Mi Cocina | Añadir producto | Recetas | Ficha de receta |
|---|---|---|---|
| <img src="docs/capturas/mi-cocina.png" width="180"> <img src="docs/capturas/mi-cocina-oscuro.png" width="180"> | <img src="docs/capturas/producto-nuevo.png" width="180"> <img src="docs/capturas/producto-nuevo-oscuro.png" width="180"> | <img src="docs/capturas/recetas.png" width="180"> <img src="docs/capturas/recetas-oscuro.png" width="180"> | <img src="docs/capturas/receta-detalle.png" width="180"> <img src="docs/capturas/receta-detalle-oscuro.png" width="180"> |

| Lista de la compra | Favoritos | Colección | Historial semanal |
|---|---|---|---|
| <img src="docs/capturas/lista.png" width="180"> <img src="docs/capturas/lista-oscuro.png" width="180"> | <img src="docs/capturas/favoritos.png" width="180"> <img src="docs/capturas/favoritos-oscuro.png" width="180"> | <img src="docs/capturas/coleccion-detalle.png" width="180"> <img src="docs/capturas/coleccion-detalle-oscuro.png" width="180"> | <img src="docs/capturas/historial.png" width="180"> <img src="docs/capturas/historial-oscuro.png" width="180"> |

| Perfil | Notificaciones | Escanear ticket | Página 404 |
|---|---|---|---|
| <img src="docs/capturas/perfil.png" width="180"> <img src="docs/capturas/perfil-oscuro.png" width="180"> | <img src="docs/capturas/notificaciones.png" width="180"> <img src="docs/capturas/notificaciones-oscuro.png" width="180"> | <img src="docs/capturas/escanear-ticket.png" width="180"> <img src="docs/capturas/escanear-ticket-oscuro.png" width="180"> | <img src="docs/capturas/no-encontrada.png" width="180"> <img src="docs/capturas/no-encontrada-oscuro.png" width="180"> |

## Identidad visual

![Paleta de SmartChef](docs/paleta.png)

| Color | Hex | Dónde se usa |
|---|---|---|
| primary · Albahaca | `#285515` | Cabeceras, botones principales, pestaña activa |
| secondary · Calabaza | `#D96B27` | Acciones destacadas, botón flotante, indicador del menú |
| tertiary · Hoja | `#4F8A2B` | Casillas marcadas, tarjeta «Caducan pronto» |
| Fondo · Crema | `#F8F5EF` | Fondo de todas las pantallas |
| Texto | `#1F2419` | Títulos y textos |

En modo oscuro el fondo pasa a `#12160F`, las tarjetas a `#1C2218` y el verde principal se aclara
(`#8CC16B`) para que se lea bien.

**Tipografías** (instaladas con Fontsource, funcionan sin internet):

- **DM Serif Display** para los títulos (la serif de las cabeceras de Figma).
- **Instrument Sans** para los textos.

**Componentes de Ionic modificados:** menú inferior con línea naranja e icono relleno en la pestaña activa,
segmentos con fondo verde suave, chips de filtro en forma de píldora, buscador y campos con caja blanca,
botón atrás circular sobre la foto de la receta, barra de progreso naranja dentro de la cabecera, casillas
verdes en la lista de la compra.

## Árbol de rutas

```
/                         → redirige a /entrada
├── entrada               Entrada (fuera del menú)
├── login                 Inicio de sesión (fuera del menú)
├── registro              Registro (fuera del menú)
├── app                   TabsPage: menú inferior
│   ├── inicio            Inicio
│   ├── inicio/historial  Historial semanal (cada día abre una ventana con sus platos)
│   ├── inicio/receta/:id Ficha de receta abierta desde Inicio
│   ├── inicio/perfil     Perfil y modo oscuro
│   ├── mi-cocina         Mi Cocina
│   ├── mi-cocina/nuevo   Añadir producto (formulario)
│   ├── recetas           Recetas
│   ├── recetas/:id       Ficha de receta
│   ├── lista             Lista de la compra
│   ├── favoritos         Favoritos
│   ├── favoritos/receta/:id  Ficha de receta abierta desde Favoritos
│   └── favoritos/:id     Colección
├── escanear-ticket       Pantalla completa, sin menú
├── notificaciones        Pantalla completa, sin menú
└── **                    Página 404
```

La ficha de receta existe dentro de cada pestaña desde la que se puede abrir. Así se queda iluminada la
pestaña en la que estabas y el botón atrás vuelve a la pantalla anterior (por ejemplo, al historial) en vez de
saltar a Recetas.

Al entrar (login/registro) y al cerrar sesión se navega con `routerDirection="root"`, así el botón atrás no
vuelve al login.

## Árbol de componentes

```
AppComponent
├── EntradaPage ─────────── app-logo-chef
├── LoginPage / RegistroPage ── app-cabecera
├── TabsPage
│   ├── InicioPage ──────── app-cabecera, app-tarjeta-receta (mini)
│   ├── HistorialPage ───── app-cabecera, app-tarjeta-dato, app-fila-imagen, app-estado-vacio
│   ├── PerfilPage ──────── app-cabecera, app-tarjeta-dato
│   ├── MiCocinaPage ────── app-cabecera, app-fila-producto, app-estado-vacio
│   ├── ProductoNuevoPage ─ app-cabecera
│   ├── RecetasPage ─────── app-cabecera, app-tarjeta-receta, app-estado-vacio
│   ├── RecetaDetallePage ─ app-chip-etiqueta, app-chip-dificultad, app-estado-vacio
│   ├── ListaPage ───────── app-cabecera, app-estado-vacio
│   ├── FavoritosPage ───── app-cabecera, app-fila-imagen, app-estado-vacio
│   └── ColeccionDetallePage ─ app-cabecera, app-tarjeta-receta, app-estado-vacio
├── EscanearTicketPage ──── app-fila-producto
├── NotificacionesPage ──── app-cabecera, app-estado-vacio
└── NoEncontradaPage

app-tarjeta-receta
├── app-chip-etiqueta
└── app-chip-dificultad
```

| Componente | Entradas (`input()`) | Para qué |
|---|---|---|
| `app-cabecera` | `titulo`, `subtitulo`, `atras` | La cabecera verde de cada pantalla. Tiene **tres huecos `ng-content`**: antetítulo, iconos de la derecha y lo que va debajo (botones, barra de progreso). |
| `app-tarjeta-receta` | `receta`, `mini` | Tarjeta con foto, etiquetas, tiempo, kcal, raciones y dificultad. |
| `app-chip-dificultad` | `dificultad` | Fácil / Media / Difícil con `@switch`. |
| `app-chip-etiqueta` | `etiqueta` | Vegetariano, Rápido, Temporada… con `@switch`. |
| `app-fila-producto` | `producto` | Un producto de la despensa, con la caducidad en rojo o naranja si está cerca. |
| `app-fila-imagen` | `imagen`, `titulo`, `enlace`, `insignia`, `grande` | Fila con foto. El detalle lo pone quien la usa con **`ng-content`**. |
| `app-estado-vacio` | `icono`, `titulo` | Lo que se ve cuando una lista está vacía. Texto y botones por **`ng-content`**. |
| `app-tarjeta-dato` | `icono`, `valor`, `etiqueta` | Un número destacado (historial y perfil). |
| `app-logo-chef` | — | El chef robot en SVG, que cambia de color en modo oscuro. |

## Modelos y datos de maqueta

- `src/app/modelos/`: interfaces con los mismos campos que las entidades del backend (`Receta`, `Paso`,
  `Foto`, `Ingrediente`, `Despensa`, `Coleccion`, `UsuarioReceta`, `Notificacion`, `Usuario`).
- `src/app/datos/`: 9 recetas, 26 ingredientes, 12 productos en la despensa, colecciones, historial,
  lista de la compra y notificaciones. Hay nombres largos, campos opcionales vacíos y una colección sin
  recetas para comprobar que el diseño aguanta.
- La maqueta vive siempre en el 19 de septiembre de 2026 (`datos/hoy.ts`), la misma semana del diseño.

Algunos campos los necesita la interfaz pero el backend todavía no los tiene. Están marcados con el comentario
«solo en la maqueta»: `Receta.kcal`, `Receta.valoracion`, `Receta.etiquetas`, `Despensa.ubicacion`, y la
cantidad, la categoría y el marcado de la lista de la compra.

## Requisitos de la entrega

| Requisito | Dónde |
|---|---|
| Login fuera del menú con `routerDirection="root"` | `login`, `registro`, cerrar sesión en `perfil` |
| Menú inferior de 5 pestañas, personalizado | `paginas/tabs` |
| Detalle con `:id` | `recetas/:id`, `favoritos/:id` |
| Pantalla completa y 404 | `escanear-ticket`, `notificaciones`, `**` |
| 4+ componentes con `input()`, uno con `ng-content` | 8 componentes, 3 con `ng-content` |
| `@for`, `@if`, `@switch` | En casi todas las páginas; `@switch` en chips, notificaciones, ficha y escáner |
| Lista, tarjeta, chip, segmento, buscador | Lista de la compra, tarjetas de receta, filtros, Mi Cocina y ficha, Recetas |
| Formulario con 4 tipos de campo | Añadir producto: texto, número, selector, fecha e interruptor |
| Modal y botón flotante | Lista de la compra (botón + abre «Nuevo producto»); calendarios |
| Tema propio y modo oscuro | `theme/variables.scss`, interruptor en Perfil |
| Estados vacíos | Recetas, Mi Cocina, Lista, Favoritos, Colección, Historial, Notificaciones |
| Fotos con licencia y créditos | **Pendiente:** ver `CREDITOS.md` |
