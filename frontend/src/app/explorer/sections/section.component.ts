import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppsComponent } from '../apps/apps.component';
import { ActivatedRoute, Router } from '@angular/router';
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { GalleriaModule } from 'primeng/galleria';
import { SearchBarComponent } from "../../search-bar/search-bar.component";

@Component({
	selector: 'app-section-component',
	imports: [CommonModule, AppsComponent, CarouselModule, ButtonModule, GalleriaModule, SearchBarComponent],
	templateUrl: './section.component.html',
	styleUrls: ['./section.component.css']
})
export class SectionComponent implements OnInit {

	currentSection: string = '';

	constructor(private route: ActivatedRoute, private router: Router) { }

	/**
	 * Inicializa el componente y suscribe a los cambios en la URL para actualizar la sección actual.
	 * También desplaza la ventana al inicio de la página.
	 */
	ngOnInit(): void {
		window.scrollTo({ top: 0, behavior: 'smooth' });

		this.route.url.subscribe(segments => {
			this.currentSection = segments[0]?.path || 'home';
			this.updateSecciones();
			this.updateProductsSection();
		});
	}

	validSections = ['home', 'application', 'music', 'books', 'videos'];

	/**
	 * Verifica si la sección actual es válida.
	 * @param section - La sección a verificar.
	 * @returns true si la sección es válida, false en caso contrario.
	 */
	isSectionValid(section: string): boolean {
		return this.validSections.includes(section);
	}

	secciones: { title: string; type: string, orderBy: string }[] = [];

	/**
	 * Detecta cambios en las propiedades del componente y actualiza las secciones y productos según la sección actual.
	 */
	ngOnChanges(changes: SimpleChanges): void {
		if (changes['currentSection']) {
			this.updateSecciones();
			this.updateProductsSection();
		}
	}

	images = [
		{ currentSection: 'home' },
		{ currentSection: 'books' },
		{ currentSection: 'music' }
	];

	/**
	 * Actualiza las secciones disponibles según la sección actual.
	 * Las secciones se definen con un título, tipo y orden de clasificación.
	 */
	updateSecciones(): void {
		switch (this.currentSection) {
			case 'application':
			case 'home':
			case 'music':
			case 'videos':
			case 'books':
				this.secciones = [
					{ title: 'Top populares', type: 'populares', orderBy: 'timesDownloaded' },
					{ title: 'Top valorados', type: 'valorados', orderBy: 'rating' },
				];
				break;
			default:
				this.secciones = [];
		}
	}

	/**
	 * Actualiza la sección actual de los productos en la galería de imágenes.
	 * Asigna un identificador único a cada imagen basado en la sección actual y su índice.
	 */
	updateProductsSection(): void {
		this.images.forEach((image, index) => {
			image.currentSection = `${this.currentSection}${index + 1}`;
		});
	}

	/**
	 * Traduce la sección actual al español.
	 */
	get translatedSection(): string {
	switch (this.currentSection) {
		case 'home':
			return 'Inicio';
		case 'application':
			return 'Aplicaciones';
		case 'music':
			return 'Música';
		case 'books':
			return 'Libros';
		case 'videos':
			return 'Videos';
		default:
			return 'Sección';
	}
}
}
