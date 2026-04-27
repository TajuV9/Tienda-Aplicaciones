import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { sacarHeader } from '../../../app.config';
import { Users } from '../../../users';
import { environment } from '../../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class LoginService {
    register(arg0: { userName: string; email: string; name: string; lastName: string; password: string; }) {
        throw new Error('Method not implemented.');
    }

    tokenKey: string = 'authToken'; // Nombre de la cookie para el token
    private urlEndPoint: string = environment.apiUrl+'/v1/auth/login';
    private httpheaders = new HttpHeaders({ 'Content-Type': 'application/json' });

    constructor(private http: HttpClient) { }

    /**
     * Inicia sesión con las credenciales del usuario.
     * @param username Nombre de usuario.
     * @param password Contraseña del usuario.
     * @param keepLoggedIn Indica si se debe mantener la sesión iniciada.
     * @returns Observable<HttpResponse<any>> con la respuesta del servidor.
     */
    login(username: string, password: string, keepLoggedIn: boolean): Observable<HttpResponse<any>> {
        return this.http.post<any>(
            this.urlEndPoint,
            { username, password, keepLoggedIn },
            {
                headers: this.httpheaders,
                observe: 'response'
            }
        );
    }

    /**
     * Obtiene el perfil del usuario autenticado.
     * @returns Observable<HttpResponse<Users>> con la respuesta del servidor.
     */
    getProfile(): Observable<HttpResponse<Users>> {
        const headers: HttpHeaders = sacarHeader();

        return this.http.get<Users>(environment.apiUrl+'/v1/profile', { headers, observe: 'response' });
    }

}