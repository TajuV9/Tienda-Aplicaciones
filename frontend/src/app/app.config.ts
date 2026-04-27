import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { HttpHeaders, provideHttpClient } from '@angular/common/http';
import { LoginGuardian } from './my-account/authentification/login/login-guardian';

import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';

export const appConfig: ApplicationConfig = {
    providers: [
        provideZoneChangeDetection({ eventCoalescing: true }),
        provideRouter(routes),
        provideHttpClient(),
        LoginGuardian,
        provideAnimationsAsync(),
        providePrimeNG({
            theme: {
                preset: Aura
            }
        })
    ]
};




/**
 * Genera un objeto HttpHeaders con el token de autenticación.
 * @returns HttpHeaders con el token de autenticación.
 */
export function sacarHeader(): HttpHeaders {
    const token = sacarToken("token");


    if (!token) {
        throw new Error('Token de autenticación no encontrado');
    }

    const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    });

    return headers;

}

/**
 * Genera un objeto HttpHeaders opcional con el token de autenticación.
 * @returns HttpHeaders | null
 */
export function sacarHeaderOpcional(): HttpHeaders | null {
    const token = sacarToken("token");

    if (!token) return null;

    return new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Accept': '*/*'
    });
}

/**
 * Genera un objeto HttpHeaders para solicitudes GET con el token de autenticación.
 * @returns HttpHeaders
 */
export function sacarHeaderGet(): HttpHeaders {
    const token = sacarToken("token");


    if (!token) {
        throw new Error('Token de autenticación no encontrado');
    }

    const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Accept': '*/*'
    });

    return headers;

}

/**
 * Genera un objeto HttpHeaders sin el Content-Type, pero con el token de autenticación.
 * @returns HttpHeaders
 */
export function sacarHeaderSinContentType(): HttpHeaders {
    const token = sacarToken("token");

    if (!token) {
        throw new Error('Token de autenticación no encontrado');
    }

    return new HttpHeaders({
        'Authorization': `Bearer ${token}`
        // No pongas 'Content-Type'
    });
}

/**
 * Obtiene el valor de una cookie por su nombre.
 * @param name Nombre de la cookie que se desea obtener.
 * @returns El valor de la cookie si existe, o null si no se encuentra.
 */
export function sacarToken(name: string) {
    const cookies = document.cookie.split('; ');
    for (let c of cookies) {
        const [key, value] = c.split('=');
        if (key === name) return decodeURIComponent(value);
    }
    return null;
}

export function isLoggedIn() {
    const tokenCookie = document.cookie.split('; ').find(row => row.startsWith('token='));
    return !!tokenCookie;
}