import { CommonModule, Location } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AppItem, ItemSectionService } from './item-section.service';
import { ItemComponent } from '../item/item.component';

@Component({
    selector: 'app-item-section',
    imports: [CommonModule, ItemComponent, RouterLink],
    providers: [Location],
    templateUrl: './item-section.component.html',
    styleUrl: './item-section.component.css'
}) export class ItemSectionComponent {
    @Input() currentSection!: string;
    @Input() type!: string;
    applications: AppItem[] = [];
    category: string = '';
    orderBy: string = '';
    displaySection: string = '';

    constructor(
        private route: ActivatedRoute,
        private location: Location,
        private itemService: ItemSectionService
    ) {
        this.route.params.subscribe(params => {
            this.currentSection = params['currentSection'];
            this.type = params['type'];
        });
    }


    /**
     * Recupera los parámetros de la URL y llama al servicio para obtener los datos de la API.
     */
    ngOnInit(): void {
        this.route.params.subscribe(params => {
            const section = params['currentSection'];
            const type = params['type'];

            this.currentSection = section;
            this.type = type;

            const categoryMap: { [key: string]: string } = {
                application: 'APPLICATION',
                music: 'MUSIC',
                books: 'BOOK',
                videos: 'VIDEO'
            };
            this.category = categoryMap[section] || '';

            const orderByMap: { [key: string]: string } = {
                valorados: 'rating',
                populares: 'downloads'
            };
            this.orderBy = orderByMap[type] || '';

            const displayMap: { [key: string]: string } = {
                application: 'aplicaciones',
                music: 'música',
                books: 'libros',
                videos: 'videos',
                home: 'todo tipo de archivos'
            };
            this.displaySection = displayMap[section] || section;

            this.fetchApplications();
        });
    }

    /**
     * Llama al servicio para obtener los datos de la API y los asigna a la variable applications.
     */
    fetchApplications(): void {
        this.itemService.getItemsByCategoryAndOrder(this.category, this.orderBy, 0, 50).subscribe({
            next: (data) => this.applications = data,
            error: (err) => console.error('Error al obtener las aplicaciones:', err)
        });
    }

    /**
     * Navega hacia atrás en el historial de navegación.
     */
    goBack() {
        this.location.back();
    }
}
