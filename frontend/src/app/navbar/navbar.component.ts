import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NavigationEnd, RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { CreatorStateService } from '../my-account/content-creator/creator-state.service';
import { NavbarService } from './navbar.service';
import { filter } from 'rxjs';
import { ToastLimiterService } from '../services/toast-limiter.service';
import { InputTextModule } from 'primeng/inputtext';
import { IftaLabelModule } from 'primeng/iftalabel';
import { KeyFilterModule } from 'primeng/keyfilter';

@Component({
    selector: 'app-navbar',
    standalone: true,
    imports: [RouterLink, CommonModule, InputTextModule, IftaLabelModule, KeyFilterModule],
    providers: [ToastLimiterService],
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
}) 
export class NavbarComponent {
    name: string = '';
    role: string = '';
    isUserCreator: boolean = false;

    constructor(
        private router: Router,
        public creatorState: CreatorStateService,
        private navbarService: NavbarService,
        private toastLimiter: ToastLimiterService
    ) {
        // Comprobar si el usuario está logueado al iniciar el componente
        this.router.events.pipe(
            filter(event => event instanceof NavigationEnd)
        ).subscribe(() => {
            if (this.isLoggedIn()) {
                this.loadProfileData();
            } else {
                this.role = '';
                this.name = '';
                this.isUserCreator = false;
            }
        });
    }
    
    ngOnInit() {
        this.loadProfileData();
    }

    /**
     * Verifica si el usuario está logueado comprobando la existencia de una cookie de token.
     * @returns {boolean} Verdadero si el usuario está logueado, falso en caso contrario.
     */
    isLoggedIn() {
        const tokenCookie = document.cookie.split('; ').find(row => row.startsWith('token='));
        return !!tokenCookie;
    }

    /**
     * Verifica si el usuario ha aceptado los términos y condiciones.
     * @returns {boolean} Verdadero si los términos han sido aceptados, falso en caso contrario.
     */
    get acceptedTerms() {
        return this.creatorState.acceptedTerms;
    }

    /**
     * Carga los datos del perfil del usuario logueado.
     * Si el usuario es un creador, se actualiza el estado correspondiente.
     */
    loadProfileData(): void {
        this.navbarService.getProfile().subscribe({
            next: (profile) => {
                this.name = profile.body?.userName ?? '';
                this.role = profile.body?.role ?? '';
                this.isUserCreator = this.role === 'CREATOR';
            }
        });
    }

    /**
     * Maneja el evento de clic en el botón de cerrar sesión.
     * Limpia las cookies, el estado local y redirige al usuario a la página de inicio.
     */
    logout() {
        document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';

        this.name = '';
        this.role = '';
        this.isUserCreator = false;

        // Mostrar mensaje de éxito
        this.toastLimiter.success('👋 ¡Has cerrado sesión correctamente!', 'Esperamos verte pronto de nuevo. 😊', 5000);

        // Redirigir
        this.router.navigate(['/login']);
    }

}
