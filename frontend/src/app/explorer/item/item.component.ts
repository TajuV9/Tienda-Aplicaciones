import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
    selector: 'app-item',
    imports: [CommonModule],
    templateUrl: './item.component.html',
    styleUrl: './item.component.css'
})
export class ItemComponent {
    @Input() name!: string;
    @Input() imageUrl!: string;
    @Input() rating!: any;
    @Input() id!: bigint;
    @Input() apps!: any[];
    @Input() fromCreator: boolean = false;
    @Input() sectionType: string = ''; // Nuevo input para el tipo de sección

    getStarsArray(starCount: number | string): number[] {
        return Array(Number(starCount)).fill(0);
    }

    constructor(private router: Router) { }

    /**
     * Navega a la página de detalles del archivo.
     * Utiliza el ID del archivo y, si es necesario, agrega un parámetro de consulta para indicar que proviene del creador.
     */
    navigateToDetail() {
        this.router.navigate(
            ['/archive', this.id],
            {
                queryParams: this.fromCreator ? { fromCreator: true } : {}
            }
        );
    }

    /**
     * Redondea la calificación a un número entero.
     */
    get roundedRating(): number {
        const numericRating = Number(this.rating);
        if (isNaN(numericRating)) return 0;
        return Math.round(numericRating);
    }

    get fullStars(): number[] {
        return Array(this.roundedRating).fill(0);
    }

    get emptyStars(): number[] {
        return Array(5 - this.roundedRating).fill(0);
    }

    get imageSectionClass(): string {
        switch (this.sectionType) {
            case 'APPLICATION': return 'img-apps';
            case 'MUSIC': return 'img-music';
            case 'VIDEO': return 'img-videos';
            case 'BOOK': return 'img-books';
            default: return '';
        }
    }

}

