import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

@Component({
    selector: 'app-search-bar',
    imports: [CommonModule, RouterLink, FormsModule],
    templateUrl: './search-bar.component.html',
    styleUrl: './search-bar.component.css'
})
export class SearchBarComponent {
    isHomeRoute: boolean = false;
    searchContent: string = '';

    // Lista de rutas exactas
    private staticRoutes: string[] = ['/login', '/register', '/my-account', '/my-downloads', 'myFiles', 'terms-and-conditions', 'creator-zone', '/creator-zone', '/creator-dashboard', '/creator-dashboard/my-files', '/creator-dashboard/show-content', '/creator-dashboard/creator-zone'];

    // Lista de patrones dinámicos (como expresiones regulares simples)
    private dynamicRoutes: RegExp[] = [
        /^\/archive\/[^\/]+\/[^\/]+\/\d+(?:\?.*)?$/,
        /^\/archive\/\d+$/
    ];

    /**
     * Constructor del componente de la barra de búsqueda.
     * Suscribe a los eventos del router para determinar si la ruta actual es una de las rutas estáticas o dinámicas.
     * Si la ruta no coincide con ninguna de ellas, se considera que es la ruta de inicio.
     */
    constructor(private router: Router) {
        this.router.events.subscribe(() => {
            const url = this.router.url;
            const isStaticHidden = this.staticRoutes.includes(url);
            const isDynamicHidden = this.dynamicRoutes.some((pattern) => pattern.test(url));

            this.isHomeRoute = !(isStaticHidden || isDynamicHidden);
        });
    }

    /**
     * Método para manejar el evento de búsqueda.
     * Navega a la ruta de búsqueda con el contenido ingresado.
     */
    search() {
        if (this.searchContent.trim()) {
            this.router.navigate(['/search', this.searchContent.trim()]);
        }
    }

}