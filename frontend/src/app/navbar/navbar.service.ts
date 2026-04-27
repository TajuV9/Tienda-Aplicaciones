import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Users } from '../users';
import { sacarHeader, sacarHeaderOpcional } from '../app.config';
import { environment } from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class NavbarService {

    private urlEndPoint = environment.apiUrl+'/v1/profile';

    constructor(private http: HttpClient) { }

    /**
     * Obtiene el perfil del usuario autenticado.
     * @returns Observable<HttpResponse<Users>> con la respuesta del servidor.
     */
    getProfile(): Observable<HttpResponse<Users>> {
        const headers = sacarHeaderOpcional();

        const options = headers
            ? { headers, observe: 'response' as const }
            : { observe: 'response' as const };

        return this.http.get<Users>(this.urlEndPoint, options);
    }
}
