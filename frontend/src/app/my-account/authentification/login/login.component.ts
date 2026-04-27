import { Component, Inject } from '@angular/core';
import { Router, RouterLink, ActivatedRoute } from '@angular/router';
import { LoginService } from './login.service';
import { FormsModule } from '@angular/forms';
import { Users } from '../../../users';
import { HttpClient } from '@angular/common/http';
import { ToastLimiterService } from '../../../services/toast-limiter.service';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabel } from 'primeng/floatlabel';
import { PasswordModule } from 'primeng/password';
import { ProfileService } from '../../profile/profile.service';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [RouterLink, FormsModule, InputTextModule, FloatLabel, PasswordModule],
    providers: [LoginService, HttpClient, ToastLimiterService],
    templateUrl: './login.component.html',
    styleUrl: './login.component.css'
})
export class LoginComponent {

    userName = '';
    password = '';
    checkbox: boolean = false;
    validate = true;
    notification: string = '';
    role: string = '';

    constructor(
        @Inject(LoginService) private loginService: LoginService,
        private router: Router,
        private toastLimiter: ToastLimiterService,
        private route: ActivatedRoute // <-- Añadido
    ) { }

    ngOnInit() {
        // Recoge el parámetro 'nombre' de la URL si existe
        this.route.queryParams.subscribe(params => {
            if (params['nombre']) {
                this.userName = params['nombre'];
            }
        });
    }

    /**
     * Maneja el evento de inicio de sesión.
     * Valida el nombre de usuario y la contraseña ingresados.
     * Si la validación falla, muestra un mensaje de error en un toast.
     * Si la validación es exitosa, llama al método hacerLogin para procesar el inicio de sesión.
     */
    login() {
        this.validate = this.validateNomPass(this.userName, this.password);

        if (!this.validate) {
            this.toastLimiter.error('Error de validación 🚨', this.notification, 5000);
        } else {
            this.hacerLogin();
        }
    }

    /**
     * Valida el nombre de usuario y la contraseña ingresados.
     * Comprueba que el nombre no esté vacío, tenga entre 3 y 30 caracteres,
     * y que la contraseña no esté vacía y tenga entre 8 y 100 caracteres.
     * Si hay errores, los agrega a la notificación y devuelve false.
     * @param name Nombre de usuario ingresado.
     * @param password Contraseña ingresada.
     */
    validateNomPass(userName: string, password: string): boolean {
        let validated = true;
        let notification: string = 'Errores en el inicio de sesión:';

        if (userName === '') {
            notification += "\n-El usuario está vacío.";
            validated = false;
        }

        if (userName && (userName.length < 3 || userName.length > 20)) {
            notification += "\n-El usuario debe tener entre 3 y 20 caracteres.";
            validated = false;
        }

        if (password === '') {
            notification += "\n-La contraseña está vacía.";
            validated = false;
        }

        if (password && (password.length < 8 || password.length > 20)) {
            notification += "\n-La contraseña debe tener entre 8 y 20 caracteres.";
            validated = false;
        }

        this.notification = notification;

        return validated;
    }

    /**
     * Maneja el inicio de sesión del usuario.
     * Envía una solicitud al servicio de login con el nombre de usuario, contraseña y estado del checkbox.
     * Si la respuesta es exitosa, guarda el token en una cookie y navega a la página de inicio.
     * Si hay un error, muestra un mensaje de error en un toast.
     * @param name Nombre de usuario ingresado.
     * @param password Contraseña ingresada.
     * @param checkbox Estado del checkbox de "Recordar sesión".
     */
    hacerLogin() {
        this.loginService.login(this.userName, this.password, this.checkbox).subscribe({
            next: (response) => {
                const token = response.body?.token;
                const expirationTime: Date = new Date(response.body?.expirationTime);
                if (token) {
                    document.cookie = `token=${token}; expires=${expirationTime.toUTCString()}; path=/;`;

                    this.loginService.getProfile()
                        .subscribe({
                            next: (profile) => {
                                this.toastLimiter.success('🔓 ¡Sesión iniciada!', '¡Bienvenido a bordo! 🌟', 5000);
                                this.router.navigate(['/home']);
                            },
                            error: () => {
                                this.toastLimiter.error('Error', 'Error al cargar los datos del perfil', 5000);
                                this.router.navigate(['/home']);
                            },
                        });

                } else {
                    this.toastLimiter.error('Error', 'No se recibió un token', 5000);
                }
            },
            error: (err) => {
                let detail = 'Error al iniciar sesión';

                if (err.status === 400) {
                    detail = 'Datos inválidos. Revisa el formulario.';
                } else if (err.status === 401) {
                    detail = 'Credenciales incorrectas o usuario bloqueado.';
                } else if (err.error) {
                    if (err.error.message) {
                        detail = err.error.message;
                    }
                    if (Array.isArray(err.error.errors) && err.error.errors.length > 0) {
                        detail += '\n' + err.error.errors.join('\n');
                    }
                }

                this.toastLimiter.error('Error', detail, 5000);
            }
        });
    }

}
