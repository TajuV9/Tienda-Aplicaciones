import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItemComponent } from "../item/item.component";
import { RouterLink } from '@angular/router';
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { GalleriaModule } from 'primeng/galleria';
import { AppsService, AppContent } from './apps.service';
import { SkeletonModule } from 'primeng/skeleton';

@Component({
    selector: 'app-apps',
    standalone: true,
    imports: [CommonModule, ItemComponent, RouterLink, CarouselModule, ButtonModule, GalleriaModule, SkeletonModule],
    templateUrl: './apps.component.html',
    styleUrls: ['./apps.component.css']
})
export class AppsComponent implements OnInit {
    @Input() title!: string;
    @Input() currentSection!: string;
    @Input() type!: string;
    @Input() orderBy!: string;

    categoryMap: { [key: string]: string } = {
        application: 'APPLICATION',
        music: 'MUSIC',
        videos: 'VIDEO',
        books: 'BOOK'
    };

    apps: AppContent[] = [];

    isLoading = true;

    constructor(private AppsService: AppsService) { }

    ngOnInit(): void {
        this.loadContent();
    }

    /**
     * Carga el contenido de la sección actual utilizando el servicio AppsService.
     * Utiliza el mapa categoryMap para determinar la categoría correcta según currentSection.
     */
    loadContent() {
        this.isLoading = true;
        const category = this.categoryMap[this.currentSection];
        if (category) {
            this.AppsService.getContent(category, 0, 24, this.orderBy).subscribe({
                next: (data) => {
                    this.apps = data;
                    this.isLoading = false;
                },
                error: (err) => {
                    console.error(`Error cargando contenido (${category}):`, err);
                    this.isLoading = false;
                }
            });
        } else {
            console.warn(`Categoría inválida: ${this.currentSection}`);
            this.isLoading = false;
        }
    }

    /**
     * Devuelve las aplicaciones que se mostrarán en la sección.
     * Utiliza la propiedad apps para acceder a las aplicaciones cargadas.
     */
    get displayedApplications() {
        return this.apps;
    }
}
