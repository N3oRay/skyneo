import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgIconsModule, provideNgIconsConfig} from '@ng-icons/core';
import { featherAirplay, featherUser, featherVideo, featherSmile, featherSearch,
    featherPower, featherInstagram, featherInfo, featherHexagon, featherGlobe, featherCoffee, featherCircle, featherActivity } from '@ng-icons/feather-icons';
import { heroUsers } from '@ng-icons/heroicons/outline';

//import chalk from 'chalk';

//console.log(chalk.blue('Hello world!'));
console.log('Hello world!');

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
    NgIconsModule.withIcons({ featherAirplay, featherUser, featherVideo, featherSmile, featherSearch,
    featherPower, featherInstagram, featherInfo, featherHexagon, featherGlobe, featherCoffee, featherCircle, featherActivity, heroUsers })
   // RouterModule,
    // autres modules
  ],
  providers: [
    provideNgIconsConfig({
      size: '1.0em',
      color: 'white',
    }),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
