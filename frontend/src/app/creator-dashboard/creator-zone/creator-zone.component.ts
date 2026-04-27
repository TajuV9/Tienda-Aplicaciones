import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyFilesComponent } from "../my-files/my-files.component";
import { ShowContentComponent } from "../show-content/show-content.component";
import { ActivatedRoute } from '@angular/router';
import { UpdateZoneComponent } from "../update-zone/update-zone.component";

@Component({
    selector: 'app-creator-zone',
    standalone: true,
    imports: [CommonModule, MyFilesComponent, MyFilesComponent, ShowContentComponent, UpdateZoneComponent],
    templateUrl: './creator-zone.component.html',
    styleUrl: './creator-zone.component.css'
})
export class CreatorZoneComponent implements OnInit {
    activeSection: 'view' | 'create' | 'update' = 'view';
    initialAppName: string = '';
    initialDescription: string = '';
    initialCategory: string = '';
    initialAppId!: number;

    constructor(private route: ActivatedRoute) { }

    /**
     * Inicializa el componente y suscribe a los parámetros de la ruta para determinar la sección activa.
     */
    ngOnInit(): void {
        this.route.queryParams.subscribe(params => {
            const section = params['section'];
            const appId = +params['appId'];

            // Siempre asigna, usa 'view' por defecto
            if (section === 'update' || section === 'create' || section === 'view') {
                this.activeSection = section;
            } else {
                this.activeSection = 'view';
            }

            if (appId) {
                this.initialAppId = appId;
            }
        });

    }

    onPublishedSuccess() {
        window.scrollTo({ top: 0, behavior: 'smooth' });
        this.activeSection = 'view';
    }
}

