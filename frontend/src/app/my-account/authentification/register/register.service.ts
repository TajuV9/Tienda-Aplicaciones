import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpHandler, HttpResponse } from '@angular/common/http';
import { Users } from '../../../users';
import { environment } from '../../../../environments/environment';


@Injectable({
    providedIn: 'root'
})
export class RegisterService {

    tokenKey: string = 'authToken';
    private urlEndPoint: string = environment.apiUrl+'/v1/auth/signup';
    private httpheaders = new HttpHeaders({ 'Content-Type': 'application/json' });

    constructor(private http: HttpClient) { }

    /**
     * Registra un nuevo usuario.
     * @param userName Nombre de usuario.
     * @param email Correo electrónico del usuario.
     * @param firstName Nombre del usuario.
     * @param lastName Apellido del usuario.
     * @param password Contraseña del usuario.
     * @returns Observable<HttpResponse<{ message: string }>> con la respuesta del servidor.
     */
    register(
        userName: string,
        email: string,
        firstName: string,
        lastName: string,
        password: string
    ): Observable<HttpResponse<{ message: string }>> {
        return this.http.post<{ message: string }>(
            this.urlEndPoint,
            { userName, email, firstName, lastName, password },
            {
                headers: this.httpheaders,
                observe: 'response'
            }
        );
    }
}