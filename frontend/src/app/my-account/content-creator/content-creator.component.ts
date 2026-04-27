import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CreatorStateService } from './creator-state.service';
import { ToastLimiterService } from '../../services/toast-limiter.service';
import { ContentCreatorService } from './content-creator.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-content-creator',
  templateUrl: './content-creator.component.html',
  styleUrls: ['./content-creator.component.css'],
  imports: [FormsModule, CommonModule],
  providers: [ToastLimiterService]
})
export class ContentCreatorComponent {
  acceptedTerms = false;
  warningMessage = '';

  constructor(
    private router: Router,
    public creatorState: CreatorStateService,
    private toastLimiter: ToastLimiterService,
    private profileService: ContentCreatorService
  ) { }

  /**
   * Activa el modo creador de contenido para el usuario.
   * Valida si el usuario ha aceptado los términos y condiciones.
   * @returns {boolean} Verdadero si el usuario ha aceptado los términos y condiciones, falso en caso contrario.
   */
  activateCreator() {
    if (!this.acceptedTerms) {
      this.warningMessage = 'Debes aceptar los términos y condiciones para continuar.';
      return;
    }

    this.warningMessage = '';

    this.profileService.becomeCreator().subscribe({
      next: (response) => {
        this.creatorState.acceptedTerms = true;
        this.toastLimiter.info('🎉 ¡Estás dentro!', response.message || 'Ahora eres un creador de contenido 🚀', 5000);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        if (err.status === 401) {
          this.warningMessage = 'Debes iniciar sesión para activar el modo creador.';
        } else if (err.status === 409) {
          this.warningMessage = 'Ya eres un creador de contenido.';
        } else {
          this.warningMessage = 'Hubo un error al activar el modo creador. Intenta más tarde.';
        }
      }
    });
  }
}
