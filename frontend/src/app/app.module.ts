import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// import { MdCardModule, MdInputModule } from '@angular/material';
import { mixinColor, MatCommonModule } from '@angular/material/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';

/*
import { RouterModule, Routes, PreloadAllModules } from '@angular/router';


const routes: Routes = [
  {
    path: 'users',
    loadChildren: () =>
      import('./users/users.module').then(m => m.UsersModule)
  },
  // Autres routes principales ici...
  { path: '', redirectTo: 'users', pathMatch: 'full' },
  { path: '**', redirectTo: 'users' }
];
*/

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
   // RouterModule,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule, // requis pour Angular Material
    MatCommonModule,
    MatSlideToggleModule,
    MatSidenavModule,        // <- obligatoire pour <mat-sidenav-content> !
    MatListModule,
    MatIconModule,
    MatButtonModule,
    MatDividerModule,
   // RouterModule,
    // autres modules
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
