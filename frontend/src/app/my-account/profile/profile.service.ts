import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Users } from '../../users';
import { sacarHeader } from '../../app.config';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root',
})
export class ProfileService {
    private urlEndPoint = environment.apiUrl+'/v1/profile';

    constructor(private http: HttpClient) { }

    /**
     * Obtiene el perfil del usuario autenticado. 
     * @returns Observable<HttpResponse<Users>> con la respuesta del servidor.
     */
    getProfile(): Observable<HttpResponse<Users>> {
        const headers: HttpHeaders = sacarHeader();

        return this.http.get<Users>(this.urlEndPoint, { headers, observe: 'response' });
    }
    
    /**
     * Actualiza el perfil del usuario autenticado.
     * @returns Observable<HttpResponse<any>> con la respuesta del servidor.
     */
    updateProfile( email: string, firstName: string, lastName: string): Observable<HttpResponse<any>> {
        const headers: HttpHeaders = sacarHeader();

        return this.http.put<any>(
            this.urlEndPoint,
            { email, firstName, lastName },
            { headers , observe: 'response'}
        );
    }
}
