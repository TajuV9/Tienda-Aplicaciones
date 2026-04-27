import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { sacarHeader } from '../../app.config';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ContentCreatorService {
    private apiUrl = environment.apiUrl+'/v1/profile/become-creator';

    constructor(private http: HttpClient) { }

    /**
     * Solicita al usuario convertirse en creador de contenido.
     * @returns Observable<any> con la respuesta del servidor.
     */
    becomeCreator(): Observable<any> {

        const headers: HttpHeaders = sacarHeader();

        return this.http.post(`${this.apiUrl}`, null, { headers });
    }

}
