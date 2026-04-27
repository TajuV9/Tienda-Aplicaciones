import { Component, EventEmitter, Input, NgModule, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RatingModule } from 'primeng/rating';
import { ArchiveService } from '../archive.service';
import { ToastLimiterService } from '../../../services/toast-limiter.service';

@Component({
    selector: 'app-create-review',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule, RatingModule, FormsModule],
    templateUrl: './create-review.component.html',
    styleUrl: './create-review.component.css'
})
export class CreateReviewComponent {
    @Output() reviewCreated = new EventEmitter<{ rating: number; review: string }>();

    @Input() id!: Number;

    hasUserReview = false;
    formGroup: FormGroup;

    constructor(private fb: FormBuilder, private reviewService: ArchiveService, private toastLimiter: ToastLimiterService) {
        this.formGroup = this.fb.group({
            comentario: ['', Validators.required],
            value: [null, Validators.required]
        });
    }

    /**
     * Maneja el evento de envío del formulario para crear una nueva reseña.
     * Valida que la reseña tenga entre 5 y 500 caracteres y que la calificación sea mayor a 0.
     */
    onSubmit(): void {
        if (this.newReview.review.trim().length >= 5 || this.newReview.review.length <= 500 || this.newReview.rating > 0) {

            this.reviewService.postReview(this.id, this.newReview.review, this.newReview.rating).subscribe({
                next: (response) => {
                    this.toastLimiter.success('🎉 Éxito', 'Reseña creada correctamente. 🤩', 5000);
                    this.reviewCreated.emit();
                },
                error: (err) => {
                    // Mostrar lista de errores como en register
                    const errors = err.error?.errors;
                    if (errors && Array.isArray(errors)) {
                        let notification = 'Errores al crear la reseña:';
                        for (const errorMsg of errors) {
                            notification += `\n -${errorMsg}`;
                        }
                        this.toastLimiter.error('No se pudo crear la reseña. 😞', notification, 5000);
                    } else {
                        this.toastLimiter.error('No se pudo crear la reseña. 😞', err.error?.error, 5000);
                    }
                }
            });
        } else {
            this.toastLimiter.warn('Advertencia', 'Debes escribir una reseña y dar una calificación válida antes de publicar. 💫', 5000);
        }
    }

    // Inicializa la reseña nueva con valores por defecto
    newReview = {
        rating: 0,
        review: ''
    };
}
