import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface AppItem {
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
export class ItemSectionService {
    private apiUrl = environment.apiUrl+'/v1/content';

    constructor(private http: HttpClient) { }

    /**
     * Obtiene los elementos de la API basados en la búsqueda.
     * @param serach Término de búsqueda.
     * @param page Número de página para paginación.
     * @param pageSize Tamaño de página para paginación.
     * @returns Observable con una lista de AppItem.
     */
    getItemsByCategoryAndOrder(category: string, orderBy: string, page: number, pageSize: number): Observable<AppItem[]> {
        let params = new HttpParams()
            .set('page', page)
            .set('pageSize', pageSize);

        if (category) {
            params = params.set('category', category);
        }
        if (orderBy) {
            params = params.set('orderBy', orderBy);
        }


        return this.http.get<AppItem[]>(this.apiUrl, { params });
    }

}
