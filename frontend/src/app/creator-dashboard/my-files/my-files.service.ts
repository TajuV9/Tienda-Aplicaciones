import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { sacarHeader } from '../../app.config';
import { environment } from "../../../environments/environment";


@Injectable({
    providedIn: 'root'
})
export class MyFilesService {

    private urlEndPoint: string = environment.apiUrl+'/v1/content';

    constructor(private http: HttpClient) { }

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
     * Obtiene los archivos del usuario autenticado.
     * @param page Número de página para paginación.
     * @param pageSize Tamaño de página para paginación.
     * @returns Observable con una lista de archivos del usuario.
     */
    publish(title: string, description: string, category: string) {
        const headers: HttpHeaders = sacarHeader();
        return this.http.post<{ message: string, id?: string }>(
            this.urlEndPoint,
            { title, description, category },
            {
                headers,
                observe: 'response'
            }
        );
    }

    /**
     * Obtiene la imagen del usuario autenticado.
     * @param contentId ID del contenido a obtener.
     * @param iconFile Imagen del icono a subir.
     * @returns Promise con la respuesta del servidor.
     */
    uploadIcon(contentId: string, iconFile: File) {
        const formData = new FormData();
        formData.append('file', iconFile);
        const headers = this.getAuthHeader();
        return this.http.put<{ message: string }>(
            `${this.urlEndPoint}/${contentId}/icon`,
            formData,
            {
                headers,
                observe: 'response'
            }
        ).toPromise();
    }

    /**
     * Obtiene el archivos del usuario autenticado.
     * @param contentId ID del contenido a obtener.
     * @param file Archivo a subir.
     * @returns Promise con la respuesta del servidor.
     */
    uploadFile(contentId: string, file: File) {
        const formData = new FormData();
        formData.append('file', file);
        const headers = this.getAuthHeader();
        return this.http.put<{ message: string }>(
            `${this.urlEndPoint}/${contentId}/file`,
            formData,
            {
                headers,
                observe: 'response'
            }
        ).toPromise();
    }
    
    /**
     * Sube imágenes de previsualización al contenido, una por una, como archivo (FormData).
     * @param contentId ID del contenido.
     * @param images Array de archivos de imagen.
     * @returns Promise con la respuesta del último upload.
     */
    async uploadPreviewImages(contentId: string, images: File[]) {
        let lastResponse: any = null;
        for (let idx = 0; idx < images.length; idx++) {
            const formData = new FormData();
            formData.append('file', images[idx]);
            const headers = this.getAuthHeader();
            lastResponse = await this.http.post<{ message: string }>(
                `${this.urlEndPoint}/${contentId}/media`,
                formData,
                {
                    headers,
                    observe: 'response'
                }
            ).toPromise();
        }
        return lastResponse;
    }

}