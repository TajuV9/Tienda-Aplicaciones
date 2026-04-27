import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { sacarHeader } from '../../app.config';
import { environment } from '../../../environments/environment';

export interface ContentItem {
  id: number;
  title: string;
  imageUrl: string | null;
  rating: number | null;
  published: boolean;

  contentId: number;
  contentName: string;
  contentIcon: string;
  hasUpdates: boolean;
  downloadLink: string;
}

@Injectable({
  providedIn: 'root'
})
export class ContentService {
  private apiUrl = environment.apiUrl+'/v1/profile/content';
  private apiUrl2 = environment.apiUrl+'/v1/profile/downloads';

  constructor(private http: HttpClient) { }

  /**
   * Obtiene los elementos de contenido del usuario autenticado.
   * @returns Observable con una lista de ContentItem.
   */
  getContent(): Observable<ContentItem[]> {
    const headers: HttpHeaders = sacarHeader();


    return this.http.get<ContentItem[]>(this.apiUrl, { headers });
  }

  /**
   * Obtiene los elementos de descarga del usuario autenticado.
   * @returns Observable con una lista de ContentItem.
   */
  getDownloads(): Observable<ContentItem[]> {
    const headers: HttpHeaders = sacarHeader();
    
    return this.http.get<ContentItem[]>(this.apiUrl2, { headers });
  }
}
