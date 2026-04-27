import { Component } from '@angular/core';
import { ProfileComponent } from '../profile/profile.component';
import { CommonModule } from '@angular/common';
import { SecurityComponent } from "../security/security.component";
import { ContentCreatorComponent } from "../content-creator/content-creator.component";
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';
import { NavbarService } from '../../navbar/navbar.service';
import { isLoggedIn } from '../../app.config';

@Component({
    selector: 'app-account-manager',
    imports: [CommonModule, ProfileComponent, SecurityComponent, ContentCreatorComponent],
    templateUrl: './account-manager.component.html',
    styleUrl: './account-manager.component.css'
})
export class AccountManagerComponent {
    
    activeSection: 'profile' | 'security' | 'creator' = 'profile';
    role: string = '';
    isUserCreator: boolean = false;

    /**
     * Suscribe a los eventos de navegación para cargar los datos del perfil del usuario actual.
     * @param router Router para manejar la navegación.
     * @param navbarService Servicio para obtener información del perfil del usuario.
     */
    constructor(private router: Router, private navbarService: NavbarService) 
    {
        this.router.events.pipe(
            filter(event => event instanceof NavigationEnd)
        ).subscribe(() => {
            if (isLoggedIn()) {
                this.loadProfileData();
            } else {
                this.role = '';
                this.isUserCreator = false;
            }
        });
    }

    /**
     * Carga los datos del perfil del usuario actual.
     * Utiliza el servicio NavbarService para obtener la información del perfil.
     */
    loadProfileData(): void {
        this.navbarService.getProfile().subscribe({
            next: (profile) => {
                this.role = profile.body?.role ?? '';
                this.isUserCreator = this.role === 'CREATOR';
            }
        });
    }
}
