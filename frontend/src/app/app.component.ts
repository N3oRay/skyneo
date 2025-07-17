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
      label: 'Quêtes',
      subItems: [
        { label: 'Principale', content: 'Voici la quête principale de Skyrim. Explore le monde, terrasse Alduin et sauve Bordeciel.' },
        { label: 'Secondaires', content: 'Accomplis de nombreuses quêtes secondaires pour les guildes, villages et personnages.' }
      ]
    },
    {
      label: 'Personnages',
      subItems: [
        { label: 'Dovahkiin', content: 'Le héros légendaire, porteur de la voix et du destin de Bordeciel.' },
        { label: 'Alduin', content: 'Le dragon destructeur, menace ancestrale réveillée pour ravager le monde.' }
      ]
    },
    {
      label: 'Objets',
      subItems: [
        { label: 'Armes', content: 'Épée d\'Ebène, Arc de Daedra, haches légendaires… Trouve l\'arme qui te convient !' },
        { label: 'Potions', content: 'Soigne-toi, fortifie tes compétences, ou deviens invisible grâce aux potions magiques.' }
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
