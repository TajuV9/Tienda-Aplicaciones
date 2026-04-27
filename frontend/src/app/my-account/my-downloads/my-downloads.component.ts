import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { SkeletonModule } from 'primeng/skeleton';
import { CategorySectionComponent } from '../../creator-dashboard/show-content/category-section/category-section.component';
import { ContentService } from '../../creator-dashboard/show-content/show-content.service';
import { GalleriaModule } from 'primeng/galleria';
import { CarouselModule } from 'primeng/carousel';

@Component({
    selector: 'app-my-downloads',
    imports: [CommonModule, CategorySectionComponent, SkeletonModule, GalleriaModule, CarouselModule],
    templateUrl: './my-downloads.component.html',
    styleUrl: './my-downloads.component.scss'
})
export class MyDownloadsComponent {

    isLoading = true;

    apps: any[] = [];
    music = [];
    books = [];
    videos = [];

    appsDownloaded: any[] = [];
    musicDownloaded = [];
    booksDownloaded = [];
    videosDownloaded = [];
    appsDownloadedWithUpdates: any[] = [];
    appsDownloadedWithoutUpdates: any[] = [];

    constructor(private contentService: ContentService) { }

    ngOnInit(): void {
        this.loadDownloads();
    }

    /**
     * Carga las descargas del usuario desde el servicio de contenido.
     * Separa las aplicaciones descargadas en dos categorías: con actualizaciones y sin actualizaciones.
     */
    loadDownloads(): void {
        this.isLoading = true;

        this.contentService.getDownloads().subscribe({
            next: (downloaded) => {
                this.appsDownloaded = downloaded.map(item => ({
                    id: item.contentId,
                    name: item.contentName,
                    imageUrl: item.contentIcon,
                    downloadLink: item.downloadLink,
                    hasUpdates: item.hasUpdates ?? false, // usa el valor real si está disponible
                    rating: item.rating,
                }));

                this.appsDownloadedWithUpdates = this.appsDownloaded.filter(app => app.hasUpdates);
                this.appsDownloadedWithoutUpdates = this.appsDownloaded.filter(app => !app.hasUpdates);
                this.isLoading = false;
            },
            error: (err) => {
                console.error('Error al cargar descargas', err);
                this.isLoading = false;
            }
        });
    }



}
