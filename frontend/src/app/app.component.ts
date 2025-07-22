import { Component } from '@angular/core';
import { trigger, transition, style, animate } from '@angular/animations';
import { NgIcon, provideIcons } from '@ng-icons/core';
import { featherAirplay } from '@ng-icons/feather-icons';
import { heroUsers } from '@ng-icons/heroicons/outline';

interface MenuItem {
  label: string;
  subItems?: { label: string; content: string; }[];
}
/*
@Component({
  standalone: true,
  imports: [NgIcon],
  providers: [provideIcons({ featherAirplay, heroUsers })],
})
*/
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations: [
    trigger('articleFade', [
      transition(':enter', [
        style({ opacity: 0, transform: 'scale(0.95) translateY(30px)' }),
        animate('400ms cubic-bezier(.25,1.7,.36,1)')
      ]),
      transition(':leave', [
        animate('250ms ease-in', style({ opacity: 0, transform: 'scale(0.98) translateY(10px)' }))
      ]),
    ])
  ]
})
export class AppComponent {
  menus: MenuItem[] = [
    {
      label: 'Parc Informatique',
      subItems: [
        { label: 'Principale', content: 'Site principale, Bureaux.' },
        { label: 'Secondaires', content: 'Site distants, Clouds, Herbergements.' }
      ]
    },
    {
      label: 'Infrastructures',
      subItems: [
        { label: 'Routeur', content: 'Listes des équippements réseaux.' },
        { label: 'Firewall', content: 'Listes des équippements de protections, contrôle et accès.' },
        { label: 'GPS', content: 'Antennes, et locations.' },
        { label: 'Climatisations', content: 'Listes des équippements de ventilisations et climatisations' }
      ]
    },
    {
      label: 'Objets connectés',
      subItems: [
        { label: 'Audios', content: 'Ensemble des composants permettants la diffusions audios.' },
        { label: 'Vidéos', content: 'Ensembles de écrans et outils de diffusions.' },
        { label: 'Divers', content: 'Accessoires divers.' }
      ]
    },
    {
      label: 'Equippements',
      subItems: [
        { label: 'Portables', content: 'Ordinateurs portables.' },
        { label: 'PC Fixes', content: 'Ordinateurs fixes, équippements de bureaux, équippements personnels.' },
        { label: 'Téléphonies', content: 'Smartphones, tablettes, petits équippements.' }
      ]
    },
    {
      label: 'Configuration',
      subItems: [
        { label: 'Listes standards', content: 'Listes complètes des équippements.' },
        { label: 'Listes Watts', content: 'Classements des équippements en fonction de leur consommation.' },
        { label: 'Spécifications techniques', content: 'Smartphones, tablettes, petits équippements, serveurs, pc fixe. Détails des spécifications.' },
        { label: 'Veilles technologiques', content: 'Définitions et documations.' }
      ]
    }
  ];

  selectedMenuIndex: number = 0;
  selectedSubmenuIndex: number = 0;

  get selectedContent(): string {
    return this.menus[this.selectedMenuIndex].subItems?.[this.selectedSubmenuIndex]?.content ?? '';
  }

  selectMenu(i: number) {
    this.selectedMenuIndex = i;
    this.selectedSubmenuIndex = 0;
  }

  selectSubmenu(j: number) {
    this.selectedSubmenuIndex = j;
  }
}
