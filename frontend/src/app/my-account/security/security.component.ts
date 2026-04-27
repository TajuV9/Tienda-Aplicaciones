import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { SecurityService } from './security.service';
import { ToastLimiterService } from '../../services/toast-limiter.service';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { DividerModule } from 'primeng/divider';

@Component({
  selector: 'app-security',
  standalone: true,
  imports: [CommonModule, FormsModule, FloatLabel, InputTextModule, PasswordModule, DividerModule],
  providers: [SecurityService, ToastLimiterService],
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.css']
})
export class SecurityComponent {
  currentPassword = '';
  newPassword = '';
  confirmPassword = '';

  constructor(@Inject(SecurityService) private securityService: SecurityService, private toastLimiter: ToastLimiterService) { }

  /**
   * Cambia la contraseña del usuario.
   * Valida que los campos no estén vacíos, que las nuevas contraseñas coincidan y que la nueva contraseña tenga una longitud válida.
   * Si las validaciones son exitosas, llama al servicio de seguridad para cambiar la contraseña.
   * @param form El formulario que contiene los campos de contraseña.
   */
  changePassword(form: NgForm): void {
    if (!this.currentPassword || !this.newPassword || !this.confirmPassword) {
      this.toastLimiter.error('Error', 'Por favor, completa todos los campos', 5000);
      return;
    }

    if (this.newPassword !== this.confirmPassword) {
      this.toastLimiter.warn('Contraseñas no coinciden', '¡La nueva contraseña y su confirmación no son iguales! Revísalas e inténtalo de nuevo. 🔁', 5000);
      return;
    }

    if (this.newPassword.length < 8 || this.newPassword.length > 20) {
      this.toastLimiter.error('Error', 'La nueva contraseña tiene que ser entre 8 y 20 caracteres', 5000);
      return;
    }

    this.securityService.changePassword(this.currentPassword, this.newPassword).subscribe({
      next: () => {
        this.toastLimiter.success('🔐 ¡Contraseña actualizada!', 'Tu nueva contraseña ha sido guardada con éxito. 🔒', 5000);

        form.resetForm();
      },
      error: (errorResponse) => {
        const { message, errors } = errorResponse.error;

        if (errors && Array.isArray(errors) && errors.length > 0) {
          const notification = errors.map((err: string) => `- ${err}`).join('\n');
          this.toastLimiter.error('Error', notification, 5000);
        } else if (message) {
          this.toastLimiter.error('Error', message, 5000);
        } else {
          this.toastLimiter.error('Error', 'Error desconocido al cambiar la contraseña', 5000);
        }

      }
    });

  }
}
