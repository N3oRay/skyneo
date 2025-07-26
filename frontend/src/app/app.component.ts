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
      label: 'Le numérique responsable',
      subItems: [
        { label: 'C est quoi ?', content: "C'est une démarche qui vise à réduire notre impact environnemental liée à l'utisation technologiques. Actuellement nous devons être acteur, et avoir un comportement eco responsable, afin de limiter notre impact sur l'environnement." },
        { label: 'Pourquoi ?', content: "Nous devons protéger notre environnement, nous devons préserver notre planet et pensez aux générations futur. Car le numérique est un secteur polluant qui émet du CO2. 50% est dû au fonctionnement d'internet. 50% est dû à la fabrication de nos appareils. Au total il représente 4% des émissions des gaz à effects de serre mondial. Au total il représente 1,5 fois plus de pollution que le transport aérien. Au total il représente 34 millards d'appareils, pour 4,1 millards d'utilisateurs."  },
        { label: 'Conclusion', content: "Le numérique responsable est indispensable pour la préservation de notre environnement. C'est une démarche qui est accessible à tout le monde. Nous pouvons limiter notre impact en adoptant un comportement responsable, et en limitant notre consommation. A propos des écogestes simples : Réparer ses appareils endommagés. Déconnecter nos appareils, éviter de laisser en veille. Effacer régulièrement ses mails. Favoriser le Wifi à la 4G. "  },
        { label: 'Label Eco responsable', content: "Pour préserver notre environnement, vous pouvez faire l'acquisition d'un label pour les entreprises: 'NR: Label numérique reponsable, Blue Angel: The german ecolabel, Nordic swan ecolabel"  }
      ]
    },
    {
      label: 'Parc Informatique',
      subItems: [
        { label: 'Principale', content: 'Site principale, Bureaux. Conseils pratiques numérique responsable.' },
        { label: 'Secondaires', content: 'Site distants, Clouds, Herbergements. Conseils pour être numérique responsable.' }
      ]
    },
    {
      label: 'Infrastructures',
      subItems: [
        { label: 'Routeur', content: 'Listes des équippements réseaux. Les élements imporants de vos infrastructures.' },
        { label: 'Firewall', content: 'Listes des équippements de protections, contrôle et accès. Mieux dimensionner ses équippements.' },
        { label: 'GPS', content: 'Antennes, et locations.' },
        { label: 'Climatisations', content: "Listes des équippements de ventilisations et climatisations. La consommation en énergie de vos équippements à une impact sur l'environment." }
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
