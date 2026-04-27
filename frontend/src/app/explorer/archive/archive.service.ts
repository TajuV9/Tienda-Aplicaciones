import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { sacarHeader, sacarHeaderOpcional, sacarHeaderSinContentType } from '../../app.config';
import { Users } from '../../users';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ArchiveService {
    private apiUrl = environment.apiUrl + '/v1/content';
    private urlProfile = environment.apiUrl + '/v1/profile';

    constructor(private http: HttpClient) { }

    /**
     * Obtiene el contenido de la API basado en la categoría, página, tamaño de página y orden.
     * @param id Identificador del archivo a obtener.
     * @returns 
     */
    getArchiveById(id: number): Observable<any> {

        return this.http.get<any>(`${this.apiUrl}/${id}`, {
        });
    }

    /**
     * Obtiene una lista de archivos con paginación y filtrado.
     * @param id Identificador del archivo a obtener.
     * @returns Observable con una lista de archivos.
     */
    getArchiveFile(id: number): Observable<HttpResponse<Blob>> {
        const headers: HttpHeaders = sacarHeader();

        return this.http.get(`${this.apiUrl}/${id}/file`, {
            headers,
            observe: 'response',
            responseType: 'blob'
        });
    }

    /**
     * Obtiene una lista de archivos con paginación y filtrado.
     * @param id Identificador del archivo a eliminar.
     * @returns 
     */
    deleteArchiveById(id: number): Observable<any> {
        const headers: HttpHeaders = sacarHeaderSinContentType();
        return this.http.delete(`${this.apiUrl}/${id}`, { headers })
    }

    /**
     * Obtiene una lista de archivos con paginación y filtrado.
     * @param id Identificador del archivo a obtener.
     * @returns Observable con una lista de archivos.
     */
    getReviews(id: Number): Observable<any> {
        const headers = sacarHeaderOpcional();

        const params = new HttpParams()
            .set('page', '0')
            .set('pageSize', '8');

        const options: any = { params };

        if (headers) {
            options.headers = headers;
        }

        return this.http.get(`${this.apiUrl}/${id}/review`, options);
    }

    /**
     * Publica una reseña para un contenido específico.
     * @param contentId Identificador del contenido al que se le va a publicar la reseña.
     * @param review Texto de la reseña.
     * @param rating Calificación de la reseña.
     * @returns Observable<any> que representa la respuesta del servidor.
     */
    postReview(contentId: Number, review: string, rating: number): Observable<any> {

        const headers: HttpHeaders = sacarHeader();
        const body = { review, rating };

        return this.http.post(`${this.apiUrl}/${contentId}/review`, body, { headers });
    }

    /**
     * Actualiza una reseña existente para un contenido específico.
     * @param contentId Identificador del contenido al que se le va a actualizar la reseña.
     * @param review Texto de la reseña.
     * @param rating Calificación de la reseña.
     * @returns Observable<any> que representa la respuesta del servidor.
     */
    updateReview(contentId: Number, review: string, rating: number) {
        const headers: HttpHeaders = sacarHeader();

        const body = {
            review,
            rating
        };

        return this.http.put<any>(
            `${this.apiUrl}/${contentId}/review`,
            body,
            { headers }
        );
    }

    /**
     * Elimina una reseña para un contenido específico.
     * @param contentId Identificador del contenido al que se le va a eliminar la reseña.
     * @returns Observable<any> que representa la respuesta del servidor.
     */
    deleteReview(contentId: Number): Observable<any> {
        const headers: HttpHeaders = sacarHeader();

        return this.http.request('DELETE', `${this.apiUrl}/${contentId}/review`, {
            headers
        });
    }

    /**
     * Responde a una reseña específica.
     * @param reviewId Identificador de la reseña a la que se va a responder.
     * @param answerText Texto de la respuesta.
     * @returns Observable<any> que representa la respuesta del servidor.
     */
    answerReview(reviewId: number, answerText: string): Observable<any> {
        const headers = sacarHeader();
        const body = { answer: answerText };

        return this.http.put(environment.apiUrl + `/v1/review/${reviewId}/answer`, body, { headers });
    }

    /**
     * Elimina la respuesta a una reseña específica.
     * @param reviewId Identificador de la reseña cuya respuesta se va a eliminar.
     * @returns Observable<any> que representa la respuesta del servidor.
     */
    deleteReviewResponse(reviewId: number): Observable<any> {
        const headers: HttpHeaders = sacarHeader();

        return this.http.delete(environment.apiUrl + `/v1/review/${reviewId}/answer`, { headers });
    }

    /**
     * Obtiene el perfil del usuario autenticado.
     * @returns Observable<HttpResponse<Users>> con la respuesta del servidor.
     */
    getProfile(): Observable<HttpResponse<Users>> {
        const headers: HttpHeaders = sacarHeader();

        return this.http.get<Users>(this.urlProfile, { headers, observe: 'response' });
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

}
