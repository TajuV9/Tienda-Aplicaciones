import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface AppContent {
    id: number;
    title: string;
    description: string;
    imageUrl: string;
    downloadUrl: string;
    creatorName: string;
    category: string;
    rating: number;
    downloads: number;
    published: boolean;
}

@Injectable({
    providedIn: 'root'
})
export class AppsService {

    private baseUrl = environment.apiUrl+'/v1/content';

    constructor(private http: HttpClient) { }

    /**
     * Obtiene el contenido de la API basado en la categoría, página, tamaño de página y orden.
     * @param category Categoría del contenido.
     * @param page Número de página para paginación.
     * @param pageSize Tamaño de página para paginación.
     * @param orderBy Campo por el cual ordenar el contenido.
     * @returns Observable con una lista de AppContent.
     */
    getContent(category: string, page: number, pageSize: number, orderBy: string): Observable<AppContent[]> {
        const params = new HttpParams()
            .set('category', category)
            .set('page', page)
            .set('pageSize', pageSize)
            .set('orderBy', orderBy)
            .set('asc', false);

        return this.http.get<AppContent[]>(this.baseUrl, { params });
    }   

    /**
     * Obtiene todo el contenido de la API con paginación.
     * @param page Número de página para paginación.
     * @param pageSize Tamaño de página para paginación.
     * @returns Observable con una lista de AppContent.
     */
    getAllContent(page: number, pageSize: number): Observable<AppContent[]> {
        const params = new HttpParams()
            .set('page', page)
            .set('pageSize', pageSize);

        return this.http.get<AppContent[]>(this.baseUrl, { params });
    }
}
