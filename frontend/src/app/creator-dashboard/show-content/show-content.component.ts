import { Component, OnInit } from '@angular/core';
import { CategorySectionComponent } from './category-section/category-section.component';
import { ContentService } from './show-content.service';
import { SkeletonModule } from 'primeng/skeleton';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-show-content',
    templateUrl: './show-content.component.html',
    styleUrl: './show-content.component.scss',
    standalone: true,
    imports: [CommonModule, CategorySectionComponent, SkeletonModule]
})
export class ShowContentComponent implements OnInit {

    isLoading = true;

    apps: any[] = [];
    music = [];
    books = [];
    videos = [];

    appsDownloaded: any[] = [];
    musicDownloaded = [];
    booksDownloaded = [];
    videosDownloaded = [];

    constructor(private contentService: ContentService) { }

    ngOnInit(): void {
        window.scrollTo({ top: 0, behavior: 'smooth' });

        this.loadContent();
    }

    /**
     * Carga el contenido desde el servicio de contenido.
     * Mapea los datos recibidos a un formato adecuado para la vista.
     */
    loadContent(): void {
        this.isLoading = true;
        this.contentService.getContent().subscribe({
            next: (data) => {
                this.apps = data.map(item => ({
                    id: item.id,
                    name: item.title,
                    imageUrl: item.imageUrl,
                    rating: Number(item.rating)
                }));
                this.isLoading = false;
            },
            error: (err) => {
                console.error('Error al cargar contenido', err);
                this.isLoading = false;
            }
        });
    }
}
