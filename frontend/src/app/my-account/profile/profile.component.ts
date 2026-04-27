import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProfileService } from './profile.service';
import { ToastLimiterService } from '../../services/toast-limiter.service';
import { InputTextModule } from 'primeng/inputtext';
import { IftaLabelModule } from 'primeng/iftalabel';
import { KeyFilterModule } from 'primeng/keyfilter';

@Component({
    standalone: true,
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css'],
    imports: [CommonModule, FormsModule, InputTextModule, IftaLabelModule, KeyFilterModule],
    providers: [ToastLimiterService]
})
export class ProfileComponent implements OnInit {
    userName = '';
    email = '';
    name = '';
    lastName = '';

    originalEmail = '';
    originalName = '';
    originalLastName = '';

    alphabeticPattern = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]*$/;

    isEditing = false;

    constructor(@Inject(ProfileService) private profileService: ProfileService, private toastLimiter: ToastLimiterService) { }

    ngOnInit(): void {
        this.loadProfileData();
    }

    /**
     * Carga los datos del perfil del usuario desde el servicio de perfil.
     * Si la carga es exitosa, inicializa los campos del formulario con los datos del perfil.
     * Si ocurre un error, muestra un mensaje de error en un toast.
     */
    loadProfileData(): void {
        this.profileService.getProfile()
            .subscribe({
                next: (profile) => {
                    this.userName = profile.body?.userName ?? '';
                    this.email = profile.body?.email ?? '';
                    this.name = profile.body?.firstName ?? '';
                    this.lastName = profile.body?.lastName ?? '';

                    // Guardar valores originales para comparación
                    this.originalEmail = this.email;
                    this.originalName = this.name;
                    this.originalLastName = this.lastName;
                },
                error: () => {
                    this.toastLimiter.error('Error', 'Error al cargar los datos del perfil', 5000);
                },
            });
    }

    /**
     * Inicia el modo de edición del perfil.
     */
    cancelEdit(): void {
        this.isEditing = false;
        this.loadProfileData(); // restaurar datos originales
    }

    /**
     * Actualiza el perfil del usuario con los datos ingresados en el formulario.
     * @returns Verdadero si el usuario está editando su perfil, falso en caso contrario.
     */
    updateProfile(): void {
        if (
            this.email === this.originalEmail &&
            this.name === this.originalName &&
            this.lastName === this.originalLastName
        ) {
            this.toastLimiter.warn('Sin cambios detectados', 'No se han realizado cambios en el perfil, por lo que no se puede actualizar. 💾', 5000);
            return;
        }

        const { isValid, errorMessage } = this.validateProfileData(this.userName, this.name, this.lastName, this.email);

        if (!isValid) {
            this.toastLimiter.error('Error de validación', errorMessage, 5000);
            return;
        }

        this.profileService.updateProfile(this.email, this.name, this.lastName)
            .subscribe({
                next: () => {
                    this.originalEmail = this.email;
                    this.originalName = this.name;
                    this.originalLastName = this.lastName;

                    this.toastLimiter.success('Perfil actualizado', 'Tus cambios se han guardado correctamente. ¡Todo listo y al día! 🛠️', 5000);
                },
                error: (errorResponse) => {
                    const { message, errors } = errorResponse.error;

                    if (errors && Array.isArray(errors) && errors.length > 0) {
                        const notification = errors.map((err: string) => `- ${err}`).join('\n');
                        this.toastLimiter.error('Error', notification, 5000);
                    } else if (message) {
                        this.toastLimiter.error('Error', message, 5000);
                    } else {
                        this.toastLimiter.error('Error', 'Error al actualizar perfil', 5000);
                    }
                }
            });
    }

    /**
     * Valida los datos del perfil del usuario.
     * Comprueba que el nombre y apellido tengan entre 3 y 50 caracteres,
     * que solo contengan letras y espacios, y que el correo electrónico sea válido.
     * @param name Nombre del usuario.
     * @param lastName Apellido del usuario.
     * @param email Correo electrónico del usuario.
     * @returns Un objeto con isValid (booleano) y errorMessage (string).
     */
    private validateProfileData(userName: string, name: string, lastName: string, email: string): { isValid: boolean; errorMessage: string } {
        let errors = 'Errores en el perfil:';
        let isValid = true;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!userName || userName.length < 3 || userName.length > 20) {
            errors += '\n -El nombre de usuario tiene que tener entre 3 y 20 caracteres.';
            isValid = false;
        }

        if (!name || name.length < 2 || name.length > 20) {
            errors += '\n -El nombre tiene que tener entre 2 y 20 caracteres.';
            isValid = false;
        }

        if (!this.alphabeticPattern.test(name)) {
            errors += '\n -El nombre solo puede contener letras y espacios.';
            isValid = false;
        }

        if (!lastName || lastName.length < 2 || lastName.length > 20) {
            errors += '\n -El apellido tiene que tener entre 2 y 20 caracteres.';
            isValid = false;
        }

        if (!this.alphabeticPattern.test(lastName)) {
            errors += '\n -El apellido solo puede contener letras y espacios.';
            isValid = false;
        }

        if (!email || !emailRegex.test(email)) {
            errors += '\n -El correo electrónico no tiene un formato válido.';
            isValid = false;
        }

        return { isValid, errorMessage: errors };
    }


}