import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { sacarHeader, sacarHeaderSinContentType } from '../../app.config';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class UpdateZoneService {
    private apiUrl = environment.apiUrl + '/v1/content';
    constructor(private http: HttpClient) { }

    /**
     * Obtiene el encabezado de autorización con el token de la cookie 'token'.
     * @returns HttpHeaders con el token de autorización.
     */
    private getAuthHeader(): HttpHeaders {
        const cookies = document.cookie.split('; ');
        const tokenCookie = cookies.find((row) => row.startsWith('token='));
        const token = tokenCookie ? tokenCookie.split('=')[1] : null;
        let headers = new HttpHeaders();
        if (token) {
            headers = headers.set('Authorization', `Bearer ${token}`);
        }
        return headers;
    }

    /**
     * Publica una nueva aplicación con la información proporcionada.
     * @param title Título de la aplicación.
     * @param description Descripción de la aplicación.
     * @param category Categoría de la aplicación.
     * @returns Observable con la respuesta del servidor.
     */
    getAppInfoById(id: number): Observable<any> {
        const headers: HttpHeaders = this.getAuthHeader();
        return this.http.get<any>(`${this.apiUrl}/${id}`, { headers });
    }


    /**
     * Obtiene la imagen de la aplicación por su ID.
     * @param id ID de la aplicación.
     * @returns 
     */
    getAppImageById(id: number) {
        const headers: HttpHeaders = this.getAuthHeader();
        return this.http.get(environment.apiUrl + `/v1/content/${id}/icon`, { responseType: 'blob', headers });
    }

    /**
     * Obtiene el archivo de la aplicación por su ID.
     * @param id ID de la aplicación.
     * @returns 
     */
    getAppFileById(id: number) {
        const headers: HttpHeaders = this.getAuthHeader();
        return this.http.get(environment.apiUrl + `/v1/content/${id}/file`, { responseType: 'blob', observe: 'response', headers });
    }

    /**
     * Actualiza la información de una aplicación por su ID.
     * @param id
     * @param title  
     * @param description 
     * @param category 
     * @returns 
     */
    updateAppInfoById(id: number, title: string, description: string, category: string) {
        const headers: HttpHeaders = sacarHeader();
        return this.http.put(`${this.apiUrl}/${id}`, { title, description, category }, { headers });
    }

    /**
     * Actualiza la imagen de la aplicación por su ID.
     * @param id ID de la aplicación.
     * @param imageFile Archivo de imagen a subir.
     * @returns Observable con la respuesta del servidor.
     */
    updateAppImageById(id: number, imageFile: File) {
        const formData = new FormData();
        formData.append('file', imageFile); // campo 'icon'
        const headers = this.getAuthHeader();
        return this.http.put<{ message: string }>(
            `${this.apiUrl}/${id}/icon`,
            formData,
            {
                headers,
                observe: 'response'
            }
        )
    }

    /**
     * Actualiza el archivo de la aplicación por su ID.
     * @param id ID de la aplicación.
     * @param file Archivo a subir.
     * @returns Observable con la respuesta del servidor.
     */
    updateAppFileById(id: number, file: File) {
        const formData = new FormData();
        formData.append('file', file); // campo 'file'
        const headers = this.getAuthHeader();
        return this.http.put<{ message: string }>(
            `${this.apiUrl}/${id}/file`,
            formData,
            {
                headers,
                observe: 'response'
            }
        );
    }

    /**
     * Obtiene la imagen de previsualización por su id.
     * @param imageId ID de la imagen.
     * @returns Observable con el blob de la imagen.
     */
    getPreviewImageById(imageId: number) {
        const headers: HttpHeaders = this.getAuthHeader();
        return this.http.get(environment.apiUrl + `/v1/content-media/${imageId}`, { responseType: 'blob', headers });
    }

    /**
     * Elimina un medio (imagen/video) por su ID.
     * @param mediaId ID del medio a eliminar.
     * @returns Observable con la respuesta del servidor.
     */
    deleteContentMediaById(mediaId: number): Observable<any> {
        const headers: HttpHeaders = sacarHeaderSinContentType();
        return this.http.delete(`${environment.apiUrl}/v1/content-media/${mediaId}`, { headers });
    }

    /**
     * Subida de una imagen
     * @param appId Id de la app
     * @param file Archivo que se subira
     * @returns 
     */
    uploadPreviewImage(appId: number, file: File): Observable<any> {
        const formData = new FormData();
        formData.append('file', file);

        const headers = this.getAuthHeader(); // Añadir autenticación

        return this.http.post(`${environment.apiUrl}/v1/content/${appId}/media`, formData, {
            headers,
            observe: 'response'
        });
    }


}
