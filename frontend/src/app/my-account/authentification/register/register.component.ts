import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegisterService } from './register.service';
import { ToastLimiterService } from '../../../services/toast-limiter.service';
import { FloatLabel } from 'primeng/floatlabel';
import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import { DividerModule } from 'primeng/divider';
import { KeyFilterModule } from 'primeng/keyfilter';

@Component({
    standalone: true,
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css'],
    imports: [
        CommonModule,
        RouterLink,
        FormsModule,
        InputTextModule,
        FloatLabel,
        PasswordModule,
        DividerModule,
        KeyFilterModule
    ],
    providers: [RegisterService, ToastLimiterService]
})
export class RegisterComponent {
    userName = '';
    email = '';
    name = '';
    lastName = '';
    password = '';
    confirmPassword = '';
    notification: string = '';

    alphabeticPattern = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]*$/;

    constructor(
        @Inject(RegisterService) private registerService: RegisterService,
        private router: Router,
        private toastLimiter: ToastLimiterService
    ) { }

    /**
     * Maneja el evento de registro del usuario.
     * Valida los datos ingresados y, si son válidos, llama al servicio de registro.
     * Si el registro es exitoso, redirige al usuario a la página de inicio de sesión con un mensaje de bienvenida.
     * Si la validación falla, muestra un mensaje de error en un toast.
     */
    registerUser(): void {
        if (!this.validateData(this.userName, this.email, this.name, this.lastName, this.password, this.confirmPassword)) {
            this.toastLimiter.error('Error de validación 🚨', this.notification, 5000);
            return;
        }

        this.registerService.register(this.userName, this.email.toLowerCase().trim(), this.name, this.lastName, this.password)
            .subscribe({
                next: (response) => {
                    if (response.status === 201) {
                        this.toastLimiter.success('🎉 ¡Registro completado!', response.body?.message || '¡Bienvenido a la comunidad! Tu cuenta está lista y el camino está abierto para que empieces a explorar, crear y disfrutar. 🚀', 5000);
                        this.router.navigate(['/login'], { queryParams: { nombre: this.userName } });
                    } else {
                        this.toastLimiter.error('Error', response.body?.message || 'Error al registrar el usuario', 5000);
                    }
                },
                error: (errorResponse) => {
                    const errors = errorResponse.error?.errors;
                    if (errors && Array.isArray(errors)) {
                        let notification = 'Errores en el registro:';
                        for (const err of errors) {
                            notification += `\n -${err}`;
                        }
                        this.toastLimiter.error('Error', notification, 5000);
                    } else {
                        this.toastLimiter.error('Error', errorResponse.error?.error, 5000);
                    }
                }
            });
    }

    /**
     * Valida los datos ingresados por el usuario.
     * Comprueba que todos los campos estén completos y que cumplan con las reglas de formato.
     * Si hay errores, los agrega a la notificación y devuelve false.
     * @param userName Nombre de usuario ingresado.
     * @param email Email ingresado.
     * @param name Nombre ingresado.
     * @param lastName Apellido ingresado.
     * @param password Contraseña ingresada.
     * @param confirmPassword Confirmación de la contraseña ingresada.
     * @returns true si todos los datos son válidos, false en caso contrario.
     */
    validateData(userName: string, email: string, name: string, lastName: string, password: string, confirmPassword: string): boolean {
        let validated = true;
        let notification: string = 'Errores en el registro:';
        const allFieldsFilled = this.userName && this.email.toLowerCase() && this.name && this.lastName && this.password && this.confirmPassword;

        const nameRegex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{2,20}$/;
        const userNameRegex = /^.{3,20}$/;
        const passwordRegex = /^.{8,20}$/;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!allFieldsFilled) {
            notification += '\n -Quedan campos por completar'
            validated = false;
        } else {
            if (userName.trim() === '') {
                notification += '\n -El nombre de usuario está vacío.';
                validated = false;
            } else {
                if (!userNameRegex.test(this.userName)) {
                    notification += '\n -El nombre de usuario debe tener entre 3 y 20 caracteres.';
                    validated = false;
                }
            }

            if (email.trim() === '') {
                notification += '\n -El email está vacío.';
                validated = false;
            } else {
                if (!emailRegex.test(this.email)) {
                    notification += '\n -El email no tiene un formato válido.';
                    validated = false;
                }
            }

            if (name.trim() === '') {
                notification += '\n -El nombre está vacío.';
                validated = false;
            } else {
                if (!nameRegex.test(this.name)) {
                    notification += '\n -El nombre debe tener entre 2 y 20 caracteres y solo puede contener letras y espacios.';
                    validated = false;
                }
            }

            if (lastName.trim() === '') {
                notification += '\n -Los apellidos están vacíos.';
                validated = false;
            } else {
                if (!nameRegex.test(this.lastName)) {
                    notification += '\n -El apellido debe tener entre 2 y 20 caracteres y solo puede contener letras y espacios.';
                    validated = false;
                }
            }

            if (password.trim() === '') {
                notification += '\n -La contraseña está vacía.';
                validated = false;
            } else {
                if (!passwordRegex.test(this.password)) {
                    notification += '\n -La contraseña debe tener entre 8 y 20 caracteres.';
                    validated = false;
                }
            }

            if (confirmPassword.trim() === '') {
                notification += '\n -La confirmación de la contraseña está vacía.';
                validated = false;
            }

            if (this.password !== this.confirmPassword) {
                notification += '\n -Las contraseñas no coinciden.';
                validated = false;
            }
        }

        this.notification = notification;
        return validated;
    }
}

