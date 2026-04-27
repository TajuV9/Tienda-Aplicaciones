import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Rating, RatingModule } from 'primeng/rating';
import { FormsModule } from '@angular/forms';
import { ArchiveService } from '../archive.service';
import { ToastLimiterService } from '../../../services/toast-limiter.service';
import Swal from 'sweetalert2';

@Component({
    selector: 'app-show-review',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule, RatingModule, Rating, FormsModule],
    templateUrl: './show-review.component.html',
    styleUrl: './show-review.component.css'
})
export class ShowReviewComponent implements OnInit {

    @Output() reviewDeleted = new EventEmitter<void>();
    @Output() reviewUpdated = new EventEmitter<{ rating: number; review: string }>();

    @Input() hasUserReview: boolean = false;
    @Input() fromCreator: boolean = false;
    @Input() id!: Number;

    editMode = false;
    editedReviewText = '';
    editedRating = 0;

    stars: number = 3;
    comentario: string = 'Este es un comentario de ejemplo.';
    value: number = 3;

    userReview: any = null;
    otherReviews: any[] = [];

    constructor(private reviewService: ArchiveService, private toastLimiter: ToastLimiterService) { }

    ngOnInit(): void {
        this.loadReviews();
    }

    /**
     * Carga las reseñas del archivo.
     * Utiliza el servicio reviewService para obtener las reseñas del archivo con el ID proporcionado.
     * Filtra la reseña del usuario actual y las demás reseñas.
     */
    loadReviews(): void {
        this.reviewService.getReviews(this.id).subscribe({
            next: (data) => {
                this.userReview = data.userReview;
                this.otherReviews = data.allReviews.filter(
                    (review: any) => review.id !== data.userReview?.id
                );
            },
            error: (err) => {
                this.toastLimiter.error('Error', 'No se pudieron cargar las reseñas', 5000);
            }
        });
    }

    /**
     * Elimina la reseña del usuario actual.
     * Muestra una confirmación antes de proceder con la eliminación.
     */
    deleteReview() {
        if (!this.userReview) return;

        Swal.fire({
            title: '¿Estás seguro de que deseas borrar tu reseña?',
            text: 'Esta acción no se puede deshacer.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6'
        }).then(result => {
            if (result.isConfirmed) {
                this.reviewService.deleteReview(this.id).subscribe({
                    next: () => {
                        this.loadReviews();
                        this.reviewDeleted.emit();
                        this.toastLimiter.success('Éxito 🗑️', 'Reseña eliminada con éxito', 5000);
                    },
                    error: (err) => {
                        this.toastLimiter.error('Error', 'No se pudo eliminar la reseña', 5000);
                    }
                });
            }
        })
    }

    /**
     * Inicia el modo de edición para la reseña del usuario.
     * Carga el texto y la calificación actuales de la reseña en los campos de edición.
     */
    startEdit() {
        if (!this.userReview) return;

        this.editMode = true;
        this.editedReviewText = this.userReview.review;
        this.editedRating = this.userReview.rating;
    }

    /**
     * Guarda los cambios realizados en la reseña del usuario.
     */
    saveChanges() {
        this.reviewService.updateReview(this.id, this.editedReviewText, this.editedRating).subscribe({
            next: (updatedReview) => {
                this.userReview = updatedReview;
                this.editMode = false;
                this.reviewUpdated.emit();

                this.toastLimiter.success('Éxito', 'Reseña actualizada con éxito', 5000);
            },
            error: (err) => {
                let errorMsg = 'No se pudo actualizar la reseña';
                if (err?.error?.errors && Array.isArray(err.error.errors) && err.error.errors.length > 0) {
                    errorMsg += ': ' + err.error.errors.join(', ');
                } else if (err?.error?.message) {
                    errorMsg += ': ' + err.error.message;
                }
                this.toastLimiter.error('Error', errorMsg, 5000);
                console.log(err);
            }
        });
    }

    /**
     * Envía una respuesta a una reseña específica.
     * Utiliza el servicio reviewService para enviar la respuesta.
     * @param review La reseña a la que se va a responder.
     */
    enviarRespuesta(review: any): void {
        const answerText = review.responseDraft;

        this.reviewService.answerReview(review.id, answerText).subscribe({
            next: (res) => {
                review.response = answerText;
                delete review.responseDraft;
                this.toastLimiter.success('Éxito', 'Respuesta enviada con éxito', 5000);
            },
            error: (err) => {
                const errorMsg = err?.error?.error || 'Error al responder';
                this.toastLimiter.error('Error', errorMsg, 5000);
            }
        });
    }

    /**
     * Elimina la respuesta a una reseña específica.
     * Utiliza el servicio reviewService para eliminar la respuesta.
     * @param review La reseña cuya respuesta se va a eliminar.
     */
    eliminarRespuesta(review: any): void {
        this.reviewService.deleteReviewResponse(review.id).subscribe({
            next: () => {
                review.response = null;
                review.editingResponse = false;
                review.editedResponse = null;
                review.responseDraft = '';
                this.toastLimiter.success('Éxito 🗑️', 'Respuesta eliminada correctamente', 5000);
            },
            error: (err) => {
                const errorMsg = err?.error?.error || 'No se pudo eliminar la respuesta';
                this.toastLimiter.error('Error', errorMsg, 5000);
            }
        });
    }

    /**
     * Inicia el modo de edición para la respuesta de una reseña.
     * Carga el texto actual de la respuesta en un campo editable.
     * @param review La reseña cuya respuesta se va a editar.
     */
    editResponse(review: any): void {
        review.editingResponse = true;
        review.editedResponse = review.response;
    }

    /**
     * Cancela la edición de la respuesta de una reseña.
     * Restaura el estado original y limpia el campo editable.
     * @param review La reseña cuya respuesta se está editando.
     */
    cancelEditResponse(review: any): void {
        review.editingResponse = false;
        review.editedResponse = null;
    }

    /**
     * Guarda los cambios realizados en la respuesta de una reseña.
     * Envía la respuesta actualizada al servidor y actualiza el estado de la reseña.
     * @param review La reseña cuya respuesta se va a guardar.
     */
    saveEditedResponse(review: any): void {
        const updatedText = review.editedResponse;

        this.reviewService.answerReview(review.id, updatedText).subscribe({
            next: () => {
                review.response = updatedText;
                review.editingResponse = false;
                delete review.editedResponse;
                console.log('Respuesta actualizada:', review.response);
                this.toastLimiter.success('Éxito', 'Respuesta actualizada con éxito', 5000);
            },
            error: (err) => {
                const errorMsg = err?.error?.error || 'Error al actualizar la respuesta';
                this.toastLimiter.error('Error', errorMsg, 5000);
            }
        });
    }

    /**
     * Genera un array de estrellas llenas basado en la calificación del usuario.
     */
    get fullStars() {
        return Array(this.stars || 0).fill(0);
    }

    /**
     * Genera un array de estrellas vacías basado en la calificación del usuario.
     */
    get emptyStars() {
        return Array(5 - (this.stars || 0)).fill(0);
    }


}
