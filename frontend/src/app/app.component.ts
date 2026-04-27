import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { NavbarComponent } from "./navbar/navbar.component";
import { CommonModule } from '@angular/common';
import { NavLeftComponent } from "./nav-left/nav-left.component";
import { SearchBarComponent } from "./search-bar/search-bar.component";
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { FooterComponent } from "./footer/footer.component";
import { filter } from 'rxjs/operators';
import { environment } from '../environments/environment';

@Component({
    selector: 'app-root',
    imports: [CommonModule, NavbarComponent, NavLeftComponent, RouterOutlet, ToastModule, FooterComponent],
    providers: [MessageService],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css'
})
export class AppComponent {
    showSidebar: boolean = true;


    private rutasVisibles = ['/home', '/application', '/music', '/videos', '/books'];

    /**
     * Constructor del componente principal de la aplicación.
     * Suscribe a los eventos de navegación para determinar si se debe mostrar la barra lateral.
     */
    constructor(private router: Router) {
        this.router.events
            .pipe(filter(event => event instanceof NavigationEnd))
            .subscribe((event: NavigationEnd) => {
                this.showSidebar = this.rutasVisibles.includes(event.urlAfterRedirects);
            });
    }

    ngOnInit() {
        console.log('Modo producción:', environment.production);


    }

}
