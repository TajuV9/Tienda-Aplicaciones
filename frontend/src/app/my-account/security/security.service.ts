import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Users } from '../../users';
import { sacarHeader } from '../../app.config';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root',
})
export class SecurityService {
    private urlEndpoint = environment.apiUrl+'/v1/profile/change-password';

    constructor(private http: HttpClient) { }

    /**
     * Cambia la contraseña del usuario autenticado.
     * @param oldPassword Contraseña actual del usuario.
     * @param newPassword Nueva contraseña que se desea establecer.
     * @returns Observable<HttpResponse<any>> con la respuesta del servidor.
     */
    changePassword(oldPassword: string, newPassword: string): Observable<HttpResponse<any>> {
        const headers: HttpHeaders = sacarHeader();

        return this.http.post<any>(
            `${this.urlEndpoint}`,
            { oldPassword, newPassword },
            { headers, observe: 'response' }
        );
    }

}
